package org.aes.compare.uiconsole.business;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.utility.ColorfulTextDesign;

import java.util.Stack;

public class LoggerProcessStack {
    private final static Stack<String> stack = new Stack<>();

    public static void popLoop(int val) {
        for (int i = 0; i < val; i++) {
            pop();
        }
    }

    public static String pop() {
        if (!stack.isEmpty()) {
            return stack.pop();
        }
        return ColorfulTextDesign.getErrorColorTextWithPrefix("Logger stack is empty");
    }

    public static String peek() {
        if (!stack.isEmpty()) {
            return stack.peek();
        }
        return ColorfulTextDesign.getErrorColorTextWithPrefix("Logger stack is empty");
    }

    public static void add(String data) {
        stack.add(data);
    }

    public static void addWithInnerPrefix(String data) {
        stack.add(MetaData.INNER_PROCESS_PREFIX + data);
    }

    public static String getAllInOrder() {
        StringBuilder sb = new StringBuilder();
        stack.forEach(e -> {
            sb.append(e);
        });
        return sb.toString();
    }

}
