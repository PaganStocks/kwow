package uk.co.fragiletechnologies.kwow.exception;

public class InsufficientBalanceException extends RuntimeException {
    private final int amount;
    private final long currentBalance;

    public InsufficientBalanceException(int amount, long currentBalance) {
        this.amount = amount;
        this.currentBalance = currentBalance;
    }

    public int getAmount() {
        return amount;
    }

    public long getCurrentBalance() {
        return currentBalance;
    }
}
