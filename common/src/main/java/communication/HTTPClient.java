package communication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import communication.Container.ResponseContainer;
import communication.Container.TuileContainer;
import org.springframework.web.client.RestTemplate;


@JsonIgnoreProperties(ignoreUnknown = true)
public class HTTPClient {

    private int id;



    //TODO demander comment obtenir l'url de l'appli
    private final String user_adress = "localhost:8081";
    private final String server_url = "http://localhost:8080";

    private Boolean inMatchmaking;


    public HTTPClient(){
        this.id = 0;
        inMatchmaking = false;
    }

    public HTTPClient(int id){
        this.id = id;
        inMatchmaking = false;
    }

    public HTTPClient(int id, boolean inMatchmaking){
        this.id = id;
        this.inMatchmaking = inMatchmaking;
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

    public Boolean getInMatchmaking() {
        return inMatchmaking;
    }
    public void setInMatchmaking(Boolean inMatchmaking) {
        this.inMatchmaking = inMatchmaking;
    }

    public ResponseContainer enter_matchmaking(){
        final String uri = String.format("%s/action/enter_matchmaking/%d/%s", server_url, id, user_adress);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, ResponseContainer.class);
    }

    public TuileContainer piocher_tuiles(){
        final String uri = String.format("%s/action/piocher", server_url);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, TuileContainer.class);
    }


    @Override
    public String toString() {
        return String.format("HTTPClient : id=%d uri=%s onServer=%s inMatchmaking=%s", id, user_adress, server_url, inMatchmaking.toString());
    }
}
