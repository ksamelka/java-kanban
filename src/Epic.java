import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    @Override
    public String toString() {
        return "Epic{" +
                "subtaskId=" + subtaskIds +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status=" + getStatus() +
                '}';
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubTask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }
    public void removeSubTask(int subtaskId) {
        subtaskIds.remove(subtaskId);
    }
    public void clearSubTask() {
        subtaskIds.clear();
    }
}
