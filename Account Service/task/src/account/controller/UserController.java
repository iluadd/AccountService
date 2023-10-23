package account.controller;

import account.exceptions.UserAuthException;
import account.exceptions.UserExistException;
import account.model.Account;
import account.model.AccountEntity;
import account.repository.AccountRepository;
import account.service.AccountService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class UserController {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository repository;

    public UserController(AccountService accountService, PasswordEncoder passwordEncoder, AccountRepository repository) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @PostMapping("/api/auth/signup")
    public Account getAccountInfo(@RequestBody Account account) {

        Optional<AccountEntity> accountEntityByEmail = repository.findAccountEntityByEmail(account.getEmail().toLowerCase());
        if (accountEntityByEmail.isPresent()) {
            System.out.println("this email already exists");
            throw new UserExistException();
        }

        var Account = new AccountEntity();
        Account.setName(account.getName());
        Account.setEmail(account.getEmail().toLowerCase());
        Account.setLastname(account.getLastname());
        Account.setPassword(passwordEncoder.encode(account.getPassword()));

        repository.save(Account);
        System.out.println("user registered");

        return accountService.getAccountInfo(account);
    }

//    public AccountEntity getPayment(@RequestBody Account account) {
//        Optional<AccountEntity> accountEntityByName = repository.findAccountEntityByName(account.getName());
//        if (accountEntityByName.isEmpty()) {
//            System.out.println("no such user");
//            throw new UserAuthException();
//        }
//
//        return accountEntityByName.orElse(null);
//    }
//    @GetMapping("/api/empl/payment")
//    public AccountEntity getPayment(Authentication auth){
//        return accountService.getPayment(auth);
//    }

    @GetMapping("/api/empl/payment")
    public AccountEntity getPayment(@AuthenticationPrincipal UserDetails userDetails){

        System.out.println("username " + userDetails.getUsername());
        return accountService.getPayment(userDetails.getUsername());
    }
}
