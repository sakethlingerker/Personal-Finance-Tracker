package finance;
import java.io.Serializable;  

// ===== Abstract Class (Abstraction) =====
public abstract class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String description;
    protected double amount;
    protected String date;
    protected String category;

    public Transaction(String description, double amount, String date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    public abstract double getSignedAmount();
    public abstract String getType();

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getCategory() { return category; }
}