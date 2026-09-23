package com.alfredodev.miniproyectos6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniProyectoS6Application {

	public static void main(String[] args) {
		SpringApplication.run(MiniProyectoS6Application.class, args);
	}

}
