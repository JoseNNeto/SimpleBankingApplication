package personalproject.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import personalproject.banking.model.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String accountHolderName;
    private double balance;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.accountHolderName = account.getAccountHolderName();
        this.balance = account.getBalance();
    }
}
