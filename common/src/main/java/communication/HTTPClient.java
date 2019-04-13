package communication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import communication.container.ResponseContainer;
import communication.container.TuileContainer;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown=true)
public class HTTPClient {

    private String user_adress;

    private String server_url;

    private int id;

    //Logger log = LoggerFactory.getLogger(HTTPClient.class);

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

    public <T> T self_request(String uri, Class<T> responseType) {
        try {
            return new RestTemplate().getForObject(String.format("%s/%s", "http://" + user_adress, uri), responseType);
        } catch (ResourceAccessException error) {
            System.out.println(error.getMessage());
        }
        //System.exit(1);
        return null;
    }

    public <T> T request(String uri, Class<T> responseType) {
        try {
            return new RestTemplate().getForObject(String.format("%s/%s", server_url, uri), responseType);
        } catch (ResourceAccessException error) {
            System.out.println("Server disconnected");
        }
        //System.exit(1);
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

    public ResponseContainer registerGame(){
        return post_request("register/", this, ResponseContainer.class);
    }

    public ResponseContainer isItMyTurn(){
        return post_request("current_player_turn", this, ResponseContainer.class);
    }

    public TuileContainer piocher_tuiles() {
        return request("action/piocher", TuileContainer.class);
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

    @Override
    public String toString() {
        return String.format("HTTPClient : id=%d uri=%s onServer=%s", id, user_adress, server_url);
    }
}
