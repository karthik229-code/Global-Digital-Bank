package org.example;

import org.example.command.TransactionCommand;
import java.util.List;

public class DatabaseLogDestination implements LogDestination {
    private final SimulatedDatabase db;

    public DatabaseLogDestination(SimulatedDatabase db) {
        this.db = db;
    }

    @Override
    public void write(TransactionCommand cmd) {
        db.insert("transaction_log", cmd);
    }

    @Override
    public List<TransactionCommand> readAll() {
        return db.selectAll("transaction_log");
    }

    @Override
    public void clear() {
        db.deleteAll("transaction_log");
    }

    @Override
    public String getDestinationName() {
        return "DATABASE";
    }
}