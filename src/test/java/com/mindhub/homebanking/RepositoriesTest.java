package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardsUtils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    public class RepositoriesTest {

        @Autowired
        LoanRepository loanRepository;
        @Autowired
        TransactionRepository transactionRepository;
        @Autowired
        ClientRepository clientRepository;
        @Autowired
        CardRepository cardRepository;
        @Autowired
        AccountRepository accountRepository;

        @Test
        //busca todos los préstamos y luego verificar que la lista no esté vacía
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        //verifica que en la lista de los préstamos exista uno llamado “Personal
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }
        @Test
        //verifica que en la lista de los préstamos exista uno llamado "Hipotecario"
        public void existHipLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Hipotecario"))));
        }
        @Test
        //busca todos las Transacciones y luego verificar que la lista no esté vacía
        public void existsTransaction(){
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions,is(not(empty())));
        }
        @Test
        public void existAccounts(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts,is(not(empty())));
        }
        @Test
        public void existAccount(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts,hasItem(hasProperty("accountType", is("cajaDeAhorroEnPesos"))));
        }
        @Test
        public void existClients(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients,is(not(empty())));
        }
      /*  @Test
        public void existAdmin(){
            List<Client> clients = clientService.getClients();
            assertThat(clients,hasItem(hasProperty("accountType", is("cajaDeAhorroEnPesos"))));
        }
*/
        @Test
        public void cardNumberIsCreated(){
            String cardNumber = CardsUtils.cardNumber();
            assertThat(cardNumber,is(not(emptyOrNullString())));
        }
        @Test
        public void cardNumberExist(){
            String cardNumber = CardsUtils.cardNumber();
            assertThat(cardNumber,is(not(emptyOrNullString())));
        }
    }

