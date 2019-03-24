package core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(Server.class)
public class App {

    @Autowired
    Server mainServer;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

