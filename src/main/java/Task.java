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

    public boolean checkId(int num) {
        return id == num;
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

    public boolean checkDescription(String substring) {
        return description.contains(substring);
    }

    public String getDescription() {

        return description;
    }
}