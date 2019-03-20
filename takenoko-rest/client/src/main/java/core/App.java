package core;

import org.springframework.web.client.RestTemplate;


public class App {

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        String status = restTemplate.getForObject("http://localhost:8080/status", String.class);
        System.out.println(String.format("Status : %s", status));
    }

}