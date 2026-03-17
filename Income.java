package finance;
import java.io.Serializable;  

public class Income extends Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    public Income(String description, double amount, String date, String category) {
        super(description, amount, date, category);
    }

    @Override
    public double getSignedAmount() {
        return amount;
    }

    @Override
    public String getType() {
        return "Income";
    }
}