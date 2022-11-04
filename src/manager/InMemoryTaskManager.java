package manager;

import tasks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 0;

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getTasks() {
        List<Task> taskArrayList = new ArrayList<>(tasks.values());
        return taskArrayList;
    }

    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> subtaskArrayList = new ArrayList<>(subtasks.values());
        return subtaskArrayList;
    }

    @Override
    public List<Epic> getEpics() {
        List<Epic> epicArrayList = new ArrayList<>(epics.values());
        return epicArrayList;
    }

    @Override
    public List<Subtask> getEpicSubtasks(int epicId) {
        List<Integer> subtaskIds = new ArrayList<>(getEpic(epicId).getSubtasksIds());
        List<Subtask> epicSubtask = new ArrayList<>();
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
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {

        for (int idValue : getEpic(id).getSubtasksIds()) {
            subtasks.remove(id);
        }
        epics.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = getSubtask(id);
        if (subtask == null) {
            System.out.println("Ошибка удаления. Подзадачи с id" + id + "не существует.");
        } else {
            Epic epic = getEpic(subtask.getEpicId());
            subtasks.remove(id);
            subtasks.remove(id);
        }
    }

    @Override
    public Task getTask(int id) {
        historyManager.addTask(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.addTask(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addTask(epics.get(id));
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
            epic.setStatus(TaskStatus.NEW);
            return;
        }
        TaskStatus status = TaskStatus.NEW;
        int index = 0;

        for (int id : sub) {
            Subtask subtask = subtasks.get(id);
            if (subtask.getStatus().equals(TaskStatus.NEW)) {
                continue;
            }
            if (subtask.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                status = TaskStatus.IN_PROGRESS;
                continue;
            }
            if (subtask.getStatus().equals(TaskStatus.DONE)) {
                index++;
            }
        }
        if (index == sub.size()) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(status);
        }

    }
}