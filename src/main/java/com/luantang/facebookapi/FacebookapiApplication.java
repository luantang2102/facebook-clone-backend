package com.luantang.facebookapi;

import com.luantang.facebookapi.models.enums.ConnectStatus;
import com.luantang.facebookapi.models.enums.Role;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@SpringBootApplication
public class FacebookapiApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(FacebookapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserEntity adminAccount = userRepository.findByRole(Role.ADMIN);
		if(Objects.isNull(adminAccount)) {
			UserEntity user = new UserEntity(
					UUID.randomUUID().toString(),
					"administrator",
					"",
					"",
					"admin@gmail.com",
					new BCryptPasswordEncoder().encode("admin"),
					Role.ADMIN,
					true,
					new Date(),
					0,
					new ArrayList<>(),
					ConnectStatus.ONLINE);
			userRepository.save(user);
		}
	}
}
