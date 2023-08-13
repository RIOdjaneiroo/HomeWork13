package rio.ua.example.firstTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

public class CrudFunctionTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        CrudFunction crudFunction = new CrudFunction();        // створюємо клас
        //String filePath = "./src/files/file.json";

        String newUser = crudFunction.createUser("./src/files/filex.json");  // 1 завдання створення юзера
        System.out.println("Create user:\n" + newUser);
        crudFunction.saveUserToFile(newUser, "./src/files/newFile.json");

        int updateUserId = 2; // 2 завдання співпадає з Id але Id в нашому випадку не має значення тому що оновлення беруться з файлу
        String updateUser = crudFunction.updateUserId(updateUserId, "./src/files/filex.json");
        System.out.println("Updated user:\n" + updateUser);
        crudFunction.saveUserToFile(updateUser, "./src/files/updateFile.json");

        int deleteUserId = 3;  // 3 завдання видалення
        System.out.println("Delete User : " + deleteUserId + " status - " + crudFunction.deleteUserId(deleteUserId));

        String jsonUsers = crudFunction.getAllUsers();  // 4 задача вивід всіх юзерів, Отримати користувачів
        System.out.println("Get all user: " +jsonUsers );
        crudFunction.saveUsersToFile(jsonUsers, "./src/files/users.json");

        int userId = 5;       //5 завдання відобразити юзера за Id
        String user = crudFunction.getUserById(userId);
        System.out.println("User " + userId + ":\n" + user);
        crudFunction.saveUserToFile(user, "./src/files/user"+userId+".json");

        String name = "Antonette";  // 6 завдання відображення юзера по імені Antonette Samantha Kamren eLeGant
        String userName = crudFunction.getUserByName(name);
        System.out.println("Get user \"" + name + "\": " + userName);
        crudFunction.saveUsersToFile(userName, "./src/files/user_"+name+".json");

    }
}