package com.test.controller.menus;

import com.test.base.controller.utilities.NoValidDataException;
import com.test.controller.utilities.MenuFactory;
import com.test.controller.utilities.SingletonWriterRole;
import com.test.domains.*;
import com.test.repositories.impl.*;
import com.test.services.*;
import com.test.services.impl.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class MenuActions {
    static Scanner input = new Scanner(System.in);
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    private static final EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("my-persistence-unit1");
    private static final EntityManager em = emf.createEntityManager();
    private static final EntityManager em1 = emf1.createEntityManager();

    private static final ArticleService articleService = new ArticleServiceImpl(new ArticleRepositoryImpl(em));
    private static final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl(em));
    private static final RoleService roleService = new RoleServiceImpl(new RoleRepositoryImpl(em));
    private static final TagService tagService = new TagServiceImpl(new TagRepositoryImpl(em));
    private static final UserService userService = new UserServiceImpl(new UserRepositoryImpl(em));
    private static final AddressService ADDRESS_SERVICE = new AddressServiceImpl(new AddressRepositoryImpl(em));
    private static final SampleTestService SAMPLE_TEST_SERVICE=new SampleTestServiceImpl(new SampleTestRepositoryImpl(em1));


    public static void startApp() {
        MenuFactory.getMenu(null).showMenu();
    }

    public static void endApp() {
        em.close();
        emf.close();
    }

    static void seePublishedArticles(Scanner sc) {
        List<Article> articles = articleService.findAll(Article.class, "Article.findAllPublished");
        if (articles.size() == 0) {
            System.out.println("There is not any published article yet!");
            return;
        }
        while (true) {
            int choice = printArticles(articles, sc);
            try {
                articles.get(choice - 1).printCompleteInformation();
                System.out.print("Press any key to back!");
                sc.next();
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }

    static int printArticles(List<Article> articles, Scanner sc) {
        while (true) {
            for (int i = 1; i <= articles.size(); i++) {
                System.out.printf("%n%d. %s", i, articles.get(i - 1));
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

    static void logInUser(Scanner sc) {
        while (true) {
            System.out.printf("%n====== %s ======%n", "LOGIN USER");
            System.out.print("UserName: ");
            String userName = sc.next();
            if (userName.equalsIgnoreCase("esc")) {
                return;
            }
            Optional<User> oUser = userService.findUserByUserName(userName);
            if (oUser.isPresent()) {
                User user = oUser.get();
                System.out.print("Password: ");
                String password = sc.next();
                if (password.equals(user.getPassword())) {
                    MenuFactory.getMenu(user).showMenu();
                    break;
                } else {
                    System.out.println("Wrong password!");
                }
            } else {
                System.out.println("There is not a User with this UserName!");
            }
        }
    }

    public static void signUpUser(Scanner sc) {
        while (true) {
            System.out.printf("%n====== %S ======%n", "SIGN UP USER");
            try {
                User user = new User();
                System.out.print("UserName: ");
                String userName = sc.next();
                if (userName.equalsIgnoreCase("esc")) {
                    return;
                }
                user.setUserName(userName);
                System.out.print("National Code: ");
                user.setNationalCode(sc.next());
                user.setPassword(user.getNationalCode());
                System.out.print("Birthday (YYYY-MM-DD): ");
                user.setBirthDay(sc.next());
                user.setRole(SingletonWriterRole.getWriterRole(roleService));
                Optional<User> oUser = userService.save(user);
                if (oUser.isPresent()) {
                    System.out.println("You were SignUp correctly, please login!");
                    break;
                } else {
                    System.out.println("There is a User with this information already!");
                }
            } catch (NoValidDataException e) {
                System.out.println("Wrong entered data format for " + e.getMessage() + "!");
            }
        }
    }

    public static void changePassword(User user, Scanner sc) {
        while (true) {
            System.out.printf("%n====== %s ======%n", "CHANGE PASSWORD");
            try {
                System.out.print("Old Password: ");
                String oldPassword = sc.next();
                if (oldPassword.equalsIgnoreCase("esc")) {
                    return;
                } else if (!oldPassword.equals(user.getPassword())) {
                    System.out.println("Wrong Password!");
                    continue;
                }
                System.out.print("New Password: ");
                String newPassword = sc.next();
                if (newPassword.equalsIgnoreCase("esc")) {
                    return;
                }
                user.setPassword(newPassword);
                Optional<User> oUser = userService.update(user);
                if (oUser.isPresent()) {
                    System.out.println("Your password was update successfully!");
                    break;
                } else {
                    System.out.println("There is a problem, We can not update your password!");
                }
            } catch (NoValidDataException e) {
                System.out.println("Wrong entered data format for " + e.getMessage() + "!");
            }
        }
    }

    public static void seeAndModifyArticles(User user, Scanner sc) {
        List<Article> articles = new ArrayList<>(user.getArticles());
        if (articles.size() == 0) {
            System.out.println("You have not any article yet!");
            return;
        }
        while (true) {
            int number = printUserArticles(articles, sc);
            try {
                modifyUserArticle(articles.get(number - 1), sc);
                System.out.print("Press any key to back!");
                sc.next();
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }

    private static int printUserArticles(List<Article> articles, Scanner sc) {
        while (true) {
            for (int i = 1; i <= articles.size(); i++) {
                System.out.printf("%n%d. %s", i, articles.get(i - 1));
            }
            System.out.print("Enter your choice for modify (other number for \"exit\"): ");
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wrong format, Enter a number please!");
                sc.nextLine();
            }
        }
    }

    private static void modifyUserArticle(Article article, Scanner sc) {
        while (true) {
            System.out.printf("%n====== %s ======%n", "MODIFY ARTICLE");
            try {
                System.out.println("Old Title: " + article.getTitle());
                System.out.print("New Title: ");
                String newTitle = sc.next() + sc.nextLine();
                if (newTitle.equalsIgnoreCase("esc")) {
                    return;
                } else if (!newTitle.equalsIgnoreCase("Pass")) {
                    article.setTitle(newTitle);
                    article.setLastUpdateDate(new Date(System.currentTimeMillis()));
                    Optional<Article> oArticle = articleService.update(article);
                    if (oArticle.isPresent()) {
                        System.out.println("Your article title was updated successfully!");
                        break;
                    } else {
                        System.out.println("There is a problem, We can not update your article title!");
                        continue;
                    }
                }
                System.out.println("Old Brief: " + article.getBrief());
                System.out.print("New Brief: ");
                String newBrief = input.next();
                if (newBrief.endsWith("esc")) {
                    return;
                } else if (!newBrief.endsWith("pass")) {
                    article.setBrief(newBrief);
                    article.setLastUpdateDate(new Date(System.currentTimeMillis()));
                    Optional<Article> oArticle = articleService.update(article);
                    if (oArticle.isPresent()) {
                        System.out.println("Your article brief was updated successfully!");
                        break;
                    } else {
                        System.out.println("There is a problem, We can not update your article brief!");
                        continue;
                    }
                }
                System.out.println("Old Content: " + article.getContent());
                System.out.print("New Content: ");
                String newContent = input.next();
                if (newContent.endsWith("esc")) {
                    return;
                } else if (!newContent.endsWith("pass")) {
                    article.setContent(newContent);
                    article.setLastUpdateDate(new Date(System.currentTimeMillis()));
                    Optional<Article> oArticle = articleService.update(article);
                    if (oArticle.isPresent()) {
                        System.out.println("Your article content was updated successfully!");
                        break;
                    } else {
                        System.out.println("There is a problem, We can not update your article content!");
                    }
                }
                return;
            } catch (NoValidDataException e) {
                System.out.println("Wrong entered data format for " + e.getMessage() + "!");
            }
        }
    }

    public static void writeNewArticle(User user, Scanner sc) {
        List<Category> categories = categoryService.findAll(Category.class, "Category.findAll");
        List<Tag> tags = tagService.findAll(Tag.class, "Tag.findAll");
        if (tags.size() == 0 || categories.size() == 0) {
            System.out.println("There is not any category or tag yet.\nso you can't create any article!");
            return;
        }
        Article article = new Article();
        System.out.printf("%n====== %s ======", "CREATE NEW ARTICLE");
        while (true) {
            try {
                while (true) {
                    int cNumber = printAllCategories(categories, sc);
                    article.getCategories().add(categories.get(cNumber - 1));
                    System.out.print("Do you want add another category? ");
                    String cAnswer = sc.next();
                    if (cAnswer.equalsIgnoreCase("esc")) {
                        return;
                    } else if (!cAnswer.equalsIgnoreCase("y")) {
                        break;
                    }
                }
                while (true) {
                    int tNumber = printAllTags(tags, sc);
                    article.getTags().add(tags.get(tNumber - 1));
                    System.out.print("Do you want add another tag? ");
                    String tAnswer = sc.next();
                    if (tAnswer.equalsIgnoreCase("esc")) {
                        return;
                    } else if (!tAnswer.equalsIgnoreCase("y")) {
                        break;
                    }
                }
                while (true) {
                    try {
                        System.out.print("Title: ");
                        String title = sc.next() + sc.nextLine();
                        if (title.equalsIgnoreCase("esc")) {
                            return;
                        }
                        article.setTitle(title);
                        System.out.print("Brief: ");
                        String brief = input.next();
                        if (brief.endsWith("esc")) {
                            return;
                        }
                        article.setBrief(brief);
                        System.out.print("Content: ");
                        String content = input.next();
                        if (content.endsWith("esc")) {
                            return;
                        }
                        article.setContent(content);
                        article.setCreateDate(new Date(System.currentTimeMillis()));
                        article.setPublished(false);
                        article.addWriter(user);
                        addOtherWriters(article, sc);
                        Optional<Article> oArticle = articleService.save(article);
                        if (oArticle.isPresent()) {
                            System.out.println("Your article was created correctly!");
                            return;
                        } else {
                            System.out.println("There is a article with this title already!");
                        }
                    } catch (NoValidDataException e) {
                        System.out.println("Wrong entered data format for " + e.getMessage() + "!");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }

    private static int printAllCategories(List<Category> categories, Scanner sc) {
        while (true) {
            for (int i = 1; i <= categories.size(); i++) {
                System.out.printf("%n%d. %s%n", i, categories.get(i - 1));
            }
            System.out.print("Enter your choice for category (other number for \"exit\"): ");
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wrong format, Enter a number please!");
                sc.nextLine();
            }
        }
    }

    private static int printAllTags(List<Tag> tags, Scanner sc) {
        while (true) {
            for (int i = 1; i <= tags.size(); i++) {
                System.out.printf("%n%d. %s%n", i, tags.get(i - 1));
            }
            System.out.print("Enter your choice for tag (other number for \"exit\"): ");
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Wrong format, Enter a number please!");
                sc.nextLine();
            }
        }
    }

    private static void addOtherWriters(Article article, Scanner sc) {
        while (true) {
            System.out.print("Enter another writer username (except you): ");
            String writer = sc.next() + sc.nextLine();
            if (writer.equalsIgnoreCase("esc")) {
                return;
            }
            Optional<User> oUser = userService.findUserByUserName(writer);
            if (oUser.isPresent()) {
                article.addWriter(oUser.get());
                System.out.print("Do you want add another writer? ");
                String ans = sc.next();
                if (ans.equalsIgnoreCase("y")) {
                    continue;
                }
                return;
            } else {
                System.out.println("There is not a User with this UserName!");
                System.out.print("Do you want to SignUp it? ");
                String answer = sc.next();
                if (answer.equalsIgnoreCase("y")) {
                    signUpUser(sc);
                }
            }
        }
    }

    public static void seeAllWrittenArticles(User user, Scanner sc) {
        List<Article> articles = articleService.findAll(Article.class, "Article.findAll");
        if (articles.size() == 0) {
            System.out.println("There is not any article yet!");
            return;
        }
        while (true) {
            int choice = printArticles(articles, sc);
            try {
                Article chooseArticle = articles.get(choice - 1);
                chooseArticle.printCompleteInformation();
                changeArticlesPublishState(chooseArticle, sc);
                System.out.print("Press any key to back!");
                sc.next();
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }

    public static void changeArticlesPublishState(Article article, Scanner sc) {
        System.out.print("Toggle publication state (Current state: " + article.isPublished() + ")? ");
        String answer = sc.next();
        if (answer.equalsIgnoreCase("y")) {
            article.setPublished(!article.isPublished());
            article.setPublishedDate(new Date(System.currentTimeMillis()));
            Optional<Article> oArticle = articleService.update(article);
            if (oArticle.isPresent()) {
                System.out.println("Your article publication state was toggled successfully!");
            } else {
                System.out.println("There is a problem, We can not toggle your article publication state!");
            }
        }
    }

    public static void deleteArticles(User user, Scanner sc) {
        System.out.printf("%n====== %s ======%n", "DELETE ARTICLE");
        while (true) {
            System.out.print("Enter article title: ");
            String articleTitle = sc.next();
            if (articleTitle.equalsIgnoreCase("esc")) {
                return;
            }
            Optional<Article> oArticle = articleService.findByTitle(articleTitle);
            if (oArticle.isPresent()) {
                System.out.print("Do you want delete it? ");
                String answer = sc.next();
                if (answer.equalsIgnoreCase("y")) {
                    articleService.delete(oArticle.get().getId());
                    System.out.println(articleTitle + " Article was delete successfully!");
                }
            } else {
                System.out.println("There is not any Article with this title!");
            }
        }
    }

    public static void createNewCategory(User user, Scanner sc) {
        while (true) {
            System.out.printf("%n====== %s ======%n", "CREATE NEW CATEGORY");
            try {
                Category category = new Category();
                System.out.print("Title: ");
                String title = sc.next() + sc.nextLine();
                if (title.equalsIgnoreCase("esc")) {
                    return;
                }
                category.setTitle(title);
                System.out.print("Description: ");
                category.setDescription(input.next());
                Optional<Category> oCategory = categoryService.save(category);
                if (oCategory.isPresent()) {
                    System.out.println("Your category was create correctly!");
                    break;
                } else {
                    System.out.println("There is a category with this title already!");
                }
            } catch (NoValidDataException e) {
                System.out.println("Wrong entered data format for " + e.getMessage() + "!");
            }
        }
    }

    public static void createNewTag(User user, Scanner sc) {
        while (true) {
            System.out.printf("%n====== %s ======%n", "CREATE NEW TAG");
            try {
                Tag tag = new Tag();
                System.out.print("Title: ");
                String title = sc.next() + sc.nextLine();
                if (title.equalsIgnoreCase("esc")) {
                    return;
                }
                tag.setTitle(title);
                Optional<Tag> oTag = tagService.save(tag);
                if (oTag.isPresent()) {
                    System.out.println("Your tag was create correctly!");
                    break;
                } else {
                    System.out.println("There is a tag with this title already!");
                }
            } catch (NoValidDataException e) {
                System.out.println("Wrong entered data format for " + e.getMessage() + "!");
            }
        }
    }

    public static void changeRoleOfUsers(User user, Scanner sc) {
        List<User> users = userService.findAllExceptMe(user.getUserName());
        if (users.size() == 0) {
            System.out.println("There is not any other user yet!");
            return;
        }
        while (true) {
            int choice = printUsers(users, sc);
            try {
                User chooseUser = users.get(choice - 1);
                chooseUser.printCompleteInformation();
                changeUserRole(chooseUser, sc);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }
    }


    static int printUsers(List<User> users, Scanner sc) {
        while (true) {
            for (int i = 1; i <= users.size(); i++) {
                System.out.printf("%n%d. %s%n", i, users.get(i - 1));
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

    public static void changeUserRole(User user, Scanner sc) {
        System.out.print("Enter user role title (Current role: " + user.getRole() + "): ");
        String newRole = sc.next();
        if (newRole.equalsIgnoreCase("esc")) {
            return;
        }
        if (newRole.equalsIgnoreCase(user.getRole().getTitle())) {
            System.out.println("Duplicate role!\nnothing being changed!");
            return;
        }
        Optional<Role> role = roleService.findByTitle(newRole);
        if (role.isPresent()) {
            user.setRole(role.get());
            Optional<User> updatedUser = userService.update(user);
            if (updatedUser.isPresent()) {
                System.out.println("Your user's role was changed successfully!");
            } else {
                System.out.println("There is a problem, We can not change user's role!");
            }
        } else {
            System.out.println("There is not any role with this title yet!");
        }
    }

    public static void seeUserAddress() {
        while (true) {
            List<User> users = userService.findAll(User.class, "User.findAll");
            users.stream().forEach(User::printCompleteInformation);
            System.out.println("please enter that username you want to see her/his address's");
            String userName = input.next();
            if (userName.equalsIgnoreCase("esc")) {
                return;
            }
            Optional<Address> addresses = ADDRESS_SERVICE.findByUserName(userName);
            Optional<User> oUser = userService.findUserByUserName(userName);
            if (oUser.isPresent()) {
                addresses.get().printCompleteInformation();
                return;
            } else {
                System.out.println("this username not exist");
            }
        }
    }

    public static void printAllInformationOfSampleTest(){
        List<SampleTest> sampleTests=SAMPLE_TEST_SERVICE.findAll();
        sampleTests.stream().forEach(SampleTest::printCompleteInformation);
    }




//=======================================================================================================
    private static HashMap<Class<?>, HashMap<Class<?>, Function<?, ?>>> converters = new HashMap<>();

    public static <T, R> void putConverter(Class<T> t, Class<R> r, Function<T, R> func) {
        HashMap<Class<?>, Function<?, ?>> map = converters.get(t);
        if(map == null) converters.put(t, map = new HashMap<>());
        map.put(r, func);
    }
//=========================================================================================================



    public static  <T> T findFeature(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).findAny().orElse(null);
    }

    public static void filterAllObjectOfAnEntity(){
        List<User> list=userService.findAll(User.class,"User.findAll");
        System.out.println("enter that username you want : ");
        String username=input.next();
        Predicate<User> userPredicate= user -> user.getUserName().equalsIgnoreCase(username);
        findFeature(list,userPredicate).printCompleteInformation();
        putConverter(User.class,UserInfo.class,f -> new UserInfo(f.getUserName(),f.getNationalCode(),f.getRole()));
    }




    public static  <T,U> T convertFromEntity(final U entity, final Function<U,T> fromEntity) {
        return fromEntity.apply(entity);
    }

    public static void convertSomFieldFromUserToUserInfo(){
        List<User> list=userService.findAll(User.class,"User.findAll");
        User user= list.get(0);
        UserInfo userInfo = convertFromEntity( user,  s->new UserInfo(s.getUserName(),s.getNationalCode(),s.getRole()));
        System.out.printf("UserInfo username : %s%nUserInfo nationalCode : %s%nUserInfo role : %s%n",userInfo.getUserName(),userInfo.getNationalCoe(),userInfo.getRole());
    }















}



