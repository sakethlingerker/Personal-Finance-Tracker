package finance;

import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class FinanceTracker implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Budget budget;

    // Predefined categories
    public static final String[] INCOME_CATEGORIES = {
        "Salary", "Bonus", "Investment", "Freelance", "Gift", "Business", "Other Income"
    };
    
    public static final String[] EXPENSE_CATEGORIES = {
        "Food & Dining", "Rent/Mortgage", "Transportation", "Entertainment", 
        "Utilities", "Healthcare", "Shopping", "Education", "Travel", "Other Expenses"
    };

    public void addTransaction(Transaction t) { transactions.add(t); }
    public void setBudget(Budget b) { this.budget = b; }
    public ArrayList<Transaction> getTransactions() { return transactions; }
    public Budget getBudget() { return budget; }

    // Data Persistence Methods
    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    public static FinanceTracker loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (FinanceTracker) ois.readObject();
        }
    }

    // Search and Filter Methods
    public List<Transaction> searchTransactions(String keyword, String type, String category, String month) {
        return transactions.stream()
            .filter(t -> keyword == null || keyword.isEmpty() || 
                        t.getDescription().toLowerCase().contains(keyword.toLowerCase()))
            .filter(t -> type == null || type.isEmpty() || t.getType().equalsIgnoreCase(type))
            .filter(t -> category == null || category.isEmpty() || t.getCategory().equalsIgnoreCase(category))
            .filter(t -> month == null || month.isEmpty() || t.getDate().equals(month))
            .collect(Collectors.toList());
    }

    // Export to CSV
    public void exportToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Type,Description,Amount,Date,Category");
            for (Transaction t : transactions) {
                writer.printf("%s,%s,%.2f,%s,%s%n", 
                    t.getType(), t.getDescription(), t.getAmount(), t.getDate(), t.getCategory());
            }
        }
    }

    // Existing methods with category support
    public double getTotalIncome(String month) {
        double total = 0;
        for (Transaction t : transactions)
            if (t instanceof Income && t.getDate().equals(month)) total += t.getAmount();
        return total;
    }

    public double getTotalExpenses(String month) {
        double total = 0;
        for (Transaction t : transactions)
            if (t instanceof Expense && t.getDate().equals(month)) total += t.getAmount();
        return total;
    }

    public Map<String, Double> getCategoryWiseExpenses(String month) {
        Map<String, Double> categoryMap = new HashMap<>();
        for (Transaction t : transactions) {
            if (t instanceof Expense && t.getDate().equals(month)) {
                categoryMap.merge(t.getCategory(), t.getAmount(), Double::sum);
            }
        }
        return categoryMap;
    }

    public String getSummary(String month) {
        double income = getTotalIncome(month);
        double expenses = getTotalExpenses(month);
        double balance = income - expenses;
        StringBuilder sb = new StringBuilder();
        sb.append("===== Summary for " + month + " =====\n");
        sb.append("Total Income  : Rs." + String.format("%.2f", income) + "\n");
        sb.append("Total Expenses: Rs." + String.format("%.2f", expenses) + "\n");
        sb.append("Net Savings   : Rs." + String.format("%.2f", balance) + "\n");
        
        if (budget != null) {
            sb.append("Budget: Rs." + String.format("%.2f", budget.getLimit()) + "\n");
            if (expenses > budget.getLimit())
                sb.append("WARNING: Over Budget by Rs." + String.format("%.2f", (expenses - budget.getLimit())) + "\n");
            else
                sb.append("OK: Within Budget. Remaining: Rs." + String.format("%.2f", (budget.getLimit() - expenses)) + "\n");
        }
        
        // Add category-wise breakdown
        Map<String, Double> categoryExpenses = getCategoryWiseExpenses(month);
        if (!categoryExpenses.isEmpty()) {
            sb.append("\nExpenses by Category:\n");
            for (Map.Entry<String, Double> entry : categoryExpenses.entrySet()) {
                sb.append("  " + entry.getKey() + ": Rs." + String.format("%.2f", entry.getValue()) + "\n");
            }
        }
        
        sb.append("\nTransactions:\n");
        for (Transaction t : transactions)
            if (t.getDate().equals(month))
                sb.append(t.getType() + " | " + t.getDescription() + " | Rs." + 
                         String.format("%.2f", t.getAmount()) + " | " + t.getCategory() + "\n");
        return sb.toString();
    }

    public String getSearchResults(List<Transaction> results) {
        if (results.isEmpty()) {
            return "No transactions found matching your criteria.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Found " + results.size() + " transaction(s):\n\n");
        double total = 0;
        for (Transaction t : results) {
            sb.append(t.getType() + " | " + t.getDescription() + " | Rs." + 
                     String.format("%.2f", t.getAmount()) + " | " + t.getDate() + " | " + t.getCategory() + "\n");
            total += t.getSignedAmount();
        }
        sb.append("\nNet Total: Rs." + String.format("%.2f", total));
        return sb.toString();
    }
}