package com.accenture.desafio;

import com.accenture.desafio.controllers.UserController;
import com.accenture.desafio.objects.Phone;
import com.accenture.desafio.objects.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DesafioApplicationTests {

	@Autowired
	private UserController userController;

	@Test
	void contextLoads() {
		for (int i = 0; i <= 1000; i++) {
			String name = "João da Silva" + i, email = "joao" + i + "@silva.org", password = "hunter21" + i;
			User user = new User();

			List<Phone> phones = new ArrayList<>();
			phones.add(new Phone("987654321" + i,"21"));

			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setPhones(phones);

			var response = userController.postUser(user);
			Assertions.assertEquals(201,response.getStatusCodeValue());

			User loginUser = new User();
			loginUser.setEmail(email);
			loginUser.setPassword(password);

			var response2 = userController.loginUser(loginUser);
			Assertions.assertEquals(200,response2.getStatusCodeValue());

			User generatedUser = (User) response2.getBody();
			assert generatedUser != null;

			var response3 = userController.getUserByIdAndToken(generatedUser.getId(), generatedUser.getToken());
			Assertions.assertEquals(200,response3.getStatusCodeValue());
		}
	}

}
