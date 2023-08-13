package rio.ua.example.thirdTask;

import lombok.Data;

@Data
public class UserTask {
    int userId;
    int id;
    String title;
    boolean completed;
}
