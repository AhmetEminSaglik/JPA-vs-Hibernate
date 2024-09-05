package org.ahmeteminsaglik.uiconsole.business;

import org.ahmeteminsaglik.metadata.MetaData;
import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;

import java.util.List;
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

    public static String getCoreProcess(int val) {
        StringBuilder sb = new StringBuilder();
        List<String> subList = stack.subList(0, val);
        for (int i = 0; i < val; i++) {
            sb.append(subList.get(i));
        }
        return sb.toString();
    }

}
