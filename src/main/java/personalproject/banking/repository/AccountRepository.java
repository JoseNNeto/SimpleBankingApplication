package personalproject.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import personalproject.banking.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountHolderName(String accountHolderName);
    
}
