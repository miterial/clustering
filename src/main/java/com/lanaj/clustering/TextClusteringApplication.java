package com.lanaj.clustering;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Console;

@SpringBootApplication
@Slf4j
public class TextClusteringApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(TextClusteringApplication.class, args);
    }

    @Override
    public void run(String... args) {
        KMeans kMeans = new KMeans(10);
        kMeans.findCentroids();
        kMeans.start();
        log.info(kMeans.getResult());
    }
}

