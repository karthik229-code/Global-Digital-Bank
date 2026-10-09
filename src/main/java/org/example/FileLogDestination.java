
package org.example;
import org.example.command.TransactionCommand;
import org.example.logging.TransactionLog;
import java.util.List;

public class FileLogDestination implements LogDestination {
    private final TransactionLog log;

    public FileLogDestination() {
        this.log = new TransactionLog();
    }

    @Override
    public void write(TransactionCommand cmd) throws Exception {
        log.log(cmd);
    }

    @Override
    public List<TransactionCommand> readAll() throws Exception {
        return log.readAll();
    }

    @Override
    public void clear() throws Exception {
        log.clear();
    }

    @Override
    public String getDestinationName() {
        return "FILE";
    }
}
