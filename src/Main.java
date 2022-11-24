import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.List;

public class Main {

    public static void maind(String[] args) {
        TaskManager manager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());

        manager.addNewTask(new Task("taskFirst", "desFirst", TaskStatus.NEW));
        manager.addNewTask(new Task("taskSecond", "desSecond", TaskStatus.NEW));
        manager.addNewEpic(new Epic("epicFirst", "desFirst", TaskStatus.NEW));
        manager.addNewEpic(new Epic("epicSecond", "desFirst", TaskStatus.NEW));
        manager.addNewSubtask(new Subtask("subtaskFirst", "desFirst", TaskStatus.NEW, 3));
        manager.addNewSubtask(new Subtask("subtaskSecond", "desSecond", TaskStatus.NEW, 3));
        manager.addNewSubtask(new Subtask("subtaskThird", "desThird", TaskStatus.NEW, 3));

        manager.getTask(1);
        manager.getEpic(3);
        manager.getEpic(3);
        manager.getEpic(3);
        manager.getTask(1);
        manager.getEpic(4);
        manager.getSubtask(5);
        manager.getSubtask(5);
        manager.getSubtask(6);

        System.out.println("Get History");
        List<Task> history = manager.getHistory();
        System.out.println(history);

        manager.remove(1);
        manager.deleteEpic(3);

        System.out.println("Get History after remove");
        List<Task> historyAfterRemove = manager.getHistory();
        System.out.println(historyAfterRemove);

    }
}
