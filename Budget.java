package finance;

import java.io.Serializable;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    private double monthlyLimit;

    public Budget(double limit) {
        this.monthlyLimit = limit;
    }

    public double getLimit() { return monthlyLimit; }
    public void setLimit(double limit) { this.monthlyLimit = limit; }
}