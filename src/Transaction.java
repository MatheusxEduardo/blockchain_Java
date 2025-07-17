public class Transaction {
    private final String sender;
    private final String recipient;
    private final double amount;

    public Transaction(String sender, String recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{sender=" + sender + ", recipient=" + recipient + ", amount=" + amount + "}";
    }
}