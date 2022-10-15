package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ITasksManager {

    public List<Task> getTasks();

    public List<Subtask> getSubtasks();

    public List<Epic> getEpics();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    public List<Subtask> getEpicSubtasks(int epicId);

    int addNewTask(Task task);

    public int addNewEpic(Epic epic);

    Integer addNewSubtask(Subtask subtask);

    void updateEpicStatus(int epicId);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

}
