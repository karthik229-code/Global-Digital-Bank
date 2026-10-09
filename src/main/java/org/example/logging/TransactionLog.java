package org.example.logging;

import org.example.command.TransactionCommand;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionLog {
    private static final Path LOG_FILE =
            Paths.get("data", "transactions.ser");

    public void log(TransactionCommand cmd) throws IOException {
        Files.createDirectories(LOG_FILE.getParent());

        boolean append = Files.exists(LOG_FILE)
                && Files.size(LOG_FILE) > 0;

        try (ObjectOutputStream output = append
                ? new AppendableObjectOutputStream(
                new FileOutputStream(LOG_FILE.toFile(), true))
                : new ObjectOutputStream(
                new FileOutputStream(LOG_FILE.toFile()))) {

            output.writeObject(cmd);
            output.flush();
        }
    }

    public List<TransactionCommand> readAll()
            throws IOException, ClassNotFoundException {
        List<TransactionCommand> commands = new ArrayList<>();

        if (!Files.exists(LOG_FILE) || Files.size(LOG_FILE) == 0) {
            return commands;
        }

        try (ObjectInputStream input =
                     new ObjectInputStream(
                             new FileInputStream(LOG_FILE.toFile()))) {
            while (true) {
                try {
                    Object object = input.readObject();

                    if (object instanceof TransactionCommand) {
                        commands.add((TransactionCommand) object);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        }

        return commands;
    }

    public void clear() throws IOException {
        Files.deleteIfExists(LOG_FILE);
    }

    private static class AppendableObjectOutputStream
            extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream output)
                throws IOException {
            super(output);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset();
        }
    }
}