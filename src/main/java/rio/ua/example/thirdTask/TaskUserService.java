package rio.ua.example.thirdTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskUserService {
    HttpClient client = HttpClient.newHttpClient(); // створюємо Http клієнта

    public void saveToJsonOpenTaskUser(int userId) throws IOException, InterruptedException { 
        // метод створює JSON-файл зі списком незавершених завдань користувача за його ід
        String allJsonTask = getAllTaskUserId(userId); // отримуємо JSON зі всіма завданнями користувача
        List<UserTask> allTask = getOpenTaskFromJson(allJsonTask); // отримуємо список незавершених завдань з JSON
        String filePath = "./src/files/" + "user-" + userId + "-open_task.json"; // визначаємо шлях до файлу
        saveJsonTaskUser(allTask, filePath);          // cтворюємо файл зі списком незавершених завдань
        System.out.println("Файл задач збережено - " + filePath); // виводимо шлях до створеного JSON-файлу
    } 

    private String getAllTaskUserId(int userId) throws IOException, InterruptedException {
        // метод повертаєстроку створений для отримання JSON зі всіма завданнями користувача за його ід
        String uri = "https://jsonplaceholder.typicode.com/users/" + userId + "/todos";  //строка ресурсу в змінній
        HttpRequest request = HttpRequest.newBuilder()  // створюємо запит                 з білдером
                .uri(URI.create(uri))
                .GET()                                                      // GET
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //створюємо відповідь на відправлений запит
        return response.body(); // повертаємо тіло відповіді
    }

    private List<UserTask> getOpenTaskFromJson(String jsonTask) { 
        // метод повертає список задач зі строки сворений для отримання відкритих задач з JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();  //створюємо Gson - обєкт з гарним відображенням
        UserTask[] tasks = gson.fromJson(jsonTask, UserTask[].class); // конвертуємо JSON в масив обєктів UserTask
        List<UserTask> taskList = new ArrayList<>(Arrays.asList(tasks)); // перетворюємо масив в список
        List<UserTask> openTaskList = new ArrayList<>();          // cтворюємо список для незавершених завдань
        for (UserTask element : taskList) {     // наповнюємо список циклом
            if (!element.completed) {            // якщо завдання не completed
                openTaskList.add(element);      // додаємо в список
            }
        }
        return openTaskList;               // виводимо результат
    }

    private void saveJsonTaskUser(List<UserTask> tasks, String jsonFilePath) {// метод створює JSON-файл зі списком завдань
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //створюємо Gson - обєкт з гарним відображенням
        String outputString = gson.toJson(tasks.toArray());         // конвертуємо список в JSON-рядок
        try (FileWriter output = new FileWriter(jsonFilePath)) {    // відкриваємо файл для запису
            output.write(outputString);                           // записуємо JSON-рядок у файл
        } catch (IOException e) {
            e.getStackTrace();                             // Обробка можливої помилки запису
        }
    }
}