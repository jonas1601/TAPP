package de.tapp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"de.tapp.controller"})
public class TAPPServerApplication {

	public static void main(String[] args){
		try {
//			File file = ResourceUtils.getFile("classpath:tapp-d4545-firebase-adminsdk-wqwe6-7ac69f7815.json");
//			FileInputStream serviceAccount =
//					new FileInputStream(file);
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//					.setDatabaseUrl("https://tapp-d4545.firebaseio.com")
//					.build();
//
//			FirebaseApp.initializeApp(options);

			SpringApplication.run(TAPPServerApplication.class, args);
		}
		catch(Exception e){
			e.printStackTrace();
			}
	}


}
