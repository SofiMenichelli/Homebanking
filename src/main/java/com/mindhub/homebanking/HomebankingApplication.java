package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}
	@Bean
	public CommandLineRunner initData(ClientRepository repositoryClient,
									  AccountRepository repositoryAccount,
									  TransactionRepository repositoryTransaction,
									  LoanRepository repositoryLoan,
									  ClientLoanRepository repClientL,
									  CardRepository repCard
	) {
		return (args) -> {
			// save a couple of customers
			Client admin = repositoryClient.save(new Client("ADMIN", "ADMIN", "admin@mindhub.com", passwordEncoder.encode("admin")));
			Client client1 = repositoryClient.save(new Client("Melba", "Morel", "melba@mindhub.com",passwordEncoder.encode("melba1989")));
			Client client2 =repositoryClient.save(new Client("Javier", "Perez", "gjavi.perez@hotmail.com",passwordEncoder.encode("javier1302")));
			Client client3 =repositoryClient.save(new Client("Sabina", "Menichelli", "sabinamenichelli@hotmail.com", passwordEncoder.encode("menichelli40")));
			Client client4 =repositoryClient.save(new Client("Fernando", "Menichelli", "fmenichelli@hotmail.com", passwordEncoder.encode("fmmenichelli50")));
			Account acc1 = repositoryAccount.save(new Account("VIN001", LocalDateTime.now(),5000, AccountType.cuentaCorriente, client1));
			Account acc2 = repositoryAccount.save(new Account("VIN002", LocalDateTime.now().plusDays(1) ,7500, AccountType.cajaDeAhorroEnPesos , client1));
			Account acc3 = repositoryAccount.save(new Account("VIN003", LocalDateTime.now().plusDays(2) ,10500, AccountType.cajaDeAhorroEnDolares, client2));
			//Transaction trans1 = repositoryTransaction.save(new Transaction(-256.5, "Compra MercadoPago", LocalDateTime.now(), TransactionType.DEBIT,acc1));
			//Transaction trans2 =repositoryTransaction.save(new Transaction(50.0, "Pago MercadoPago", LocalDateTime.now(), TransactionType.CREDIT,acc1));
			//Transaction trans3 =repositoryTransaction.save(new Transaction(250.0, "Pago SportTotal", LocalDateTime.now(), TransactionType.CREDIT,acc1));


			Loan hipotecario = repositoryLoan.save(new Loan("Hipotecario", 500000F, Arrays.asList(12,24,36,48,60), 0.50));
			Loan personal = repositoryLoan.save(new Loan("Personal", 100000F, Arrays.asList(6,12,24), 0.60));
			Loan automotriz = repositoryLoan.save(new Loan("Automotriz", 300000F, Arrays.asList(6,12,24,36), 0.40));

			repClientL.save(new ClientLoan(400000F, 24, client1, hipotecario));
			repClientL.save(new ClientLoan(50000F, 12, client1, personal));
			repClientL.save(new ClientLoan(100000F, 24, client2, personal));
			repClientL.save(new ClientLoan(200000F, 36, client2, automotriz));

			repCard.save(new Card(CardType.DEBIT, CardColor.GOLD, client1));
			repCard.save(new Card(CardType.CREDIT, CardColor.TITANIUM, client1));
			repCard.save(new Card(CardType.CREDIT, CardColor.SILVER, client2));

		};
	}

}
