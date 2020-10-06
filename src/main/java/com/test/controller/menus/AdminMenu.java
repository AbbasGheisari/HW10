package com.test.controller.menus;

import com.test.domains.User;

public class AdminMenu extends AbstractMenu {

    public AdminMenu(User admin) {
        super("", new String[]{
                "Change your password",
                "See and Modify your articles",
                "Write new article",
                "Change role of users",
                "Publish or unPublish an article",
                "Delete an article",
                "Create a category",
                "Create a tag",
                "see user address",
                "see information of second database",
                "filter all object of an entity",
                "convert som field from user to UserInfo"
        }, admin);
    }

    @Override
    public void showMenu() {
        while (true) {
            switch (menuAction()) {
                case 1:
                    MenuActions.changePassword(thisMenuUser, sc);
                    break;
                case 2:
                    MenuActions.seeAndModifyArticles(thisMenuUser, sc);
                    break;
                case 3:
                    MenuActions.writeNewArticle(thisMenuUser, sc);
                    break;
                case 4:
                    MenuActions.changeRoleOfUsers(thisMenuUser,sc);
                    break;
                case 5:
                    MenuActions.seeAllWrittenArticles(thisMenuUser, sc);
                    break;
                case 6:
                    MenuActions.deleteArticles(thisMenuUser,sc);
                    break;
                case 7:
                    MenuActions.createNewCategory(thisMenuUser,sc);
                    break;
                case 8:
                    MenuActions.createNewTag(thisMenuUser,sc);
                    break;
                case  9:
                    MenuActions.seeUserAddress();
                    break;
                case 10:
                    MenuActions.printAllInformationOfSampleTest();
                    break;
                case 11:
                    MenuActions.filterAllObjectOfAnEntity();
                    break;
                case 12:
                    MenuActions.convertSomFieldFromUserToUserInfo();
                    break;
                default:
                    return;
            }
        }
    }
}
