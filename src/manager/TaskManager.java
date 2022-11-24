package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    void remove(int id);

    public List<Task> getTasks();

    public List<Subtask> getSubtasks();

    public List<Epic> getEpics();

    int addNewTask(Task task);

    public int addNewEpic(Epic epic);

    int addNewSubtask(Subtask subtask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    public List<Subtask> getEpicSubtasks(int id);

    void updateEpicStatus(Epic epic);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);


}
