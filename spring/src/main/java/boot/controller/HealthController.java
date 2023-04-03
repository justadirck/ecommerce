package boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String health() {
        return "health";
    }
    @GetMapping("/carly")
    public String carly() {
        return "carly";
    }

    @GetMapping("/dirck")
    public String dirck() {
        return "dirck";
    }

    @GetMapping("/shar")
    public String shar() {
        return "shar";
    }

}
