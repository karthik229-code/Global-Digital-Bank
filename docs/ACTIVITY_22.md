
# Activity 22: JDBC Foundation

## 1. Objective

Establish a relational database foundation for the Global Digital Bank application using SQLite JDBC.

The implementation introduces database connection abstraction, schema initialization, configurable persistence modes, and JDBC-based repositories.

## 2. Technologies Used

- Java
- JDBC (Java Database Connectivity)
- SQLite
- SQLite JDBC Driver (3.44.1.0)
- Maven
- IntelliJ IDEA

## 3. Implementation

### 3.1 ConnectionProvider

Defines an abstraction for database connection management.

Methods:
- `getConnection()` - obtains a database connection.
- `shutdown()` - closes the connection.
- `getProviderName()` - returns the provider name.

### 3.2 JdbcConnectionProvider

Implements ConnectionProvider using JDBC DriverManager.

It creates and reuses a SQLite database connection and supports connection shutdown and reconnection.

### 3.3 Database Configuration

Database settings are loaded from `config/rules/persistence.properties`.

Configuration:

- `persistence.mode=jdbc`
- `persistence.db.url=jdbc:sqlite:data/gdb.db`
- `persistence.db.driver=org.sqlite.JDBC`

### 3.4 Database Schema

The `schema.sql` resource defines two tables:

**Accounts**
- account_number
- name
- age
- balance
- account_type
- status
- pin
- tenure_years

**Transactions**
- transaction_id
- timestamp
- account_number
- transaction_type
- amount
- balance_after
- status
- description
- source_account
- destination_account

Both tables use `CREATE TABLE IF NOT EXISTS` to support idempotent initialization.

### 3.5 SchemaInitializer

Loads `schema.sql` from the application resources and executes its SQL statements.

It initializes the database schema before JDBC repositories are used.

### 3.6 JDBC Repositories

**JdbcAccountRepository**

Implements AccountRepository and provides:
- Save account
- Find account by ID
- Retrieve all accounts
- Update account
- Delete account
- Check account existence
- Generate the next account number

**JdbcTransactionRepository**

Implements TransactionRepository and provides:
- Save transaction
- Find transactions by account
- Retrieve all transactions
- Clear transaction records

### 3.7 RepositoryFactory

Selects the persistence implementation based on the configured persistence mode.

- `memory` uses in-memory repositories.
- `jdbc` initializes the database and uses JDBC repositories.

This allows the application to switch persistence strategies through configuration.

## 4. Testing

The JDBC connection test verifies:

1. Database connection establishment.
2. Connection provider identification.
3. Successful execution of `SELECT 1`.
4. Existence of the accounts table.
5. Existence of the transactions table.
6. Connection shutdown.
7. Successful reconnection.

### Manual Application Testing

The banking application was also tested for:

- Account creation
- Deposits and withdrawals
- PIN validation
- Money transfers
- Minimum balance validation
- Transaction history
- Account status persistence
- Database persistence after application restart

All tested operations behaved as expected.

## 5. Result

The SQLite JDBC foundation was successfully integrated into the Global Digital Bank application.

Database connections, schema initialization, JDBC repositories, and persistent account and transaction records were verified.

The application can now use either in-memory or SQLite-based persistence through RepositoryFactory.

## 6. Architecture

```text
             AccountService
                   |
                   v
            RepositoryFactory
                   |
          +--------+--------+
          |                 |
       Memory             JDBC
      Repository       Repository
                            |
                            v
                   ConnectionProvider
                            |
                            v
                      SQLite JDBC
                            |
                            v
                       gdb.db
```

## 7. Conclusion

Activity 22 established the JDBC persistence foundation for Global Digital Bank.

The application now supports database-backed account and transaction storage while retaining the existing repository abstraction and in-memory persistence option.
