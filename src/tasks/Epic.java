package tasks;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected ArrayList<Integer> subtasksIds = new ArrayList<>();

    public Epic(int id, String name, String description, String status) {
        super(id, name, description, status);
    }

    public Epic(String name, String description, String status) {
        super(name, description, status);
    }

    public boolean booleanisEpic() {
        return true;
    }

    public void addSubtaskId(int id) {
        subtasksIds.add(id);
    }

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void addIdSubtasks(int id) {
        subtasksIds.add(id);
    }

    public void cleanSubtaskIds() {
        subtasksIds.clear();
    }

    public void removeSubtask(int id) {
        subtasksIds.remove(Integer.valueOf(id));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksIds, epic.subtasksIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksIds);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subtasksIds=" + subtasksIds +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}