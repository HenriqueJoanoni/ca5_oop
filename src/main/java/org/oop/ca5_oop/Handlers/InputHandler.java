package org.oop.ca5_oop.Handlers;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;

public class InputHandler {

    public void handleStringInput(Scanner kb, String fieldName, Consumer<String> setter) {
        while (true) {
            System.out.print("Enter " + fieldName + " to search: ");
            String input = kb.nextLine().trim();

            if (!input.isEmpty()) {
                setter.accept(input);
                return;
            }
            System.out.println("Error: " + fieldName + " cannot be empty!");
        }
    }

    public void handleNumericInput(Scanner kb, String fieldName, String typeName, Runnable setter) {
        while (true) {
            try {
                System.out.print("Enter " + fieldName + ": ");
                setter.run();
                kb.nextLine();
                return;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid " + typeName + ".");
                kb.nextLine();
            }
        }
    }
}
