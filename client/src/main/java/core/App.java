package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@SpringBootApplication
@Import(Joueur.class)
public class App {


    private final Joueur joueur;

    @Autowired
    public App(@Qualifier("joueur_1") Joueur joueur) {
        this.joueur = joueur;
        this.joueur.enter_matchmaking();
    }

    public static void main(String args[]) {
        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8081"));
        app.run(args);
    }

}