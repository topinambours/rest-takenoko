package core;

import communication.Container.ResponseContainer;
import communication.HTTPClient;
import org.springframework.context.annotation.Bean;
import communication.Container.TuileContainer;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


public class Joueur {

    private final int id;

    private final HTTPClient httpClient;

    private Joueur(){
        this.id = -1;
        this.httpClient = new HTTPClient(id);
    }

    public Joueur(int id){
        this.id = id;
        this.httpClient = new HTTPClient(id);
    }

    public Joueur(int id, HTTPClient httpClient){
        this.id = id;
        this.httpClient = httpClient;
    }

    public void enter_matchmaking(){
        ResponseContainer response = httpClient.enter_matchmaking();
        System.out.println(response.toString());
    }

    public void piocher(){
        TuileContainer result = httpClient.piocher_tuiles();

        System.out.println("Le joueur a pioché :" + Arrays.deepToString(result.getContent().toArray()));
    }

    public int getId() {
        return id;
    }

    @Bean(name = "joueur_1")
    public Joueur joueur_1(){
        return new Joueur(1);
    }
}
