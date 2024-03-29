package tasks;

import java.time.Instant;

import java.util.*;

public class Task {
    private int id;
    private String name;
    private String description;
    private TaskStatus status;
    private Instant startTime;
    private long duration;

    public Task(int id, String name, String description, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;

    }

    public Task(String name, String description, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;

    }

    public Task(String name, String description, TaskStatus status, Instant startTime, long duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public TaskStatus getStatus() {

        return status;
    }

    public void setStatus(TaskStatus status) {

        this.status = status;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Instant getStartTime() {

        return startTime;
    }

    public void setStartTime(Instant startTime) {

        this.startTime = startTime;
    }

    public long getDuration() {

        return duration;
    }

    public void setDuration(long duration) {

        this.duration = duration;
    }

    public Instant getEndTime() {
        long secondsInMinute = 60L;
        return startTime.plusSeconds(duration * secondsInMinute);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(status, task.status) && Objects.equals(description, task.description) && Objects.equals(startTime, task.startTime) &&
                Objects.equals(duration, task.duration);
    }


    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, status, startTime, duration);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", startTime='" + startTime.toEpochMilli() + '\'' +
                ", endTime='" + getEndTime().toEpochMilli() + '\'' +
                ", duration='" + duration +
                '}';
    }
}


