package communication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jcabi.aspects.RetryOnFailure;
import communication.container.CoordContainer;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@JsonIgnoreProperties(ignoreUnknown=true)
public class HTTPClient {

    @Autowired
    private Environment env;

    private String user_adress;

    private String server_url;

    private int id;

    //Logger log = LoggerFactory.getLogger(HTTPClient.class);
    public HTTPClient(){
        this.id = -1;
        this.user_adress = "";
        this.server_url = "";
    }

    public HTTPClient(int id, String user_adress, String server_url) {
        this.id = id;
        this.user_adress = user_adress;
        this.server_url = server_url;
        registerGame();
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

    @RetryOnFailure(attempts = 5, delay = 200, verbose = true)
    public <T> T self_request(String uri, Class<T> responseType) {
        return new RestTemplate().getForObject(String.format("%s/%s", "http://" + user_adress, uri), responseType);
    }

    @RetryOnFailure(attempts = 5, delay = 200, verbose = true)
    public <T> T request(String uri, Class<T> responseType) {
        return new RestTemplate().getForObject(String.format("%s/%s", server_url, uri), responseType);
    }

    @RetryOnFailure(attempts = 5, delay = 200, verbose = true)
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

    public ResponseContainer registerGame(){
        return post_request("register/", this, ResponseContainer.class);
    }

    public ResponseContainer isItMyTurn(){
        return post_request("current_player_turn", this, ResponseContainer.class);
    }

    public TuileContainer piocher_tuiles() {
        return request("action/piocher", TuileContainer.class);
    }

    public ResponseContainer rendreTuiles(TuileContainer tuiles){
        return post_request("/action/rendre_tuiles/", tuiles, ResponseContainer.class);
    }

    public ResponseContainer poserTuile(PoseTuileContainer poseTuileContainer){
        return post_request("/action/poser-tuile/", poseTuileContainer, ResponseContainer.class);
    }

    public CoordContainer requestLegalMovesTuiles(){
        return request("/plateau/legal/", CoordContainer.class);
    }

    public ResponseContainer notifyEndTurn(){
        return request("/end_turn", ResponseContainer.class);
    }

    public ResponseContainer isGameEnded(){
        return request("/gameEnded", ResponseContainer.class);
    }

    public ResponseContainer isGameStarted(){
        return request("/gameStarted", ResponseContainer.class);
    }

    public void setUser_adress(String user_adress) {
        this.user_adress = user_adress;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format("HTTPClient : id=%d uri=%s onServer=%s", id, user_adress, server_url);
    }

    @Primary
    @Bean(name = "http_client")
    public HTTPClient httpClient(){
        String user_port = env.getProperty("client.port");
        String server_adress = env.getProperty("distant.server.address");
        int player_id = Integer.parseInt(env.getProperty("client.id"));
        return new HTTPClient(player_id, "localhost:" + user_port, server_adress);
    }
}
