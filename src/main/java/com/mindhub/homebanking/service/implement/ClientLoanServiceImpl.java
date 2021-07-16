package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.service.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {
    @Autowired
    ClientLoanService clientLoanService;

    @Override
    public void saveLoan(ClientLoan clientLoan) { clientLoanService.saveLoan(clientLoan);  }
}
