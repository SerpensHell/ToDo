import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks;
    private String command;
    private String description;
    private final static String ADD = "add";
    private final static String PRINT = "print";
    private final static String TOGGLE = "toggle";
    private final static String QUIT = "quit";

    private void setCommandAndDescription(String userInput) {
        int i = userInput.indexOf(' ');
        if (i > -1) {
            command = userInput.substring(0, i);
            description = userInput.substring(i + 1);
        } else {
            command = userInput;
            description = "";
        }
    }

    private void addTask() {
        if (description.equals("")) {
            System.out.println("Отсутствует описание задачи");
            return;
        }
        if (tasks.isEmpty()) {
            tasks.add(0, new Task(1, description));
        } else {
            tasks.set(0,new Task(1,description));
        }
    }

    private void printTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
        } else {
            if (!description.equals("all") && !description.equals("")) {
                System.out.println("Недопустимый аргумент команды print");
                return;
            }
            Task task = tasks.get(0);
            if (description.equals("") && !task.getDone() || description.equals("all")) {
                System.out.printf("%d. [%s] %s\n", task.getId(), task.getDone() ? "x" : " ", task.getDescription());
            }
        }
    }

    private void toggleTasks() {
        try {
            if (tasks.isEmpty()) {
                System.out.println("Список задач пуст");
            } else {
                int num = Integer.parseInt(description);
                Task task = tasks.get(0);
                if (num <= tasks.size() && num > 0) {
                    tasks.get(0).setDone(!task.getDone());
                } else {
                    System.out.println("Введеный идентификатор задачи в команде toggle не существует");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Введен неверный идентификатор задачи в команде toggle");
        }
    }

    public void work() throws IOException {
        String userInput;
        boolean working = true;
        tasks = new ArrayList<>(1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(working) {
            userInput = reader.readLine();
            setCommandAndDescription(userInput);
            switch (command) {
                case (ADD):
                    addTask();
                    break;
                case (PRINT):
                    printTasks();
                    break;
                case (TOGGLE):
                    toggleTasks();
                    break;
                case (QUIT):
                    working = false;
                    break;
                default:
                    System.out.println("Введена неверная команда");
                    break;
            }
        }
    }
}