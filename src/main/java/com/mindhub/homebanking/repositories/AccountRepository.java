package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumber (String number);
    //Account findById(long id);
    List<Account> findByStatus (boolean status);
}
