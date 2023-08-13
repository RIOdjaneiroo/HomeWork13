package rio.ua.example.secondTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentsUserService {
    HttpClient client = HttpClient.newHttpClient(); // створюємо клієнта

    public void createJsonCommentsFromLastPostUserId(int userId) throws IOException, InterruptedException {
        String allPosts = getUserIdPost(userId); // отримання всіх постів користувача за його ід
        List<String> idPosts = getAllMatches(allPosts); // отримуємо список ідентифікаторів постів
        int getLastPost = getLastPostId(idPosts); // отримуємо ідентифікатора останнього поста
        String allComments = getAllCommentsByPostId(getLastPost); // отримуємо всі коментарі для останнього поста
        String fileJsonPath = "./src/files/" + "user-" + userId + "-post-" + getLastPost + "-comments.json"; // визначаємо шлях до файлу
        createJsonFileFromComments(allComments, fileJsonPath); // cтворюємо файл з коментарями
        System.out.println("Файл збережено - " + fileJsonPath); // виведення шляху до файлу що створили
    }
    private List<String> getAllMatches(String text) {  // метод дає ліст з тексту
        List<String> matches = new ArrayList<>(); // створюємо список для зберігання збігів
        Pattern pattern = Pattern.compile("\\\"id\": \\d+"); // патерн для пошуку ід (число)
        Matcher matcher = pattern.matcher(text); // створення обєкта Matcher для виявлення співпадінь
        while (matcher.find()) {                // поки знаходимо співпадіння
            matches.add(matcher.group()); // додаємо знайдених до списку
        }
        return matches; // Повернення списку зі збігами
    }
    private String getUserIdPost(int userId) throws IOException, InterruptedException {
        String uri = "https://jsonplaceholder.typicode.com/users/" + userId + "/posts"; // формуємо URL для отримання постів користувача
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build(); // створюємо GET запит до вказаного URL
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // відправляємо запит та отримуємо відповідь
        return response.body(); // Повернення тіла відповіді (всі пости користувача у вигляді рядка)
    }
    private int getNumOfString(String text) { // метод для видобування чисел з тексту
        String[] parts = text.split(" "); // створюємо масив розбиваючи рядок на частини за пробілами
        return Integer.parseInt(parts[1]); // результат Повернення другої частини як ціле число
    }
    private int getLastPostId(List<String> postsIds) {    // метод для отримання індекса (ідентифікатора) останнього поста
        return getNumOfString(postsIds.get(postsIds.size() - 1)); // виводим результат як останній елемент списку
    }
    private void createJsonFileFromComments(String str, String jsonFilePath) { // назва говоритьсама за себе
        UserComent[] comments = commentsToJson(str);                         // створюємо масив обєктів з JSON рядка
        Gson gson = new GsonBuilder().setPrettyPrinting().create();       // створюємо обєкт Gson для гарної обробки JSON
        String outputString = gson.toJson(comments); // конвертуємо обєкт в JSON рядок
        try (FileWriter output = new FileWriter(jsonFilePath)) { // створюємо FileWriter для запису JSON в файл
            output.write(outputString); // запис JSON рядка у файл
        } catch (IOException e) {
            e.printStackTrace(); // можливої помилки виводу
        }
    }
    private UserComent[] commentsToJson(String json) {          // допоміжний метод для перетворення JSON рядка в масив обєктів
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // створення обєкту Gson для обробки JSON
        return gson.fromJson(json, UserComent[].class); // конвертація JSON рядка в масив обєктів UserComent
    }
    private String getAllCommentsByPostId(int postId) throws IOException, InterruptedException { // метод повертає строку (коментарі) до поста
        String uri = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments"; // формуємо URL для отримання коментарів
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build(); // створюємо / білдимо GET запит до URL
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); // відправляємо запит та отримуємо відповідь
        return response.body(); // повертаємо тіла відповіді (всі коментарі для даного поста у вигляді рядка)
    }
}
    // @Data // Аннотація Lombok для генерації геттерів, сеттер
