package org.aes.compare.uiconsole.utility;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SafeScannerInput {
    Scanner scanner = new Scanner(System.in);

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

    public Integer getInt(String input) {
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
}
