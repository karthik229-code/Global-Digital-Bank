# Activity 23: JdbcAccountRepository (CRUD + Atomic Transfer)

## 1. Objective

Implement a JDBC-based AccountRepository using SQLite.

The implementation supports:
- Create, Read, Update and Delete (CRUD) operations.
- PreparedStatement-based SQL queries.
- ResultSet mapping to account objects.
- Atomic multi-account transfers.
- Transaction rollback when an operation fails.
- DataAccessException for handling SQL errors.

## 2. Architecture

The repository layer separates database operations from business logic.

Main components:

- AccountRepository: Defines account persistence operations.
- InMemoryAccountRepository: Stores accounts in memory.
- JdbcAccountRepository: Stores accounts in SQLite.
- RepositoryFactory: Selects the repository implementation.
- DataAccessException: Wraps database access errors.

## 3. CRUD Operations

### Create
The save() method inserts an account into the accounts table.

### Read
findById() retrieves an account using its account number.

findAll() retrieves all stored accounts.

### Update
update() modifies an existing account's information.

### Delete
delete() removes an account using its account number.

### Utility Operations
exists() checks whether an account exists.

nextAccountNumber() generates the next available account number.

## 4. Prepared Statements

PreparedStatement is used for database operations.

Benefits:
- Prevents SQL injection.
- Separates SQL queries from parameter values.
- Supports efficient query execution.

## 5. Atomic Transfer

The transfer() method persists the updated balances of two accounts.

The operation follows these steps:

1. Obtain a database connection.
2. Disable auto-commit.
3. Update the source account balance.
4. Update the destination account balance.
5. Commit the transaction if both updates succeed.
6. Roll back if either update fails.
7. Restore the original auto-commit setting.

This ensures that both database balance updates succeed together or are rolled back together.

## 6. Exception Handling

DataAccessException extends RuntimeException.

It wraps SQL exceptions and preserves the original cause.

This separates database errors from business-level account exceptions.

## 7. Testing

TestJdbcAccountRepository validates:

- Account creation and retrieval.
- Account updates.
- Account deletion.
- Account existence.
- Atomic transfer.
- Rollback when a transfer fails.

## 8. Conclusion

Activity 23 extends the repository pattern with JDBC-based CRUD operations and transactional transfer persistence.

Prepared statements, ResultSet mapping, exception handling and transaction boundaries improve the reliability of database operations.