package takenoko;

import takenoko.util.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource("default-spring.xml")
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        // On initialise la console avec le mode souhaité {release, debug, test}
        String consoleModeLabel = args[0];
        Console.Log.init(consoleModeLabel);
        SpringApplication.run(GameStarter.class, args);
    }
}
