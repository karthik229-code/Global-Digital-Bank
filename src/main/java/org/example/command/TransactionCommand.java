package org.example.command;

import org.example.Transaction;
import java.io.Serializable;

public interface TransactionCommand extends Serializable {
    void execute() throws Exception;

    Transaction getTransaction();
}