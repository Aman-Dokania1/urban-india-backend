package com.Urban_India;

import com.Urban_India.entity.Role;
import com.Urban_India.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrbanIndiaApplication  implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UrbanIndiaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role roleAdmin = Role.builder() .name("ROLE_ADMIN").build();
		Role roleUser = Role.builder().name("ROLE_USER").build();
		if(!roleRepository.findByName("ROLE_USER").isPresent())
			roleRepository.save(roleUser);
		if(!roleRepository.findByName("ROLE_ADMIN").isPresent())
			roleRepository.save(roleAdmin);

	}
}
