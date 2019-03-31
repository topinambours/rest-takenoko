package communication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import communication.Container.ResponseContainer;
import communication.Container.TuileContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import takenoko.tuile.CoordAxial;


@JsonIgnoreProperties(ignoreUnknown = true)
public class HTTPClient {

    //TODO demander comment obtenir l'url de l'appli
    private String user_adress = "localhost:8081";
    private final String server_url = "http://localhost:8080";
    private int id;

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

    public ResponseContainer pingServer() {
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
