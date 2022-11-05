import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task taskFirst = new Task("taskFirst", "desFirst", TaskStatus.NEW);
        Task taskSecond = new Task("taskSecond", "desSecond", TaskStatus.NEW);
        final int taskIdFirst = manager.addNewTask(taskFirst);
        final int taskIdSecond = manager.addNewTask(taskSecond);

        Epic epicFirst = new Epic("epicFirst", "desFirst", TaskStatus.NEW);
        Epic epicSecond = new Epic("epicSecond", "desSecond", TaskStatus.NEW);
        final int epicIdFirst = manager.addNewEpic(epicFirst);
        final int epicIdSecond = manager.addNewEpic(epicSecond);

        Subtask subtaskFirst = new Subtask("subtaskFirst", "desFirst", TaskStatus.NEW, epicIdFirst);
        Subtask subtaskSecond = new Subtask("subtaskSecond", "desSecond", TaskStatus.NEW, epicIdFirst);
        Subtask subtaskThird = new Subtask("subtaskThird", "desThird", TaskStatus.NEW, epicIdSecond);
        final int subtaskIdFirst = manager.addNewTask(subtaskFirst);
        final int subtaskIdSecond = manager.addNewTask(subtaskSecond);
        final int subtaskIdThird = manager.addNewTask(subtaskThird);
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());
        System.out.println();

        manager.updateTask(new Task(taskFirst.getId(), "taskFirst", "desFirst", TaskStatus.IN_PROGRESS));
        manager.updateTask(new Task(taskSecond.getId(), "taskSecond", "desSecond", TaskStatus.DONE));
        manager.updateSubtask(subtaskFirst = new Subtask(subtaskFirst.getId(), "subtaskFirst", "desFirst", TaskStatus.IN_PROGRESS, epicIdFirst));
        manager.updateSubtask(new Subtask(subtaskSecond.getId(), "subtaskSecond", "desSecond", TaskStatus.DONE, epicIdFirst));
        manager.updateSubtask(new Subtask(subtaskFirst.getId(), "subtaskFirst", "desFirst", TaskStatus.DONE, epicIdSecond));
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());
        System.out.println();

        System.out.println(manager.getTask(taskIdFirst));
        System.out.println(manager.getTask(taskIdSecond));
        System.out.println(manager.getSubtask(subtaskIdFirst));
        System.out.println(manager.getSubtask(subtaskIdSecond));
        System.out.println(manager.getEpic(epicIdFirst));
        System.out.println(manager.getEpic(epicIdSecond));
        System.out.println(manager.getHistory());

    }
}
