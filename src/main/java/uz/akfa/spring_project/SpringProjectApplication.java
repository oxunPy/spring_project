package uz.akfa.spring_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);
    }


//    @Scheduled(fixedRate = 1000L)
//    public void start(){
//        System.out.println("new Rate " + new Date());
//    }
//
//    @Scheduled(fixedDelay = 1000L)
//    public void start2(){
//        System.out.println("new FixedDelay" + new Date());
//    }

    @Scheduled(cron = "0 46 7 * * *")
    public void start(){
        System.out.println("new cron " + new Date());
    }

}
