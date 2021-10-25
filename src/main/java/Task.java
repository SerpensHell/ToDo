public class Task {
    private int id;
    private boolean done;
    private String description;

    Task(int idTask, String desc) {
        done = false;
        description = desc;
        id = idTask;
    }

    public int getId() {
        return id;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean completed) {
        done = completed;
    }

    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public String getDescription() {
        return description;
    }
}