import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks;
    private String command;
    private String description;
    private int id;
    private final static String ADD = "add";
    private final static String PRINT = "print";
    private final static String TOGGLE = "toggle";
    private final static String DELETE = "delete";
    private final static String EDIT = "edit";
    private final static String SEARCH = "search";
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
            tasks.add(new Task(tasks.get(tasks.size() - 1).getId() + 1, description));
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
            for (Task task : tasks) {
                if (description.equals("") && !task.getDone() || description.equals("all")) {
                    System.out.printf("%d. [%s] %s\n", task.getId(), task.getDone() ? "x" : " ", task.getDescription());
                }
            }
        }
    }

    private int searchIndexById(int num) {
        int index = -1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).checkId(num)) {
                index = i;
                return index;
            }
        }
        return index;
    }

    private void toggleTasks() {
        try {
            if (tasks.isEmpty()) {
                System.out.println("Список задач пуст");
            } else {
                int num = Integer.parseInt(description);
                if (num > 0) {
                    int index = searchIndexById(num);
                    if (index != -1) {
                        tasks.get(index).setDone(!tasks.get(index).getDone());
                    }
                    else {
                        System.out.println("Введеный идентификатор задачи в команде toggle не существует");
                    }
                } else {
                    System.out.println("Введеный идентификатор <= 0");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Введен неверный идентификатор задачи в команде toggle");
        }
    }

    private void deleteTask() {
        try {
            if (tasks.isEmpty()) {
                System.out.println("Список задач пуст");
            } else {
                int num = Integer.parseInt(description);
                if (num > 0) {
                    int index = searchIndexById(num);
                    if (index != -1) {
                        tasks.remove(index);
                    }
                    else {
                        System.out.println("Введеный идентификатор задачи в команде delete не существует");
                    }
                } else {
                    System.out.println("Введеный идентификатор <= 0");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Введен неверный идентификатор задачи в команде delete");
        }
    }

    private void setIdAndDescription() {
        if (description.equals("")) {
            System.out.println("Отсутствует описание команды edit");
            return;
        }
        int i = description.indexOf(' ');
        if (i > -1) {
            id =Integer.parseInt(description.substring(0, i));
            description = description.substring(i + 1);
        } else {
            System.out.println("Введен неверный формат команды edit");
            id = Integer.MIN_VALUE;
        }
    }

    private void editTaskDescription() {
        try {
            setIdAndDescription();
            if (id == Integer.MIN_VALUE) {
                return;
            }
            if (tasks.isEmpty()) {
                System.out.println("Список задач пуст");
            } else {
                if (id > 0) {
                    int index = searchIndexById(id);
                    if (index != -1) {
                        tasks.get(index).setDescription(description);
                    }
                    else {
                        System.out.println("Введеный идентификатор задачи в команде edit не существует");
                    }
                } else {
                    System.out.println("Введеный идентификатор <= 0");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Введен неверный идентификатор задачи в команде edit");
        }
    }

    private void searchFromSubstring() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }
        for (Task task : tasks){
            if(task.checkDescription(description)){
                System.out.printf("%d. [%s] %s\n", task.getId(), task.getDone() ? "x" : " ", task.getDescription());
            }
        }
    }

    public void work() throws IOException {
        String userInput;
        boolean working = true;
        tasks = new ArrayList<>();
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
                case (DELETE):
                    deleteTask();
                    break;
                case (EDIT):
                    editTaskDescription();
                    break;
                case (SEARCH):
                    searchFromSubstring();
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