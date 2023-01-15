package http_tests;

import http_api.HTTPTaskManager;
import http_api.KVServer;
import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import manager.TaskManagerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.*;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HTTPTaskManagerTest<T extends TaskManagerTest<HTTPTaskManager>> {
    private KVServer server;
    private TaskManager manager;

    @BeforeEach
    public void createManager() {
        try {
            server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            manager = Managers.getDefault(historyManager);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при создании менеджера");
        }
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void shouldLoadTasks() {
        Task task1 = new Task("description1", "name1", TaskStatus.NEW, Instant.now(), 1);
        Task task2 = new Task("description2", "name2", TaskStatus.NEW, Instant.now(), 2);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getAllTasks(), list);
    }

    @Test
    public void shouldLoadEpics() {
        Epic epic1 = new Epic("description1", "name1", TaskStatus.NEW, Instant.now(), 3);
        Epic epic2 = new Epic("description2", "name2", TaskStatus.NEW, Instant.now(), 4);
        manager.addNewEpic(epic1);
        manager.addNewEpic(epic2);
        manager.getEpic(epic1.getId());
        manager.getEpic(epic2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getAllEpics(), list);
    }

    @Test
    public void shouldLoadSubtasks() {
        Epic epic1 = new Epic("description1", "name1", TaskStatus.NEW, Instant.now(), 5);
        Subtask subtask1 = new Subtask("description1", "name1", TaskStatus.NEW, epic1.getId()
                , Instant.now(), 6);
        Subtask subtask2 = new Subtask("description2", "name2", TaskStatus.NEW, epic1.getId(),
                Instant.now(), 7);
        manager.addNewSubtask(subtask1);
        manager.addNewSubtask(subtask2);
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getAllSubtasks(), list);
    }

}