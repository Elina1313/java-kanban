package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    void remove(int id);

    Task addNewTask(Task task);

    Epic addNewEpic(Epic epic);

    Subtask addNewSubtask(Subtask subtask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    List<Subtask> getEpicSubtasks(int id);

    void updateEpicStatus(Epic epic);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void printTasks();

    void printEpics();

    void printSubtasks();

    List<Task> getPrioritizedTasks();

}
