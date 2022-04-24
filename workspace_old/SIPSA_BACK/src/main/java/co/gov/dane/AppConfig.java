package co.gov.dane;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppConfig {
     
    public static void main(String[] args) {
    	System.setProperty("file.encoding" , "UTF-8");
        SpringApplication.run(AppConfig.class, args);
    }
}
