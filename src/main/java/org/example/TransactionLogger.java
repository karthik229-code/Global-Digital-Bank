package org.example;

import org.example.command.TransactionCommand;
import java.util.List;

public class TransactionLogger {
    protected LogDestination destination;

    public TransactionLogger(LogDestination destination) {
        this.destination = destination;
    }

    public void setDestination(LogDestination destination) {
        this.destination = destination;
    }

    public void log(TransactionCommand cmd) throws Exception {
        destination.write(cmd);
    }

    public List<TransactionCommand> readAll() throws Exception {
        return destination.readAll();
    }

    public void clear() throws Exception {
        destination.clear();
    }

    public String getDestinationName() {
        return destination.getDestinationName();
    }
}