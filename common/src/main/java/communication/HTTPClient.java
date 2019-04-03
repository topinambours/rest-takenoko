package communication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class HTTPClient {

    private String user_adress;
    private String server_url;

    private int id;

    Logger log = LoggerFactory.getLogger(HTTPClient.class);

    public HTTPClient() {
        this.id = 0;
    }

    public HTTPClient(int id) {

        this.id = id;
    }

    public HTTPClient(int id, String user_adress) {
        this.id = id;
        this.user_adress = user_adress;
    }

    public HTTPClient(int id, String user_adress, String server_url) {
        this.id = id;
        this.user_adress = user_adress;
        this.server_url = server_url;
    }

    public int getId() {
        return id;
    }

    public String getUser_adress() {
        return user_adress;
    }

    public String getServer_url() {
        return server_url;
    }

    public <T> T request(String uri, Class<T> responseType) {
        try {
            return new RestTemplate().getForObject(String.format("%s/%s", server_url, uri), responseType);
        } catch (ResourceAccessException error) {
            System.out.println("Server disconnected");
        }
        System.exit(1);
        return null;
    }

    public <Req,Res> Res post_request(String uri,Req postObject, Class<Res> responseType){
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Req> request = new HttpEntity<>(postObject, new HttpHeaders());
        URI uri_req = null;
        try {
            uri_req = new URI(String.format("%s/%s", server_url, uri));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return restTemplate.postForObject(uri_req, request, responseType);

    }



    public ResponseContainer pingServer() {
        log.info(user_adress + " performing ping to " + server_url);
        return request(String.format("ping/%d/%s", id, user_adress), ResponseContainer.class);
    }

    public ResponseContainer enter_matchmaking() {
        return request(String.format("action/enter_matchmaking/%d/%s", server_url, id, user_adress), ResponseContainer.class);
    }

    public TuileContainer piocher_tuiles() {
        return request(String.format("action/piocher", server_url), TuileContainer.class);
    }

    public boolean rendre_tuiles(int tuileId, int tuileId_2){
        return request(String.format("action/rendre-tuiles/%s/%s",tuileId, tuileId_2), Boolean.class);
    }

    public ResponseContainer rendre_tuiles_2(Tuile tuile, Tuile tuile_2){
        List<Tuile> out = new ArrayList<>();
        out.add(tuile);
        out.add(tuile_2);
        return post_request("action/rendre_tuiles/", new TuileContainer(out), ResponseContainer.class);
    }

    public ResponseContainer poser_tuile(int tuileId, CoordAxial pos){
        return request(String.format("action/poser_tuile/%s/%s/%s",tuileId, pos.getQ(), pos.getR()), ResponseContainer.class);
    }

    public ResponseContainer req_register(){
        return request(String.format("register/%d/%s", id, user_adress), ResponseContainer.class);
    }


    @Override
    public String toString() {
        return String.format("HTTPClient : id=%d uri=%s onServer=%s", id, user_adress, server_url);
    }

    @Bean(name = "client_test")
    @Scope("singleton")
    public HTTPClient httpClient_for_test() {
        return new HTTPClient(1);
    }
}
