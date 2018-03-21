package de.tapp.application;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;


@SpringBootApplication
public class TAPPServerApplication {

	public static void main(String[] args){
		try {
			File file = ResourceUtils.getFile("classpath:tapp-d4545-firebase-adminsdk-wqwe6-7ac69f7815.json");
			FileInputStream serviceAccount =
					new FileInputStream(file);
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://tapp-d4545.firebaseio.com")
					.build();

			FirebaseApp.initializeApp(options);

			SpringApplication.run(TAPPServerApplication.class, args);
		}
		catch(Exception e){
			e.printStackTrace();
			}
	}


}
