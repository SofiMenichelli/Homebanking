package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository repositoryClient, AccountRepository repositoryAccount) {
		return (args) -> {
			// save a couple of customers
			Client client1 = repositoryClient.save(new Client("Melba", "Menichelli", "smenichelli@hotmail.com"));
			Client client2 =repositoryClient.save(new Client("Javier", "Perez", "gjavi.perez@hotmail.com"));
			Client client3 =repositoryClient.save(new Client("Sabina", "Menichelli", "sabinamenichelli@hotmail.com"));
			Client client4 =repositoryClient.save(new Client("Fernando", "Menichelli", "fmenichelli@hotmail.com"));
			repositoryAccount.save(new Account("12548203", LocalDateTime.now(),5000, "Cuenta Corriente $", "CC$", client1));
			repositoryAccount.save(new Account("851852", LocalDateTime.now().plusDays(1) ,7500, "Caja de Ahorro en $", "CA$", client1));
			repositoryAccount.save(new Account("25341852", LocalDateTime.now().plusDays(2) ,10500,"Caja de Ahorro en u$s", "CAu$S", client1));
		};
	}
}
