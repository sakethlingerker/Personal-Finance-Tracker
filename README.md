# Personal Finance Tracker

A comprehensive Java-based desktop application for managing personal finances with advanced features including transaction categorization, data persistence, search functionality, and CSV export capabilities.

This project demonstrates practical implementation of Object-Oriented Programming principles with Java AWT for GUI development, providing a robust solution for personal financial management.

---

## 🌟 Key Features

### 💰 Financial Management
- **Income & Expense Tracking:** Add and categorize financial transactions with predefined categories.
- **Real-time Balance Calculation:** Automatic balance updates after each transaction.
- **Budget Management:** Set monthly spending limits with category-wise tracking.
- **Transaction History:** Complete record of all financial activities with timestamps.

### 🔍 Advanced Functionality
- **Smart Search & Filtering:** Multi-criteria search by keyword, type, category, and date.
- **Data Persistence:** Automatic saving and loading using Java Serialization.
- **CSV Export:** Export transaction data to CSV format for external analysis.
- **Input Validation:** Comprehensive validation for data integrity and error prevention.

### 🎯 User Experience
- **Intuitive GUI:** Clean AWT-based interface with organized components.
- **Category Management:** Dynamic category selection based on transaction type.
- **Real-time Updates:** Immediate UI refresh after each operation.
- **Error Handling:** User-friendly error messages and graceful recovery.

---

## 💻 Technology Stack

| Category               | Technology               | Purpose                          |
|------------------------|--------------------------|----------------------------------|
| Programming Language   | Java 17+                 | Core application development     |
| GUI Framework          | Java AWT                 | Desktop user interface           |
| Data Storage           | Java Serialization       | Object persistence               |
| Data Export            | CSV Format               | External data portability        |
| Development Environment| VS Code                  | Code editing and debugging       |
| Build Tools            | Java Compiler (`javac`)  | Application compilation          |

---

## ⚙️ Setup and Installation

### 🧱 Prerequisites
Ensure you have:
- Java JDK 17 or higher
- Windows / Linux / macOS operating system
- Minimum 2GB RAM
- 500MB free disk space

### 🔧 Installation Steps
```bash
# Clone or download the project and extract to your preferred directory

# Navigate to Project Directory
cd Personal-Finance-Tracker

# Compile the Application
javac finance/*.java

# Run the Application
java finance.FinanceTrackerAWT
