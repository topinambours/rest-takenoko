package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class App {

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

