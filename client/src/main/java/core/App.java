package core;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
@Import(Joueur.class)
public class App {


    private final Joueur joueur;

    //TODO demander comment obtenir l'url de l'appli
    private final String user_adress = "localhost:8081";
    private final String server_url = "http://localhost:8080";

    @Autowired
    public App(@Qualifier("joueur_1") Joueur joueur) {
        this.joueur = joueur;
        enter_lobby();
    }

    public void enter_lobby(){
        final String uri = server_url + "/action/enter_matchmaking/" + user_adress;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println(result);
    }

    public static void main(String args[]) {
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8081"));
        app.run(args);
    }

}