package com.ramgom;

import akka.actor.ActorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DronesAkkaApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;

    @Autowired
    private Dispatcher dispatcher;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DronesAkkaApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("drones-akka");
        springExtension.initialize(applicationContext);
        return system;
    }

    @Override
    public void run(String... args)  {
        dispatcher.start();
    }
}
