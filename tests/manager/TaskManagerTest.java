package manager;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    protected Task addNewTask() {

        return new Task("Title", "Description", TaskStatus.NEW, Instant.now(), 0);

    }

    protected Epic addNewEpic() {

        return new Epic("Title", "Description", TaskStatus.NEW, Instant.now(), 0);
    }

    protected Subtask addNewSubtask(Epic epic) {

        return new Subtask("Title", "Description", TaskStatus.NEW, epic.getId(), Instant.now(), 0);

    }

    @Test
    public void shouldAddNewTask() {
        Task task = addNewTask();
        manager.addNewTask(task);
        List<Task> tasks = manager.getAllTasks();
        assertNotNull(task.getStatus());
        assertEquals(TaskStatus.NEW, task.getStatus());
        assertEquals(List.of(task), tasks);
    }

    @Test
    public void shouldAddNewEpic() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        List<Epic> epics = manager.getAllEpics();
        assertNotNull(epic.getStatus());
        assertEquals(TaskStatus.NEW, epic.getStatus());
        assertEquals(Collections.EMPTY_LIST, epic.getSubtasksIds());
        assertEquals(List.of(epic), epics);
    }

    @Test
    public void shouldAddNewSubtask() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addNewSubtask(epic);
        manager.addNewSubtask(subtask);
        List<Subtask> subtasks = manager.getAllSubtasks();
        assertNotNull(subtask.getStatus());
        assertEquals(epic.getId(), subtask.getEpicId());
        assertEquals(TaskStatus.NEW, subtask.getStatus());
        assertEquals(List.of(subtask), subtasks);
        assertEquals(List.of(subtask.getId()), epic.getSubtasksIds());
    }

    @Test
    void shouldReturnNullForTaskNull() {
        Task task = manager.addNewTask(null);
        assertNull(task);
    }

    @Test
    void shouldReturnNullForEpicNull() {
        Epic epic = manager.addNewEpic(null);
        assertNull(epic);
    }

    @Test
    void shouldReturnNullForSubtaskNull() {
        Subtask subtask = manager.addNewSubtask(null);
        assertNull(subtask);
    }

    @Test
    public void shouldUpdateTaskStatusInProgress() {
        Task task = addNewTask();
        manager.addNewTask(task);
        task.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(task);
        assertEquals(TaskStatus.IN_PROGRESS, manager.getTask(task.getId()).getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusInProgress() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        epic.setStatus(TaskStatus.IN_PROGRESS);
        assertEquals(TaskStatus.IN_PROGRESS, manager.getEpic(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateSubtaskStatusInProgress() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addNewSubtask(epic);
        manager.addNewSubtask(subtask);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(subtask);
        assertEquals(TaskStatus.IN_PROGRESS, manager.getSubtask(subtask.getId()).getStatus());
        assertEquals(TaskStatus.IN_PROGRESS, manager.getEpic(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateTaskStatusInDone() {
        Task task = addNewTask();
        manager.addNewTask(task);
        task.setStatus(TaskStatus.DONE);
        manager.updateTask(task);
        assertEquals(TaskStatus.DONE, manager.getTask(task.getId()).getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusInDone() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        epic.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, manager.getEpic(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateSubtaskStatusInDone() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addNewSubtask(epic);
        manager.addNewSubtask(subtask);
        subtask.setStatus(TaskStatus.DONE);
        manager.updateSubtask(subtask);
        assertEquals(TaskStatus.DONE, manager.getSubtask(subtask.getId()).getStatus());
        assertEquals(TaskStatus.DONE, manager.getEpic(epic.getId()).getStatus());
    }

    @Test
    public void shouldNotUpdateTaskIfNull() {
        Task task = addNewTask();
        manager.addNewTask(task);
        manager.updateTask(null);
        assertEquals(task, manager.getTask(task.getId()));
    }

    @Test
    public void shouldNotUpdateEpicIfNull() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        manager.updateEpic(null);
        assertEquals(epic, manager.getEpic(epic.getId()));
    }

    @Test
    public void shouldNotUpdateSubtaskIfNull() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addNewSubtask(epic);
        manager.addNewSubtask(subtask);
        manager.updateSubtask(null);
        assertEquals(subtask, manager.getSubtask(subtask.getId()));
    }

    @Test
    public void shouldDeleteAllTasks() {
        Task task = addNewTask();
        manager.addNewTask(task);
        manager.deleteAllTasks();
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
    }

    @Test
    public void shouldDeleteAllEpics() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        manager.deleteAllEpics();
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
    }

    @Test
    public void shouldDeleteAllSubtasks() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addNewSubtask(epic);
        manager.addNewSubtask(subtask);
        manager.deleteAllSubtasks();
        assertTrue(epic.getSubtasksIds().isEmpty());
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldDeleteTask() {
        Task task = addNewTask();
        manager.addNewTask(task);
        manager.deleteTask(task.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getAllTasks());
    }

    @Test
    public void shouldDeleteEpic() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        manager.deleteEpic(epic.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getAllEpics());
    }

    @Test
    public void shouldNotDeleteTaskIfWrongId() {
        Task task = addNewTask();
        manager.addNewTask(task);
        manager.deleteTask(333);
        assertEquals(List.of(task), manager.getAllTasks());
    }

    @Test
    public void shouldNotDeleteEpicIfWrongId() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        manager.deleteEpic(333);
        assertEquals(List.of(epic), manager.getAllEpics());
    }

    @Test
    public void shouldNotDeleteSubtaskIfWrongId() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addNewSubtask(epic);
        manager.addNewSubtask(subtask);
        manager.deleteSubtask(333);
        assertEquals(List.of(subtask), manager.getAllSubtasks());
        assertEquals(List.of(subtask.getId()), manager.getEpic(epic.getId()).getSubtasksIds());
    }

    @Test
    public void shouldNoDoingIfTaskHashMapIsEmpty() {
        manager.deleteAllTasks();
        manager.deleteTask(333);
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    public void shouldNoDoingIfEpicHashMapIsEmpty() {
        manager.deleteAllEpics();
        manager.deleteEpic(333);
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Test
    public void shouldNoDoingIfSubtaskHashMapIsEmpty() {
        manager.deleteAllEpics();
        manager.deleteSubtask(333);
        assertEquals(0, manager.getAllSubtasks().size());
    }

    @Test
    void shouldReturnEmptyListForEmptyGetEpicSubtasks() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        List<Subtask> subtasks = manager.getEpicSubtasks(epic.getId());
        assertTrue(subtasks.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForNoTasks() {
        assertTrue(manager.getAllTasks().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForNoEpics() {
        assertTrue(manager.getAllEpics().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForNoSubtasks() {
        assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldReturnNullForNotExistTasks() {
        assertNull(manager.getTask(333));
    }

    @Test
    public void shouldReturnNullIForNotExistEpics() {
        assertNull(manager.getEpic(333));
    }

    @Test
    public void shouldReturnNullForNotExistSubtasks() {
        assertNull(manager.getSubtask(333));
    }

    @Test
    public void shouldReturnEmptyHistory() {
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldReturnEmptyHistoryForNotExistTask() {
        manager.getTask(333);
        manager.getSubtask(333);
        manager.getEpic(333);
        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    public void shouldReturnHistoryWithTask() {
        Epic epic = addNewEpic();
        manager.addNewEpic(epic);
        Subtask subtask = addNewSubtask(epic);
        manager.addNewSubtask(subtask);
        manager.getEpic(epic.getId());
        manager.getSubtask(subtask.getId());
        List<Task> list = manager.getHistory();
        assertEquals(2, list.size());
        assertTrue(list.contains(subtask));
        assertTrue(list.contains(epic));
    }

}