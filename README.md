# 🪙 Finfolk — Personal Finance Tracker

A clean, warm full-stack personal finance tracker built with **Spring Boot + H2 Database** (backend) and **HTML + Tailwind CSS** (frontend).

---

## ✨ Features

| Feature | Description |
|---|---|
| 📊 **Dashboard** | Net balance, income/expense totals, this month's stats |
| 💸 **Transactions** | Add, view, filter, and delete income & expense entries |
| 🎯 **Budgets** | Set monthly spending limits per category with progress bars |
| 📈 **Analytics** | Savings rate, category breakdown, income vs expense ratio |
| 🗄️ **H2 Database** | File-based embedded DB — data persists between restarts |
| 🌐 **REST API** | Clean JSON API at `localhost:8080/api` |

---

## 🏗️ Tech Stack

**Backend:**
- Java 17
- Spring Boot 3.2
- Spring Data JPA
- H2 Database (file-based)
- Maven

**Frontend:**
- HTML5
- Tailwind CSS (CDN)
- Vanilla JavaScript
- Google Fonts (Playfair Display + DM Sans)

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+

### 1. Start the Backend

```bash
cd backend
mvn spring-boot:run
```

The API will be live at: **http://localhost:8080**

> The H2 database file (`financedb.mv.db`) is created automatically in the `backend/` folder.

### 2. Open the Frontend

Open `frontend/index.html` directly in your browser:

```bash
# macOS
open frontend/index.html

# Linux
xdg-open frontend/index.html

# Windows
start frontend/index.html
```

Or serve it with a simple HTTP server:
```bash
cd frontend
python3 -m http.server 3000
# Then visit http://localhost:3000
```

---

## 📡 API Endpoints

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/transactions` | Get all transactions |
| GET | `/api/transactions?type=INCOME` | Filter by type |
| GET | `/api/transactions/{id}` | Get by ID |
| POST | `/api/transactions` | Create transaction |
| PUT | `/api/transactions/{id}` | Update transaction |
| DELETE | `/api/transactions/{id}` | Delete transaction |
| GET | `/api/transactions/dashboard` | Dashboard stats |

### Budgets
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/budgets` | Get all budgets |
| POST | `/api/budgets` | Create/update budget |
| DELETE | `/api/budgets/{id}` | Delete budget |
| GET | `/api/budgets/progress` | Budget progress with spending |

### H2 Console
Visit `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./financedb`
- Username: `sa`
- Password: *(empty)*

---

## 📁 Project Structure

```
finance-tracker/
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/finance/tracker/
│       ├── FinanceTrackerApplication.java
│       ├── controller/
│       │   ├── TransactionController.java
│       │   └── BudgetController.java
│       ├── model/
│       │   ├── Transaction.java
│       │   └── Budget.java
│       ├── repository/
│       │   ├── TransactionRepository.java
│       │   └── BudgetRepository.java
│       └── service/
│           ├── TransactionService.java
│           └── BudgetService.java
└── frontend/
    └── index.html
```

---

## 📦 Sample Transaction JSON

```json
{
  "title": "Monthly Salary",
  "amount": 50000,
  "type": "INCOME",
  "category": "Salary",
  "date": "2024-01-15",
  "note": "January paycheck"
}
```

---

## 🎨 Theme

Warm notebook aesthetic — cream background, ink dark sidebar, sage green for income, rust red for expenses, and gold accents throughout.
