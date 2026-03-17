package finance;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class FinanceTrackerAWT extends Frame implements ActionListener {
    private TextField descField, amountField, dateField, budgetField, summaryMonthField;
    private TextField searchField, searchMonthField;
    private TextArea outputArea;
    private Button addIncomeBtn, addExpenseBtn, setBudgetBtn, showSummaryBtn;
    private Button saveBtn, loadBtn, exportBtn, searchBtn;
    private Choice categoryChoice, searchTypeChoice, searchCategoryChoice;
    private FinanceTracker tracker = new FinanceTracker();
    private static final String DATA_FILE = "finance_data.ser";

    public FinanceTrackerAWT() {
        setTitle("Personal Finance Tracker - Enhanced Version");
        setSize(750, 700);
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Transaction Type Panel
        Panel typePanel = new Panel(new FlowLayout());
        typePanel.setBackground(new Color(200, 230, 255));
        typePanel.setPreferredSize(new Dimension(700, 50));

        addIncomeBtn = new Button("Add Income");
        addIncomeBtn.setBackground(new Color(144, 238, 144));
        addExpenseBtn = new Button("Add Expense");
        addExpenseBtn.setBackground(new Color(255, 182, 193));

        typePanel.add(addIncomeBtn);
        typePanel.add(addExpenseBtn);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        add(typePanel, gbc);

        // Transaction Details Panel
        Panel detailsPanel = new Panel(new GridLayout(4, 2, 5, 5));
        detailsPanel.setBackground(new Color(245, 245, 255));
        detailsPanel.setPreferredSize(new Dimension(700, 120));

        detailsPanel.add(new Label("Description:"));
        descField = new TextField(20);
        detailsPanel.add(descField);

        detailsPanel.add(new Label("Amount:"));
        amountField = new TextField(15);
        detailsPanel.add(amountField);

        detailsPanel.add(new Label("Date (mm-yyyy):"));
        dateField = new TextField(15);
        detailsPanel.add(dateField);

        detailsPanel.add(new Label("Category:"));
        categoryChoice = new Choice();
        categoryChoice.add("Select Category");
        // Add income categories
        for (String category : FinanceTracker.INCOME_CATEGORIES) {
            categoryChoice.add(category);
        }
        // Add expense categories
        for (String category : FinanceTracker.EXPENSE_CATEGORIES) {
            categoryChoice.add(category);
        }
        detailsPanel.add(categoryChoice);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        add(detailsPanel, gbc);

        // Budget Panel
        Panel budgetPanel = new Panel(new FlowLayout());
        budgetPanel.setBackground(new Color(255, 250, 205));
        budgetPanel.setPreferredSize(new Dimension(700, 50));

        budgetPanel.add(new Label("Monthly Budget: Rs."));
        budgetField = new TextField(10);
        setBudgetBtn = new Button("Set Budget");
        setBudgetBtn.setBackground(new Color(255, 215, 0));

        budgetPanel.add(budgetField);
        budgetPanel.add(setBudgetBtn);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 3;
        add(budgetPanel, gbc);

        // Search Panel
        Panel searchPanel = new Panel(new GridLayout(2, 1, 5, 5));
        searchPanel.setBackground(new Color(224, 255, 255));
        searchPanel.setPreferredSize(new Dimension(700, 80));

        // Search row 1
        Panel searchRow1 = new Panel(new FlowLayout());
        searchRow1.add(new Label("Search:"));
        searchField = new TextField(15);
        searchRow1.add(searchField);
        
        searchRow1.add(new Label("Type:"));
        searchTypeChoice = new Choice();
        searchTypeChoice.add("All Types");
        searchTypeChoice.add("Income");
        searchTypeChoice.add("Expense");
        searchRow1.add(searchTypeChoice);
        
        searchRow1.add(new Label("Category:"));
        searchCategoryChoice = new Choice();
        searchCategoryChoice.add("All Categories");
        for (String category : FinanceTracker.INCOME_CATEGORIES) {
            searchCategoryChoice.add(category);
        }
        for (String category : FinanceTracker.EXPENSE_CATEGORIES) {
            searchCategoryChoice.add(category);
        }
        searchRow1.add(searchCategoryChoice);

        // Search row 2
        Panel searchRow2 = new Panel(new FlowLayout());
        searchRow2.add(new Label("Month (mm-yyyy):"));
        searchMonthField = new TextField(10);
        searchRow2.add(searchMonthField);
        
        searchBtn = new Button("Search Transactions");
        searchBtn.setBackground(new Color(175, 238, 238));
        searchRow2.add(searchBtn);

        searchPanel.add(searchRow1);
        searchPanel.add(searchRow2);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        add(searchPanel, gbc);

        // Data Management Panel
        Panel dataPanel = new Panel(new FlowLayout());
        dataPanel.setBackground(new Color(255, 228, 225));
        dataPanel.setPreferredSize(new Dimension(700, 50));

        saveBtn = new Button("Save Data");
        saveBtn.setBackground(new Color(152, 251, 152));
        loadBtn = new Button("Load Data");
        loadBtn.setBackground(new Color(135, 206, 250));
        exportBtn = new Button("Export to CSV");
        exportBtn.setBackground(new Color(221, 160, 221));

        dataPanel.add(saveBtn);
        dataPanel.add(loadBtn);
        dataPanel.add(exportBtn);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        add(dataPanel, gbc);

        // Summary Panel
        Panel summaryPanel = new Panel(new FlowLayout());
        summaryPanel.setBackground(new Color(230, 230, 250));
        summaryPanel.setPreferredSize(new Dimension(700, 50));

        summaryPanel.add(new Label("Summary Month (mm-yyyy):"));
        summaryMonthField = new TextField(10);
        showSummaryBtn = new Button("Show Summary");
        showSummaryBtn.setBackground(new Color(135, 206, 250));

        summaryPanel.add(summaryMonthField);
        summaryPanel.add(showSummaryBtn);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3;
        add(summaryPanel, gbc);

        // Output Area
        outputArea = new TextArea(15, 80);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(250, 250, 250));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(outputArea, gbc);

        // Add action listeners
        addIncomeBtn.addActionListener(this);
        addExpenseBtn.addActionListener(this);
        setBudgetBtn.addActionListener(this);
        showSummaryBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        loadBtn.addActionListener(this);
        exportBtn.addActionListener(this);
        searchBtn.addActionListener(this);

        // Update category choices when transaction type changes
        addIncomeBtn.addActionListener(e -> updateCategoriesForIncome());
        addExpenseBtn.addActionListener(e -> updateCategoriesForExpense());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        // Try to load existing data on startup
        try {
            tracker = FinanceTracker.loadFromFile(DATA_FILE);
            outputArea.setText("Welcome! Previous data loaded successfully.\n");
        } catch (Exception e) {
            outputArea.setText("Welcome! Starting with new finance tracker.\n");
        }

        setVisible(true);
    }

    private void updateCategoriesForIncome() {
        categoryChoice.removeAll();
        for (String category : FinanceTracker.INCOME_CATEGORIES) {
            categoryChoice.add(category);
        }
    }

    private void updateCategoriesForExpense() {
        categoryChoice.removeAll();
        for (String category : FinanceTracker.EXPENSE_CATEGORIES) {
            categoryChoice.add(category);
        }
    }

    // Validation methods (same as before)
    private boolean isValidAmount(String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr);
            return amount > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidDate(String date) {
        return date.matches("\\d{2}-\\d{4}");
    }

    private boolean isValidDescription(String desc) {
        return desc != null && !desc.trim().isEmpty();
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addIncomeBtn) {
                handleAddIncome();
            } else if (e.getSource() == addExpenseBtn) {
                handleAddExpense();
            } else if (e.getSource() == setBudgetBtn) {
                handleSetBudget();
            } else if (e.getSource() == showSummaryBtn) {
                handleShowSummary();
            } else if (e.getSource() == saveBtn) {
                handleSaveData();
            } else if (e.getSource() == loadBtn) {
                handleLoadData();
            } else if (e.getSource() == exportBtn) {
                handleExportCSV();
            } else if (e.getSource() == searchBtn) {
                handleSearch();
            }
        } catch (Exception ex) {
            outputArea.setText("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleAddIncome() {
        String desc = descField.getText();
        String amountStr = amountField.getText();
        String date = dateField.getText();
        String category = categoryChoice.getSelectedItem();
        
        if (!isValidDescription(desc)) {
            outputArea.setText("ERROR: Description cannot be empty!\n");
            return;
        }
        if (!isValidAmount(amountStr)) {
            outputArea.setText("ERROR: Amount must be a positive number!\n");
            return;
        }
        if (!isValidDate(date)) {
            outputArea.setText("ERROR: Date must be in mm-yyyy format (e.g., 01-2024)!\n");
            return;
        }
        if ("Select Category".equals(category)) {
            outputArea.setText("ERROR: Please select a category!\n");
            return;
        }
        
        double amt = Double.parseDouble(amountStr);
        tracker.addTransaction(new Income(desc, amt, date, category));
        
        clearTransactionFields();
        outputArea.setText("SUCCESS: Income added successfully!\n" +
                         "Description: " + desc + "\n" +
                         "Amount: Rs." + String.format("%.2f", amt) + "\n" +
                         "Date: " + date + "\n" +
                         "Category: " + category + "\n");
    }

    private void handleAddExpense() {
        String desc = descField.getText();
        String amountStr = amountField.getText();
        String date = dateField.getText();
        String category = categoryChoice.getSelectedItem();
        
        if (!isValidDescription(desc)) {
            outputArea.setText("ERROR: Description cannot be empty!\n");
            return;
        }
        if (!isValidAmount(amountStr)) {
            outputArea.setText("ERROR: Amount must be a positive number!\n");
            return;
        }
        if (!isValidDate(date)) {
            outputArea.setText("ERROR: Date must be in mm-yyyy format (e.g., 01-2024)!\n");
            return;
        }
        if ("Select Category".equals(category)) {
            outputArea.setText("ERROR: Please select a category!\n");
            return;
        }
        
        double amt = Double.parseDouble(amountStr);
        tracker.addTransaction(new Expense(desc, amt, date, category));
        
        clearTransactionFields();
        outputArea.setText("SUCCESS: Expense added successfully!\n" +
                         "Description: " + desc + "\n" +
                         "Amount: Rs." + String.format("%.2f", amt) + "\n" +
                         "Date: " + date + "\n" +
                         "Category: " + category + "\n");
    }

    private void handleSetBudget() {
        String budgetStr = budgetField.getText();
        
        if (!isValidAmount(budgetStr)) {
            outputArea.setText("ERROR: Budget must be a positive number!\n");
            return;
        }
        
        double limit = Double.parseDouble(budgetStr);
        tracker.setBudget(new Budget(limit));
        
        budgetField.setText("");
        outputArea.setText("SUCCESS: Budget set successfully!\n" +
                         "Monthly Budget: Rs." + String.format("%.2f", limit) + "\n");
    }

    private void handleShowSummary() {
        String month = summaryMonthField.getText();
        
        if (!isValidDate(month)) {
            outputArea.setText("ERROR: Month must be in mm-yyyy format (e.g., 01-2024)!\n");
            return;
        }
        
        outputArea.setText(tracker.getSummary(month));
    }

    private void handleSaveData() {
        try {
            tracker.saveToFile(DATA_FILE);
            outputArea.setText("SUCCESS: All data saved to " + DATA_FILE + "\n" +
                             "Transactions: " + tracker.getTransactions().size() + "\n" +
                             "Data will be automatically loaded when you restart the application.\n");
        } catch (IOException ex) {
            outputArea.setText("ERROR: Failed to save data: " + ex.getMessage() + "\n");
        }
    }

    private void handleLoadData() {
        try {
            tracker = FinanceTracker.loadFromFile(DATA_FILE);
            outputArea.setText("SUCCESS: Data loaded from " + DATA_FILE + "\n" +
                             "Transactions: " + tracker.getTransactions().size() + "\n" +
                             "Budget: " + (tracker.getBudget() != null ? 
                                 "Rs." + String.format("%.2f", tracker.getBudget().getLimit()) : "Not set") + "\n");
        } catch (Exception ex) {
            outputArea.setText("ERROR: Failed to load data: " + ex.getMessage() + 
                             "\nMake sure you have saved data first.\n");
        }
    }

    private void handleExportCSV() {
        try {
            String filename = "finance_export.csv";
            tracker.exportToCSV(filename);
            outputArea.setText("SUCCESS: Data exported to " + filename + "\n" +
                             "You can open this file in Excel or any spreadsheet program.\n" +
                             "Location: " + System.getProperty("user.dir") + "\\" + filename + "\n");
        } catch (IOException ex) {
            outputArea.setText("ERROR: Failed to export CSV: " + ex.getMessage() + "\n");
        }
    }

    private void handleSearch() {
        String keyword = searchField.getText();
        String type = "All Types".equals(searchTypeChoice.getSelectedItem()) ? null : searchTypeChoice.getSelectedItem();
        String category = "All Categories".equals(searchCategoryChoice.getSelectedItem()) ? null : searchCategoryChoice.getSelectedItem();
        String month = searchMonthField.getText().isEmpty() ? null : searchMonthField.getText();
        
        java.util.List<Transaction> results = tracker.searchTransactions(keyword, type, category, month);
        outputArea.setText(tracker.getSearchResults(results));
    }

    private void clearTransactionFields() {
        descField.setText("");
        amountField.setText("");
        dateField.setText("");
        categoryChoice.select(0);
    }

    public static void main(String[] args) {
        new FinanceTrackerAWT();
    }
}