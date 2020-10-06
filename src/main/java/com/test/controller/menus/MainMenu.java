package com.test.controller.menus;

public class MainMenu extends AbstractMenu {

    public MainMenu() {
        super("main menu", new String[]{
                "Login user",
                "SignUp user",
                "See articles"
        }, null);
    }

    @Override
    public void showMenu() {
        while (true) {
            switch (menuAction()) {
                case 1:
                    MenuActions.logInUser(sc);
                    break;
                case 2:
                    MenuActions.signUpUser(sc);
                    break;
                case 3:
                    MenuActions.seePublishedArticles(sc);
                    break;
                default:
                    return;
            }
        }
    }
}

