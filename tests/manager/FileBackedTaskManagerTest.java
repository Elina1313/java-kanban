package manager;

import org.junit.jupiter.api.*;
import tasks.TaskStatus;
import tasks.Epic;
import tasks.Task;

import java.util.Collections;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;


class FileBackedTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    public static final Path path = Path.of("data.csv");
    File file = new File(String.valueOf(path));

    @BeforeEach
    public void beforeEach() {

        manager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);
    }

    @AfterEach
    public void afterEach() {
        try {
            Files.delete(path);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void shouldSaveAndLoad() {
        Task task = new Task("Title", "Description", TaskStatus.NEW, Instant.now(), 0);
        manager.addNewTask(task);
        Epic epic = new Epic("Title", "Description", TaskStatus.NEW, Instant.now(), 0);
        manager.addNewEpic(epic);
        FileBackedTaskManager fileManager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);
        fileManager.loadFromFile(file);
        Assertions.assertEquals(List.of(task), manager.getAllTasks());
        Assertions.assertEquals(List.of(epic), manager.getAllEpics());
    }

    @Test
    public void shouldSaveAndLoadEmptyDifferentTasks() {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);
        fileManager.save();
        fileManager.loadFromFile(file);
        Assertions.assertTrue(manager.getAllTasks().isEmpty());
        Assertions.assertTrue(manager.getAllEpics().isEmpty());
        Assertions.assertTrue(manager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldSaveAndLoadEmptyHistory() {
        FileBackedTaskManager fileManager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);
        fileManager.save();
        fileManager.loadFromFile(file);
        Assertions.assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }
}