package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void beforeEach() {

        manager = new InMemoryTaskManager(Managers.getDefaultHistory());

    }

    @Test
    public void createInMemoryTaskManagerTest() {

        manager.deleteAllTasks();
        List<Task> tasks = manager.getAllTasks();
        assertNotNull(tasks, "Возвращает пустой список задач");

    }

}

