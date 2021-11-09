import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TaskManager {
    private Map<String,Task> tasks;
    private String command;
    private String description;
    private static int counter = 0;
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
        counter++;
        tasks.put(String.valueOf(counter),new Task(description));
    }

   private static void printTask(Map.Entry<String, Task> tasks) {
       System.out.printf("%s. [%s] %s\n",tasks.getKey(), tasks.getValue().isDone() ? "x" : " ", tasks.getValue().getDescription());
   }

   private void printTasks() {
       if (tasks.isEmpty()) {
           System.out.println("Список задач пуст");
       } else {
           if (description.equals("all") || description.equals("")) {
               Stream<Map.Entry<String, Task>> stream = tasks.entrySet().stream();
               if(!description.equals("all")) {
                   stream = stream.filter(s -> !s.getValue().isDone());
               }
               stream.forEach(TaskManager::printTask);
           }
       }
   }

    private void toggleTasks() {
            if (tasks.isEmpty()) {
                System.out.println("Список задач пуст");
            } else {
                if (tasks.containsKey(description)) {
                   tasks.get(description).setDone(!tasks.get(description).isDone());
                } else {
                    System.out.println("Введеный идентификатор задачи в команде toggle не существует");
                }
            }
    }

    private void deleteTask() {
            if (tasks.isEmpty()) {
                System.out.println("Список задач пуст");
            } else {
                if (tasks.containsKey(description)) {
                    tasks.remove(description);
                } else {
                    System.out.println("Введеный идентификатор задачи в команде delete не существует");
                }
            }
    }

    private void editTaskDescription() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
        } else {
            Pattern pattern = Pattern.compile("^(\\d+)\\s+(\\S+.*)$");
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                String id = matcher.group(1);
                String newDescription = matcher.group(2);
                if (tasks.containsKey(id)) {
                    tasks.get(id).setDescription(newDescription);
                } else {
                    System.out.println("Введеный идентификатор задачи в команде edit не существует");
                }
            } else {
                System.out.println("Неверный формат команды edit");
            }
        }
    }

    private void searchFromSubstring() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст");
            return;
        }
        Stream<Map.Entry<String, Task>> stream = tasks.entrySet().stream();
        stream = stream.filter(s -> s.getValue().checkDescription(description));
        stream.forEach(TaskManager::printTask);
    }

    public void work() throws IOException {
        String userInput;
        boolean working = true;
        tasks = new LinkedHashMap<>();
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