package com.test.base.controller.utilities;

import java.util.Scanner;

public final class SingletonScanner {
    private final static Scanner sc = new Scanner(System.in);

    private SingletonScanner() {
    }

    public static final synchronized Scanner getScanner() {
        return sc;
    }

    public static synchronized final String readParagraph() {
        String paragraph = "";
        String line = "";
        do {
            paragraph += line + "\n";
            line = sc.nextLine();
        } while (!line.equalsIgnoreCase("\\."));
        return paragraph.trim();
    }
}
