package account.service;

import account.exceptions.UserAuthException;
import account.exceptions.UserExistException;
import account.model.Account;
import account.model.AccountEntity;
import account.model.AccountId;
import account.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccountInfo(Account account) {
        String[] emailWords = account.getEmail().split("@");
        if (account.getName() == null ||
            account.getPassword() == null ||
            account.getName().equals("") ||
            account.getPassword().equals("") ||
            account.getLastname() == null ||
            account.getLastname().equals("") ||
            account.getEmail() == null ||
            account.getEmail().equals("") ||
            !account.getEmail().contains("@") ||
            !emailWords[1].equals("acme.com")
        )
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        return new AccountId(account.getName(),account.getLastname(),account.getEmail());
    }

    public AccountEntity getPayment(String username) {
//        UserDetails details = (UserDetails) auth.getPrincipal();

        Optional<AccountEntity> accountEntityByName = repository.findAccountEntityByName(username);
        if (accountEntityByName.isEmpty()) {
            System.out.println("no such user");
            throw new UserAuthException();
        }

        return accountEntityByName.orElse(null);
    }
}
