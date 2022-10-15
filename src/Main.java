import manager.TasksManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TasksManager manager = new TasksManager();

        Task taskFirst = new Task("taskFirst", "desFirst", "NEW");
        Task taskSecond = new Task("taskSecond", "desSecond", "NEW");
        final int taskIdFirst = manager.addNewTask(taskFirst);
        final int taskIdSecond = manager.addNewTask(taskSecond);

        Epic epicFirst = new Epic("epicFirst", "desFirst", "NEW");
        Epic epicSecond = new Epic("epicSecond", "desSecond", "NEW");
        final int epicIdFirst = manager.addNewEpic(epicFirst);
        final int epicIdSecond = manager.addNewEpic(epicSecond);

        Subtask subtaskFirst = new Subtask("subtaskFirst", "desFirst", "NEW", epicIdFirst);
        Subtask subtaskSecond = new Subtask("subtaskSecond", "desSecond", "NEW", epicIdFirst);
        Subtask subtaskThird = new Subtask("subtaskThird", "desThird", "NEW", epicIdSecond);
        final int subtaskIdFirst = manager.addNewTask(subtaskFirst);
        final int subtaskIdSecond = manager.addNewTask(subtaskSecond);
        final int subtaskIdThird = manager.addNewTask(subtaskThird);
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());
        System.out.println();

        manager.updateTask(taskFirst = new Task(taskFirst.getId(), "taskFirst", "desFirst", "IN_PROGRESS"));
        manager.updateTask(taskSecond = new Task(taskSecond.getId(), "taskSecond", "desSecond", "DONE"));
        manager.updateSubtask(subtaskFirst = new Subtask(subtaskFirst.getId(), "subtaskFirst", "desFirst", "IN_PROGRESS", epicIdFirst));
        manager.updateSubtask(subtaskSecond = new Subtask(subtaskSecond.getId(), "subtaskSecond", "desSecond", "DONE", epicIdFirst));
        manager.updateSubtask(subtaskFirst = new Subtask(subtaskFirst.getId(), "subtaskFirst", "desFirst", "DONE", epicIdSecond));
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());
        System.out.println();

    }
}
