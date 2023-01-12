package manager;

import tasks.*;

import java.util.*;
import java.time.Instant;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private HistoryManager historyManager; /*private final HistoryManager historyManager;*/
    private final Comparator<Task> taskComparator = Comparator.comparing(Task::getStartTime);

    protected Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);
    private static int id = 0;

    /*private HistoryManager historyManager = Managers.getDefaultHistory();*/

    public InMemoryTaskManager(HistoryManager historyManager) {

        this.historyManager = historyManager;
    }

    public int generateId() {

        return ++id;
    }

    @Override
    public Task addNewTask(Task task) {

        if (task == null) return null;
        int newTaskId = generateId();
        task.setId(newTaskId);
        addNewPrioritizedTask(task);
        tasks.put(newTaskId, task);
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {

        if (epic == null) return null;
        int newEpicId = generateId();
        epic.setId(newEpicId);
        epics.put(newEpicId, epic);
        return epic;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {

        if (subtask == null) return null;
        int newSubtaskId = generateId();
        subtask.setId(newSubtaskId);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            addNewPrioritizedTask(subtask);
            subtasks.put(newSubtaskId, subtask);
            epic.setSubtaskIds(newSubtaskId);
            updateEpicStatus(epic);
            updateTimeEpic(epic);
            return subtask;
        } else {
            System.out.println("Эпик не найден.");
            return null;
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            prioritizedTasks.removeIf(task -> task.getId() == id);
            tasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Ошибка удаления. Задача с id" + id + "не существует.");
        }
    }

    @Override
    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtasksIds()) {
                prioritizedTasks.removeIf(task -> Objects.equals(task.getId(), subtaskId)); //
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            epics.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Ошибка удаления. Эпика с id" + id + "не существует.");
        }
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasksIds().remove((Integer) subtask.getId());
            updateEpic(epic);
            updateTimeEpic(epic); //
            prioritizedTasks.remove(subtask); //
            subtasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Ошибка удаления. Подзадачи с id" + id + "не существует.");
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
        prioritizedTasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {

        for (Epic epic : epics.values()) {
            for (int subtaskId : epic.getSubtasksIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                prioritizedTasks.remove(subtask);
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            epic.getSubtasksIds().clear();
        }
    }

    @Override
    public Task getTask(int id) {

        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpic(int id) {

        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {

        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public List<Task> getAllTasks() {
        if (tasks.size() == 0) {
            System.out.println("Список задач пуст.");
            return Collections.emptyList();
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        if (epics.size() == 0) {
            System.out.println("Список эпиков пуст.");
            return Collections.emptyList();
        }
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        if (subtasks.size() == 0) {
            System.out.println("Список подзадач пуст.");
            return Collections.emptyList();
        }
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Subtask> getEpicSubtasks(int id) {
        if (epics.containsKey(id)) {
            List<Subtask> subtasksNew = new ArrayList<>();
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubtasksIds().size(); i++) {
                subtasksNew.add(subtasks.get(epic.getSubtasksIds().get(i)));
            }
            return subtasksNew;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void updateTask(Task task) {
        if (task != null && tasks.containsKey(task.getId())) {
            addNewPrioritizedTask(task);
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задача не существует.");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epic != null && epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic);
            updateTimeEpic(epic);
        } else {
            System.out.println("Эпик не существует.");
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask != null && subtasks.containsKey(subtask.getId())) {
            addNewPrioritizedTask(subtask);
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            updateEpicStatus(epic);
            updateTimeEpic(epic);
        } else {
            System.out.println("Подзадачи не существует.");
        }
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            if (epic.getSubtasksIds().size() == 0) {
                epic.setStatus(TaskStatus.NEW);
            } else {
                List<Subtask> subtasksNew = new ArrayList<>();
                int countDone = 0;
                int countNew = 0;

                for (int i = 0; i < epic.getSubtasksIds().size(); i++) {
                    subtasksNew.add(subtasks.get(epic.getSubtasksIds().get(i)));
                }

                for (Subtask subtask : subtasksNew) {
                    if (subtask.getStatus() == TaskStatus.DONE) {
                        countDone++;
                    }
                    if (subtask.getStatus() == TaskStatus.NEW) {
                        countNew++;
                    }
                    if (subtask.getStatus() == TaskStatus.IN_PROGRESS) {
                        epic.setStatus(TaskStatus.IN_PROGRESS);
                        return;
                    }
                }

                if (countDone == epic.getSubtasksIds().size()) {
                    epic.setStatus(TaskStatus.DONE);
                } else if (countNew == epic.getSubtasksIds().size()) {
                    epic.setStatus(TaskStatus.NEW);
                } else {
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }
            }
        } else {
            System.out.println("Эпик не существует.");
        }

    }

    public void updateTimeEpic(Epic epic) {
        List<Subtask> subtasks = getEpicSubtasks(epic.getId());
        Instant startTime = subtasks.get(0).getStartTime();
        Instant endTime = subtasks.get(0).getEndTime();

        for (Subtask subtask : subtasks) {
            if (subtask.getStartTime().isBefore(startTime)) startTime = subtask.getStartTime();
            if (subtask.getEndTime().isAfter(endTime)) endTime = subtask.getEndTime();
        }

        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        long duration = (endTime.toEpochMilli() - startTime.toEpochMilli());
        epic.setDuration(duration);
    }

    @Override
    public List<Task> getHistory() {

        return historyManager.getHistory();
    }

    @Override
    public void remove(int id) {

        historyManager.remove(id);
    }

    public void addToHistory(int id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
        } else if (subtasks.containsKey(id)) {
            historyManager.add(subtasks.get(id));
        } else if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
        }
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    private void addNewPrioritizedTask(Task task) {
        prioritizedTasks.add(task);
        validateTaskPriority();
    }

    public boolean checkTime(Task task) {
        List<Task> tasks = List.copyOf(prioritizedTasks);
        int sizeTimeNull = 0;
        if (tasks.size() > 0) {
            for (Task taskSave : tasks) {
                if (taskSave.getStartTime() != null && taskSave.getEndTime() != null) {
                    if (task.getStartTime().isBefore(taskSave.getStartTime())
                            && task.getEndTime().isBefore(taskSave.getStartTime())) {
                        return true;
                    } else if (task.getStartTime().isAfter(taskSave.getEndTime())
                            && task.getEndTime().isAfter(taskSave.getEndTime())) {
                        return true;
                    }
                } else {
                    sizeTimeNull++;
                }

            }
            return sizeTimeNull == tasks.size();
        } else {
            return true;
        }
    }

    private void validateTaskPriority() {
        List<Task> tasks = getPrioritizedTasks();

        for (int i = 1; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            boolean taskHasIntersections = checkTime(task);

            if (taskHasIntersections) {
                throw new ManagerValidateException(
                        "Задачи #" + task.getId() + " и #" + tasks.get(i - 1) + "пересекаются");
            }
        }
    }

    private List<Task> getPrioritizedTasks() {
        return prioritizedTasks.stream().toList();  //
    }

    @Override
    public void printTasks() {
        if (tasks.size() == 0) {
            System.out.println("Task list is empty");
            return;
        }
        for (Task task : tasks.values()) {
            System.out.println("Task{" +
                    "description='" + task.getDescription() + '\'' +
                    ", id=" + task.getId() +
                    ", name='" + task.getName() + '\'' +
                    ", status=" + task.getStatus() +
                    '}');
        }
    }

    @Override
    public void printEpics() {
        if (epics.size() == 0) {
            System.out.println("Epic list is empty");
            return;
        }
        for (Epic epic : epics.values()) {
            System.out.println("Epic{" +
                    "subtasksIds=" + epic.getSubtasksIds() +
                    ", description='" + epic.getDescription() + '\'' +
                    ", id=" + epic.getId() +
                    ", name='" + epic.getName() + '\'' +
                    ", status=" + epic.getStatus() +
                    '}');
        }
    }

    @Override
    public void printSubtasks() {
        if (subtasks.size() == 0) {
            System.out.println("Subtask list is empty");
            return;
        }
        for (Subtask subtask : subtasks.values()) {
            System.out.println("Subtask{" +
                    "epicId=" + subtask.getEpicId() +
                    ", description='" + subtask.getDescription() + '\'' +
                    ", id=" + subtask.getId() +
                    ", name='" + subtask.getName() + '\'' +
                    ", status=" + subtask.getStatus() +
                    '}');
        }
    }

}
