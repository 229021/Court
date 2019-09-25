package com.san.platform.hikvison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HikvisonApplication {
    public static void main(String[] args) { SpringApplication.run(HikvisonApplication.class, args); }
}
