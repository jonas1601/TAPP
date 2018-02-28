package de.tapp.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class SpieleController {

	private List<Spiel> spiele = new ArrayList<>();
	
	
	@GetMapping(path="/spiele")
	public List<Spiel> getSpiele(){
		return spiele;
	}
	
	@PostMapping(path="/spiele/add")
	public void addSpiel(@RequestBody String name){
		spiele.add(new Spiel(name));
	}
}
