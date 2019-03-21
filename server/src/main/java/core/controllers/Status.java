package core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Status {

    @Autowired
    public Status() {
        super();
    }

    @RequestMapping("/status")
    public String status_req() {
        return getStatus();
    }

    static String getStatus() {
        return "Server is up";
    }
}
