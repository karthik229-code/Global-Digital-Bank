package org.example;

import org.example.command.TransactionCommand;
import java.util.List;

public interface LogDestination {
    void write(TransactionCommand cmd) throws Exception;
    List<TransactionCommand> readAll() throws Exception;
    void clear() throws Exception;
    String getDestinationName();
}