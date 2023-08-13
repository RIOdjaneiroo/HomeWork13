package rio.ua.example.firstTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class CrudFunction {
    //final String url = "https://jsonplaceholder.typicode.com/users";
    HttpClient client = HttpClient.newHttpClient();

    public String createUser (String jsonFilePath) throws IOException, InterruptedException {   //метод для створення (реєстрації) юзера приймає шлях де лежить json файл з юзером
        String url = "https://jsonplaceholder.typicode.com/users";
        HttpRequest request = HttpRequest.newBuilder()                                  // створюємо HttpRequest з білдером в якому налаштуємо пост запит
                .uri(URI.create(url))                                                // вказуємо строку підключення до апі uri
                .header("Content-type", "application/json")            // встановлюємо заголовки
                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get(jsonFilePath)))   // налаштовуємо POST в тіло запиту кладемо файл jsonFilePath з шляхом де він зберігається
                .build();                                                         // збілдили запит
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // відповідь на запит приводиться до рядка HttpResponse.BodyHandlers.ofString())
        return  response.body();                                              // виводимо тіло відповіді
    }
    public String updateUserId(int userId, String jsonFilePath) throws IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users/" + userId; // вказуємо строку підключення до апі
        HttpRequest request = HttpRequest.newBuilder()                 // HttpRequest для запиту створюємо білдер
                .uri(URI.create(uri))        // строка підключення
                .header("Content-Type", "application/json") // встановлюємо заголовки
                .PUT(HttpRequest.BodyPublishers.ofFile(Paths.get(jsonFilePath)))      // налаштовуємо пут з фацлом оновлення
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // отримуємо відповідь з відправленого запиту у вигляді строки
        return response.body();       // виводимо тіло відповіді
    }
    public int deleteUserId(int id) throws IOException, InterruptedException { // метод для видалення
        String uri = "https://jsonplaceholder.typicode.com/users/" + id;
        HttpRequest request = HttpRequest.newBuilder()              // запит з білдером
                .uri(URI.create(uri))                // передаємо строку підключення навіть на встановлюємо заголовка
                .DELETE()                         // видаляємо
                .build();                         // білдимо
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // відправляємо (видобуваємо) результат на основі відправленого запиту у виляді строки
        return response.statusCode(); //повертаємо статус виконання
    }

    public String getAllUsers() throws IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public String getUserById(int id) throws IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public String getUserByName(String userName) throws IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users?username=" + userName;     // зверніть увагу як у строку підключення додається умова
        HttpRequest request = HttpRequest.newBuilder()                             // створюємо запит з білдером
                .uri(URI.create(uri))                                             // підключаємось
                .GET()                                                           // вказуємо метод
                .build();                                                       // білдимо
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // відправляємо і водночас отримуємо строку на основі запиту що відправляємо
        return response.body();                                               // виводимо результат
    }
    public void saveUsersToFile(String jsonUsers, String jsonFilePath) { //збереження юзерів у файл
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<User> userList = Arrays.asList(gson.fromJson(jsonUsers, User[].class));

        try (FileWriter output = new FileWriter(jsonFilePath)) {
            gson.toJson(userList, output);
            System.out.println("Користувача збережено " + jsonFilePath);
        } catch (IOException e) {
            System.out.println("Error saving users to file");
        }
    }
    public User userWithJson(String users) {                                  // цей метод видає обєкт зі строки
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(users, User.class);
    }
    public void saveUserToFile(String jsonUser, String jsonFilePath) {            //збереження юзера у файл зроблений з двох методів
        User user = userWithJson(jsonUser);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String outputString = gson.toJson(user);
        try (FileWriter output = new FileWriter(jsonFilePath)) {
            output.write(outputString);
        } catch (IOException e) {
            System.out.println("Error saving user to file");
        }
    }


}
