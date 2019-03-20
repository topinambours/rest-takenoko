package core;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController {

    @RequestMapping("/status")
    public String greeting() {
        return "Server is up";
    }

}
