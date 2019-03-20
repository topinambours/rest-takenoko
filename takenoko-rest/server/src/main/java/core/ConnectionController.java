package core;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.junit.Assert.*;

@RestController
class ConnectionController {

    @RequestMapping("/status")
    public String status_req() {
        return getStatus();
    }

    protected static String getStatus() {
        return "Server is up";
    }

}

