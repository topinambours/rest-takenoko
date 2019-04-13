package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

@SpringBootApplication
@EnableAsync
public class App {

    public static void main(String[] args) {

        String APP_PORT = "8080";
        int GAME_SIZE = 2;

        if (args.length > 0){
            APP_PORT = args[0];
        }

        if (args.length > 1){
            GAME_SIZE = Integer.parseInt(args[1]);
        }

        SpringApplication app = new SpringApplication(App.class);

        Map<String, Object> properties = new Hashtable<>();

        properties.put("server.port", APP_PORT);
        properties.put("game.size", GAME_SIZE);

        app.setDefaultProperties(properties);
        app.run(args);
    }

}

