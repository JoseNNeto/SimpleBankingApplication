package personalproject.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import personalproject.banking.dto.AccountDto;
import personalproject.banking.model.Account;
import personalproject.banking.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // get account by id
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // get all accounts
    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts(){
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    // Create a new account
    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto){
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return ResponseEntity.status(201).body(createdAccount);
    }

    // Deposit money to an account
    @PutMapping("/deposit/{id}")
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request){
        double amount = request.get("amount");

        return ResponseEntity.ok(accountService.deposit(id, amount));
    }

    // Withdraw money from an account
    @PutMapping("/withdraw/{id}")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request){
        double amount = request.get("amount");

        return ResponseEntity.ok(accountService.withdraw(id, amount));
    }

    // Transfer money from one account to another
    @PutMapping("/transfer/{fromId}/{toId}")
    public ResponseEntity<AccountDto> transfer(@PathVariable Long fromId, @PathVariable Long toId, @RequestBody Map<String, Double> request){
        double amount = request.get("amount");

        return ResponseEntity.ok(accountService.transfer(fromId, toId, amount));
    }

    // Delete an account
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
