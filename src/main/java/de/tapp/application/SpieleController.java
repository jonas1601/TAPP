package de.tapp.application;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import de.tapp.entity.Rolle;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.hibernate.cfg.Configuration;



@RestController
@CrossOrigin
public class SpieleController {


	@GetMapping(path = "/spiele")
	public List<Rolle> getRollen() {


		Rolle r = new Rolle();
		r.setBeschreibung("Test");
		r.setRollenId(1);
		Configuration config = new Configuration();
		config.configure();
		Session session = config.buildSessionFactory().openSession();
		return session.createCriteria(Rolle.class).list();
/*
		// This registration token comes from the client FCM SDKs.
		String registrationToken = "cDXliGfPzgA:APA91bHjXK3zRlF79ehrzEoRR1xF4eXQmIx7fucMEQFBU1FYKEdQuM5K5VLDM6azrO4fRp-QFfHgdwgEydcvU3O_0AtM4k5mOK4p39rDn3bLUWYfnKVrXgIRD2DbOcU3D6PP0reai2Bp \n";

	// See documentation on defining a message payload.
		Message message = Message.builder()
				.setToken(registrationToken)
				.build();

	// Send a message to the device corresponding to the provided
	// registration token.
		String response = null;
		try {
			response = FirebaseMessaging.getInstance().sendAsync(message).get();
		}catch (Exception e){
			e.printStackTrace();
		}
	// Response is a message ID string.
		System.out.println("Successfully sent message: " + response);

		return r;*/

	}
}