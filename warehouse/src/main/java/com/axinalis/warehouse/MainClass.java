package com.axinalis.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.axinalis.warehouse.repository")
@SpringBootApplication
public class MainClass {

    public static void main(String[] args) {
        SpringApplication.run(MainClass.class, args);

        /*for (int i = 0; i < 400; i++) {
            System.out.println("/---------------\\");
            System.out.println("Warehouse module is working");
            System.out.printf("%d:%d time is left%n", (400 - i) / 10, (400 - i) * 4 % 60);
            System.out.println("\\---------------/");
            try{
                Thread.sleep(6000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }*/
    }

}
