package rio.ua.example;


import rio.ua.example.firstTask.CrudFunctionTest;
import rio.ua.example.secondTask.CommentsUserService;
import rio.ua.example.thirdTask.TaskUserService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Доброго життя, перше завдання об'ємне тому викликається окремо з пакету firstTask з класа CrudFunctionTest");

        CrudFunctionTest.main(args);                                   //перша задача запуск з main  main іншого класу

        CommentsUserService commentsUser = new CommentsUserService();   // друга задача зберігаємо коментарі юзера у файл
        int user = 2;                                              // змінна Ід юзера
        commentsUser.createJsonCommentsFromLastPostUserId(user);  // створюємо файл задопомогою метода


        TaskUserService taskUserService = new TaskUserService(); // третя задачя зберігаємо відкриті задачі юзера у файл
        int userId = 4;                                         // змінна Ід юзера
        taskUserService.saveToJsonOpenTaskUser(userId);        // створюємо файл задопомогою метода


    }
}