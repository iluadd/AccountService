package account.service;

import account.adapter.AccountAdapter;
import account.model.AccountEntity;
import account.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository repository;

    public AccountDetailsServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountEntity user = repository
                .findAccountEntityByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new AccountAdapter(user);
    }

}
