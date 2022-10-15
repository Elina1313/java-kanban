package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TasksManager implements ITasksManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 0;

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>(tasks.values());
        return taskArrayList;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> subtaskArrayList = new ArrayList<>(subtasks.values());
        return subtaskArrayList;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epicArrayList = new ArrayList<>(epics.values());
        return epicArrayList;
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        ArrayList<Integer> subtaskIds = new ArrayList<>(getEpic(epicId).getSubtasksIds());
        ArrayList<Subtask> epicSubtask = new ArrayList<>();
        for (Integer id : subtaskIds) {
            epicSubtask.add(getSubtask(id));
        }
        return epicSubtask;
    }


    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    @Override
    public void deleteTask(int id) {

    }

    @Override
    public void deleteEpic(int id) {

    }

    @Override
    public void deleteSubtask(int id) {

    }

    @Override
    public Task getTask(int id) {
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        return epics.get(id);
    }

    @Override
    public int addNewTask(Task task) {
        final int id = ++generatorId;
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        final int id = ++generatorId;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) {

        Epic epic = getEpic(subtask.getEpicId());
        if (getEpic(subtask.getEpicId()) == null) {
            System.out.println("no such epic: " + subtask.getEpicId());
            return -1;
        } else {
            final int id = ++generatorId;
            subtask.setId(id);
            subtasks.put(id, subtask);
            getEpic(subtask.getEpicId()).addIdSubtasks(id);
            updateEpicStatus(subtask.getEpicId());
            return id;

        }
    }

    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);

        List<Integer> sub = epic.getSubtasksIds();
        if (subtasks.isEmpty()) {
            epic.setStatus("NEW");
            return;
        }
        String status = "NEW";
        int index = 0;

        for (int id : sub) {
            Subtask subtask = subtasks.get(id);
            if (subtask.getStatus().equals("NEW")) {
                continue;
            }
            if (subtask.getStatus().equals("IN_PROGRESS")) {
                status = "IN_PROGRESS";
                continue;
            }
            if (subtask.getStatus().equals("DONE")) {
                index++;
            }
        }
        if (index == sub.size()) {
            epic.setStatus("DONE");
        } else {
            epic.setStatus(status);
        }

    }
}
