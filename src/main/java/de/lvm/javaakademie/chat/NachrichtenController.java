package de.lvm.javaakademie.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@CrossOrigin
public class NachrichtenController {

	HashMap<DeferredResult<List<Nachricht>>, Integer> listeResult = new HashMap<>();
	ArrayList<Nachricht> liste = new ArrayList<>();

	@GetMapping(path = "/nachrichten/{letzteNachricht}")
	public DeferredResult<List<Nachricht>> getNachrichten(@PathVariable int letzteNachricht) {

		DeferredResult<List<Nachricht>> deferredResult = new DeferredResult<>();
		if (!liste.subList(letzteNachricht, liste.size()).isEmpty()) {
			deferredResult.setResult(liste.subList(letzteNachricht, liste.size()));
		} else {
			listeResult.put(deferredResult, letzteNachricht);
		}

		return deferredResult;

	}
	
	
	@GetMapping(path="/user")
	public List<String> getUser(){
		
		List<String> user = new ArrayList<>();
		for (Nachricht nachricht : liste) {
			if(!user.contains(nachricht.getAbsender())){
			user.add(nachricht.getAbsender());
			}
		}
		
		return user;
	}

	@PostMapping(path = "/nachrichten")
	public void addNachricht(@RequestBody Nachricht nachricht) {
		liste.add(nachricht);
		Set<Entry<DeferredResult<List<Nachricht>>, Integer>> entrySet = listeResult.entrySet();
		for (Entry<DeferredResult<List<Nachricht>>, Integer> entry : entrySet) {
			entry.getKey().setResult(liste.subList(entry.getValue(), liste.size()));
		}
		listeResult.clear();
	}
}
