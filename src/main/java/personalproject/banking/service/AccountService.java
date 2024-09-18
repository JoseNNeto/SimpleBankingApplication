package personalproject.banking.service;

import java.util.List;
import org.springframework.stereotype.Service;

import personalproject.banking.dto.AccountDto;
import personalproject.banking.mapper.AccountMapper;
import personalproject.banking.model.Account;
import personalproject.banking.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto getAccountById(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.mapToAccountDto(account);
    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    public AccountDto createAccount(AccountDto accountdto){
        Account account = AccountMapper.mapToAccount(accountdto);

        Account existingAccount = accountRepository.findByAccountHolderName(account.getAccountHolderName());
        if(existingAccount != null){
            throw new RuntimeException("Account already exists");
        }

        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    public AccountDto deposit(Long id, double amount){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);

        Account updatedAccount = accountRepository.save(account);
        
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    public AccountDto withdraw(Long id, double amount){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));

        double newBalance = account.getBalance() - amount;
        if(newBalance < 0){
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(newBalance);

        Account updatedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    public AccountDto transfer(Long fromAccountId, Long toAccountId, double amount){
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() -> new RuntimeException("Account not found"));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() -> new RuntimeException("Account not found"));

        double newBalanceFrom = fromAccount.getBalance() - amount;
        if(newBalanceFrom < 0){
            throw new RuntimeException("Insufficient balance");
        }
        fromAccount.setBalance(newBalanceFrom);

        double newBalanceTo = toAccount.getBalance() + amount;
        toAccount.setBalance(newBalanceTo);

        Account updatedFromAccount = accountRepository.save(fromAccount);
        Account updatedToAccount = accountRepository.save(toAccount);

        return AccountMapper.mapToAccountDto(updatedFromAccount);
    }

    public AccountDto deleteAccount(Long id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        accountRepository.delete(account);
        return AccountMapper.mapToAccountDto(account);
    }
}
