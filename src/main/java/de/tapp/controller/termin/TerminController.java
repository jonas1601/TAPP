package de.tapp.controller.termin;

import de.tapp.entity.Termin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TerminController {

    @GetMapping(path = "termine")
    public Termin getTermin() {
        return null;
    }

//    public Http//
}
