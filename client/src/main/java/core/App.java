package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class App {

    public static void main(String args[]) {
        System.out.println(Arrays.deepToString(args));

        String APP_PORT = "8081";
        if (args.length > 0){
            APP_PORT = args[0];
        }

        SpringApplication app = new SpringApplication(App.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", APP_PORT));
        app.run(args);
    }

}