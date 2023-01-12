package tasks;

import java.time.Instant;

import java.util.*;

public class Epic extends Task {
    private final List<Integer> subtasksIds = new ArrayList<>();
    private Instant endTime;

    public Epic(int id, String name, String description, TaskStatus status) {

        super(id, name, description, status);
    }

    public Epic(String name, String description, TaskStatus status) {

        super(name, description, status);
    }

    public Epic(String name, String description, TaskStatus status, Instant startTime, long duration) {

        super(name, description, status, startTime, duration);
        this.endTime = super.getEndTime();
    }

    public boolean booleanisEpic() {

        return true;
    }

    public void addSubtaskId(int id) {

        subtasksIds.add(id);
    }

    public List<Integer> getSubtasksIds() {

        return subtasksIds;
    }

    /*public void addIdSubtasks(int id) {

        subtasksIds.add(id);
    }*/

    public void setSubtaskIds(int id) {
        subtasksIds.add(id);
    }


    public void cleanSubtaskIds() {

        subtasksIds.clear();
    }

    public void removeSubtask(int id) {

        subtasksIds.remove(Integer.valueOf(id));
    }

    @Override
    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTime='" + getStartTime().toEpochMilli() + '\'' +
                ", endTime='" + getEndTime().toEpochMilli() + '\'' +
                ", duration='" + getDuration() +
                '}';
    }
}