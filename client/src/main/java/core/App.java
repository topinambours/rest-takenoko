package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Import(Joueur.class)
@EnableAsync
public class App {

    @Autowired
    Joueur joueur;

    public static void main(String[] args) {

        String APP_PORT = "8081";
        String DIST_ADD = "http://localhost:8080";
        String USER_ID = "1";
        if (args.length > 0){
            APP_PORT = args[0];

            if (args.length > 1){
                DIST_ADD= args[1];
            }
            if (args.length > 2){
                USER_ID = args[2];
            }
        }

        SpringApplication app = new SpringApplication(App.class);

        Map<String, Object> properties = new HashMap<>();

        properties.put("server.port", APP_PORT);
        properties.put("client.port", APP_PORT);
        properties.put("client.id", USER_ID);
        properties.put("distant.server.address", DIST_ADD);

        app.setDefaultProperties(properties);
        app.run(args);
    }

}