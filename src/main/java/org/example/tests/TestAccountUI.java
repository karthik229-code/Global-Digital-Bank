
package org.example.tests;

import org.example.AccountService;
import org.example.AccountUI;
import org.example.FileLogDestination;
import org.example.TransactionLogger;
import org.example.TransferService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class TestAccountUI {

    public static void main(String[] args) throws Exception {
        System.out.println("========================================");
        System.out.println("       ACTIVITY 20 - ACCOUNT UI TEST");
        System.out.println("========================================");

        String simulatedInput = String.join(
                System.lineSeparator(),
                "8",
                ""
        );

        InputStream originalInput = System.in;
        PrintStream originalOutput = System.out;

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setIn(new ByteArrayInputStream(
                    simulatedInput.getBytes(StandardCharsets.UTF_8)
            ));

            System.setOut(new PrintStream(output, true, "UTF-8"));

            TransactionLogger logger =
                    new TransactionLogger(new FileLogDestination());

            TransferService transferService = new TransferService();

            AccountService service =
                    new AccountService(logger, transferService);

            AccountUI ui = new AccountUI(service);
            ui.start();

        } finally {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }

        String result = output.toString("UTF-8");

        if (result.contains("GLOBAL DIGITAL BANK")
                && result.contains("8. Exit")
                && result.contains("Thank you! Goodbye.")) {
            System.out.println("PASS: AccountUI displayed the menu and exited.");
        } else {
            System.out.println("FAIL: AccountUI integration test failed.");
            System.out.println(result);
            throw new AssertionError("AccountUI test failed.");
        }

        System.out.println("Activity 20 UI integration test completed.");
    }
}
