package com.test.controller.menus;

import com.test.base.controller.menus.Menu;
import com.test.domains.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractMenu implements Menu {
    protected final User thisMenuUser;
    protected final Scanner sc;
    private final String title;
    private final List<String> options;

    public AbstractMenu(String title, String[] options, User user) {
        this.sc = SingletonScanner.getScanner();
        this.options = new ArrayList<>(List.of(options));
        this.title = title;
        this.thisMenuUser = user;
    }

    @Override
    public int menuAction() {
        while (true) {
            System.out.printf("%n====== %s ======%n", title.toUpperCase());
            for (int i = 1; i <= options.size(); i++) {
                System.out.printf("%d. %s%n", i, options.get(i - 1));
            }
            System.out.print("Enter your choice (or other number for \"exit\"): ");
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wrong format, Enter a number please!");
                sc.nextLine();
            }
        }
    }
}
