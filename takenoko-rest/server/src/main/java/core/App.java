package core;

import cucumber.api.java.en.When;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public App(){
        start();
    }


    public void start(){
        //foo
    }

    @When("^server launched$")
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

}
