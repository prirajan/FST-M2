package Activities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

@Test
    void notEnoughFunds(){
    BankAccount account = new BankAccount(9);
    //Assertion for Not Enough Funds exception
    assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10),"Balance must be greater than amount of withdrawal");
    }

@Test
    void enoughFunds(){
    BankAccount account = new BankAccount(100);
   //assertion for enough funds
    assertDoesNotThrow(() -> account.withdraw(100));
}
}
