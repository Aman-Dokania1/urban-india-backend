package com.Urban_India;

import com.Urban_India.entity.Role;
import com.Urban_India.entity.ServiceProviderEntitiy;
import com.Urban_India.exception.ResourceNotFoundException;
import com.Urban_India.repository.RoleRepository;
import com.Urban_India.repository.ServiceRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Urban India",version = "v 3.0",description = "Application like Urban India"))
public class UrbanIndiaApplication  implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ServiceRepository serviceRepository;

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

//		ServiceProviderEntitiy service= ServiceProviderEntitiy.builder().title("service related to household").description("providing very good service").build();
//		ServiceProviderEntitiy service1= ServiceProviderEntitiy.builder().title("service related to education").description("world class level education center").build();
//		serviceRepository.save(service);
//		serviceRepository.save(service1);
	}
}

