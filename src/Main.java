import http_api.KVServer;
import tasks.TaskStatus;
import TypeAdapter.InstantTime;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.time.Instant;

public class Main {

    public static void main(String[] args) {

        KVServer server;

        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantTime()).create();

            server = new KVServer();
            server.start();

            HistoryManager historyManager = Managers.getDefaultHistory();

            TaskManager httpTaskManager = Managers.getDefault(historyManager);

            Task task1 = new Task("Name Task1", "DesTask1", TaskStatus.NEW, Instant.now(), 1);
            httpTaskManager.addNewTask(task1);

            Epic epic1 = new Epic("Name Epic1", "DesEpic1", TaskStatus.NEW, Instant.now(), 2);
            httpTaskManager.addNewEpic(epic1);

            Subtask subtask1 = new Subtask("Name Subtask1", "NDesSubtask1", TaskStatus.NEW, epic1.getId(), Instant.now(), 3);
            httpTaskManager.addNewSubtask(subtask1);


            httpTaskManager.getTask(task1.getId());
            httpTaskManager.getEpic(epic1.getId());
            httpTaskManager.getSubtask(subtask1.getId());

            System.out.println(gson.toJson(httpTaskManager.getAllTasks()));
            System.out.println(gson.toJson(httpTaskManager.getAllEpics()));
            System.out.println(gson.toJson(httpTaskManager.getAllSubtasks()));
            System.out.println(httpTaskManager);
            server.stop();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
