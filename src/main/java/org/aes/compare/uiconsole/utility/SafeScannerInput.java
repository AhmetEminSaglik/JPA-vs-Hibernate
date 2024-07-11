package org.aes.compare.uiconsole.utility;

import org.aes.compare.orm.utility.ColorfulTextDesign;

import java.util.List;
import java.util.Scanner;

public class SafeScannerInput {
    private static Scanner scanner = new Scanner(System.in);

    public int convertInputToListIndexValue(String input, List<?> list) {
        Integer integer = getInt(input);
        if (integer != null) {
            int num = --integer;
            boolean result = isNumberSuitableListRange(num, list);
            if (result) {
                return num;
            }
        }
        return -1;

    }

    public static Integer getInt(String input) {
        try {
            int num = Integer.parseInt(input);
            return num;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid Index Input. Please try again.");
            return null;
        }
    }

    private boolean isNumberSuitableListRange(int num, List<?> list) {
        if (num >= 0 && num < list.size()) {
            return true;
        }
        System.out.println("Invalid Index range. Please choose number between 0-" + (list.size() - 1));
        return false;
    }

    public static String getStringInput() {
        String input = scanner.nextLine();
        if (input.trim().isEmpty()) {
            System.out.println(ColorfulTextDesign.getErrorColorText("Blank is not allowed. Please type something"));
            return getStringInput();
        }
        return input;
    }

    public static int getIntInput() {
        String input = scanner.nextLine();
        Integer num = getInt(input);
        if (num == null) {
            return getIntInput();
        }
        return num;
    }

}
