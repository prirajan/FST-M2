package Activities;

public class NotEnoughFundsException extends RuntimeException{

    public NotEnoughFundsException(Integer amount, Integer balance)
    {
        super("Attempted to withdraw " + amount + " with balance of " + balance);
    }
}
