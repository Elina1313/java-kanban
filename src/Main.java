import manager.FileBackedTaskManager;
import manager.Managers;
import tasks.TaskStatus;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {

        //Path path = Path.of("data.csv");
        File file = new File("data.csv");
        FileBackedTaskManager manager = new FileBackedTaskManager(file);

        Task firstTask = new Task("taskFirst", "desFirst", TaskStatus.NEW);
        manager.addNewTask(firstTask);
        Task secondTask = new Task("taskSecond", "desSecond", TaskStatus.NEW);
        manager.addNewTask(secondTask);

        Epic firstEpic = new Epic("epicFirst", "desFirst", TaskStatus.NEW);
        manager.addNewEpic(firstEpic);
        Epic secondEpic = new Epic("epicSecond", "desSecond", TaskStatus.NEW);
        manager.addNewEpic(secondEpic);

        Subtask firstSubtask = new Subtask("subtaskFirst", "desFirst", TaskStatus.NEW, firstEpic.getId());
        manager.addNewSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask("subtaskSecond", "desSecond", TaskStatus.NEW, firstEpic.getId());
        manager.addNewSubtask(secondSubtask);
        Subtask thirdSubtask = new Subtask("subtaskThird", "desThird", TaskStatus.NEW, firstEpic.getId());
        manager.addNewSubtask(thirdSubtask);


        System.out.println(manager.getTask(firstTask.getId()));
        System.out.println(manager.getEpic(firstEpic.getId()));
        System.out.println(manager.getEpic(secondEpic.getId()));
        System.out.println(manager.getSubtask(firstSubtask.getId()));
        System.out.println(manager.getSubtask(secondSubtask.getId()));

        System.out.println();




        /*TaskManager manager = Managers.getInMemoryTaskManager(Managers.getDefaultHistory());

        manager.addNewTask(new Task("taskFirst", "desFirst", TaskStatus.NEW));
        manager.addNewTask(new Task("taskSecond", "desSecond", TaskStatus.NEW));
        manager.addNewEpic(new Epic("epicFirst", "desFirst", TaskStatus.NEW));
        manager.addNewEpic(new Epic("epicSecond", "desSecond", TaskStatus.NEW));
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
        System.out.println(historyAfterRemove);*/

    }
}
