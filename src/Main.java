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

        Task task1 = new Task("Name Task1", "DesTask1", TaskStatus.NEW);
        int task1Id = manager.addNewTask(task1);
        System.out.println("task1Id: " + task1Id);
        Task task2 = new Task("Name Task2", "DesTask2", TaskStatus.NEW);
        int task2Id = manager.addNewTask(task2);
        System.out.println("task2Id: " + task2Id);
        Task task3 = new Task("Name Task3", "DesTask3", TaskStatus.NEW);
        int task3Id = manager.addNewTask(task3);
        System.out.println("task3Id: " + task3Id);


        Epic epic1 = new Epic("Name Epic1", "DesEpic1", TaskStatus.NEW);
        int epic1Id = manager.addNewEpic(epic1);
        System.out.println("epic1Id: " + epic1Id);

        Subtask subTask1 = new Subtask("Name Subtask1", "Des Subtask1", TaskStatus.NEW, epic1Id);
        int subTask1Id = manager.addNewSubtask(subTask1);

        Subtask subTask2 = new Subtask("Name Subtask2", "Des Subtask2", TaskStatus.NEW, epic1Id);
        int subTask2Id = manager.addNewSubtask(subTask2);
        System.out.println("subTask2Id: " + subTask2Id);

        Subtask subTask3 = new Subtask("Name Subtask3", "Des Subtask3", TaskStatus.NEW, epic1Id);
        int subTask3Id = manager.addNewSubtask(subTask3);
        System.out.println("subTask3Id: " + subTask3Id);

        Epic epic2 = new Epic("Name Epic2", "DesEpic2", TaskStatus.NEW);
        int epic2Id = manager.addNewEpic(epic2);

        System.out.println("\nTasks, Epics, Subtasks list");
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtask(subTask1.getId()));

        System.out.println("\nUpdate Task");
        Task taskUpdate = new Task("Update TaskName1", "Update desTask1", TaskStatus.IN_PROGRESS);
        taskUpdate.setId(1);
        manager.updateTask(taskUpdate);
        manager.updateTask(new Task("Update TaskName2", "Update desTask2", TaskStatus.DONE));

        System.out.println("\nUpdate Subtask");
        manager.updateSubtask(new Subtask("Update Subtask1 Epic1", "Update DesSubtask1", TaskStatus.IN_PROGRESS, epic1Id));
        manager.updateSubtask(new Subtask("Update Subtask2 Epic1", "Update DesSubtask1", TaskStatus.DONE, epic1Id));
        manager.updateSubtask(new Subtask("Update Subtask1 Epic2", "Update DesSubtask1", TaskStatus.DONE, epic2Id));

        System.out.println(manager.getTask(task2Id));
        System.out.println("HistoryList1: " + manager.getHistory());
        System.out.println(manager.getTask(task2Id));
        System.out.println("HistoryList2: " + manager.getHistory());
        System.out.println(manager.getTask(task1Id));
        System.out.println("HistoryList3: " + manager.getHistory());
        System.out.println(manager.getTask(task3Id));
        System.out.println("HistoryList4: " + manager.getHistory());
        manager.getTask(task3Id);
        manager.getTask(task3Id);
        manager.getTask(task3Id);
        manager.getTask(task2Id);
        manager.getTask(task2Id);
        manager.getTask(task3Id);
        manager.getTask(task3Id);
        manager.getTask(task1Id);
        manager.getTask(task1Id);
        System.out.println("\nHistoryListN: " + manager.getHistory());

        System.out.println("\nEpics");
        System.out.println(manager.getEpic(epic1Id));
        System.out.println("HistoryList: " + manager.getHistory());
        System.out.println(manager.getEpic(epic2Id));
        System.out.println("HistoryList: " + manager.getHistory());
        manager.getEpic(epic1Id);
        manager.getEpic(epic1Id);
        manager.getEpic(epic2Id);
        manager.getEpic(epic2Id);
        manager.getEpic(epic2Id);
        manager.getSubtask(subTask1Id);
        manager.getSubtask(subTask2Id);
        manager.getSubtask(subTask2Id);
        System.out.println("HistoryList: " + manager.getHistory());

        System.out.println(manager.getSubtask(subTask1Id));
        System.out.println("\nHistoryList: " + manager.getHistory());
        System.out.println(manager.getSubtask(subTask3Id));
        System.out.println("HistoryList: " + manager.getHistory());
        System.out.println(manager.getSubtask(subTask2Id));
        System.out.println("HistoryList: " + manager.getHistory());
        System.out.println(manager.getSubtask(subTask3Id));
        System.out.println("HistoryList: " + manager.getHistory());

        System.out.println(manager.getTask(task1Id));
        System.out.println("\nHistoryList: " + manager.getHistory());
        System.out.println(manager.getTask(task3Id));
        System.out.println("HistoryList: " + manager.getHistory());
        System.out.println(manager.getTask(task2Id));
        System.out.println("HistoryList: " + manager.getHistory());
        System.out.println(manager.getTask(task1Id));
        System.out.println("HistoryList: " + manager.getHistory());

        System.out.println();
        System.out.println("Загрузка из файла");
        FileBackedTaskManager manager2 = new FileBackedTaskManager(file);
        manager2.loadFromFile(file);
        System.out.println(manager2.getAllTasks());
        System.out.println(manager2.getAllEpics());
        System.out.println(manager2.getAllSubtasks());

    }
}
