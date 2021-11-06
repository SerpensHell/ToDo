import lombok.Data;

@Data
public class Task {
    private boolean done;
    private String description;

    Task(String desc) {
        done = false;
        description = desc;
    }
    public boolean checkDescription(String substring) {
        return description.contains(substring);
    }

}