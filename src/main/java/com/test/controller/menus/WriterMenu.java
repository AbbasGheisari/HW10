package com.test.controller.menus;

import com.test.domains.User;

public class WriterMenu extends AbstractMenu {

    public WriterMenu(User writer) {
        super(writer.getUserName() + "'s account", new String[]{
                "Change your password",
                "See and Modify your articles",
                "Write new article"
        }, writer);
    }

    @Override
    public void showMenu() {
        while (true) {
            switch (menuAction()) {
                case 1:
                    MenuActions.changePassword(thisMenuUser,sc);
                    break;
                case 2:
                    MenuActions.seeAndModifyArticles(thisMenuUser,sc);
                    break;
                case 3:
                    MenuActions.writeNewArticle(thisMenuUser,sc);
                    break;
                default:
                    return;
            }
        }
    }
}

