package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ITasksManager {

    public ArrayList<Task> getTasks();

    public ArrayList<Subtask> getSubtasks();

    public ArrayList<Epic> getEpics();

    //public ArrayList<Subtask> getEpicSubtasks(int epicId);

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

    void deleteTask(int id); /* {
        Task task = tasks.remove(id);
    } */

    void deleteEpic(int id);

    /*{
        Epic epic = epics.remove(id);
        // если удаляется эпик удаляется и сабтаск

    }*/
    void deleteSubtask(int id);

}
