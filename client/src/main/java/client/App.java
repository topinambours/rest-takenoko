package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

@SpringBootApplication
@Import(Joueur.class)
@EnableAsync
public class App {

    @Autowired
    Joueur joueur;

    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }

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