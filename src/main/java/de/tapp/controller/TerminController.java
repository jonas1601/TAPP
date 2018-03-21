package de.tapp.controller;

import de.tapp.entity.Termin;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TerminController {

    @GetMapping(path = "termine")
    public Termin getTermin() {
        return null;
    }

//    public Http//
}
