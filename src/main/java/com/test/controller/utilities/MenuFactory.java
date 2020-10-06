package com.test.controller.utilities;

import com.test.base.controller.menus.Menu;
import com.test.controller.menus.AdminMenu;
import com.test.controller.menus.MainMenu;
import com.test.controller.menus.WriterMenu;
import com.test.domains.User;

public class MenuFactory {
    private MenuFactory() {
    }

    public static Menu getMenu(User user) {
        if (user == null)
            return new MainMenu();
        else if (user.getRole().getTitle().equals("admin"))
            return new AdminMenu(user);
        else
            return new WriterMenu(user);
    }
}
