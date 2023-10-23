package account.repository;

import account.model.AccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
    Optional<AccountEntity> findAccountEntityByName(String name);

    Optional<AccountEntity> findAccountEntityByEmail(String email);
}
