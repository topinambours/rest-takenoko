package core;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import takenoko.Container.TuileContainer;

import java.util.Arrays;


public class Joueur {

    private final int id;

    private Joueur(){
        this.id = -1;
    }

    public Joueur(int id){
        this.id = id;
    }

    public void piocher(){
        final String uri = "http://localhost:8080/action/piocher";

        RestTemplate restTemplate = new RestTemplate();
        TuileContainer result = restTemplate.getForObject(uri, TuileContainer.class);

        System.out.println("Le joueur a pioché :" + Arrays.deepToString(result.getContent().toArray()));
    }

    @Bean(name = "joueur_1")
    public Joueur joueur_1(){
        return new Joueur(1);
    }
}
