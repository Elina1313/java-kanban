package http_api;

import TypeAdapter.InstantTime;
import com.google.gson.*;
import manager.FileBackedTaskManager;
import manager.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.time.Instant;
import java.util.stream.Collectors;

public class HTTPTaskManager extends FileBackedTaskManager {

    final static String KEY_TASKS = "tasks";
    final static String KEY_SUBTASKS = "subtasks";
    final static String KEY_EPICS = "epics";
    final static String KEY_HISTORY = "history";
    final KVTaskClient client;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantTime()).create();

    public HTTPTaskManager(HistoryManager historyManager, String path) throws IOException, InterruptedException {
        super(historyManager);

        client = new KVTaskClient(path);

        JsonElement jsonTasks = JsonParser.parseString(client.load(KEY_TASKS));

        if (!jsonTasks.isJsonNull()) {

            JsonArray jsonTasksArray = jsonTasks.getAsJsonArray();

            for (JsonElement jsonTask : jsonTasksArray) {
                Task task = gson.fromJson(jsonTask, Task.class);
                this.createTask(task);
            }

        }

        JsonElement jsonEpics = JsonParser.parseString(client.load(KEY_EPICS));

        if (!jsonEpics.isJsonNull()) {

            JsonArray jsonEpicsArray = jsonEpics.getAsJsonArray();

            for (JsonElement jsonEpic : jsonEpicsArray) {
                Epic task = gson.fromJson(jsonEpic, Epic.class);
                this.createEpic(task);
            }

        }

        JsonElement jsonSubtasks = JsonParser.parseString(client.load(KEY_SUBTASKS));

        if (!jsonSubtasks.isJsonNull()) {

            JsonArray jsonSubtasksArray = jsonSubtasks.getAsJsonArray();

            for (JsonElement jsonSubtask : jsonSubtasksArray) {
                Subtask task = gson.fromJson(jsonSubtask, Subtask.class);
                this.createSubtask(task);
            }

        }

        JsonElement jsonHistoryList = JsonParser.parseString(client.load(KEY_HISTORY));

        if (!jsonHistoryList.isJsonNull()) {

            JsonArray jsonHistoryArray = jsonHistoryList.getAsJsonArray();

            for (JsonElement jsonTaskId : jsonHistoryArray) {

                int taskId = jsonTaskId.getAsInt();

                if (this.subtasks.containsKey(taskId)) {
                    this.getSubtask(taskId);
                } else if (this.epics.containsKey(taskId)) {
                    this.getEpic(taskId);
                } else if (this.tasks.containsKey(taskId)) {
                    this.getTask(taskId);
                }
            }
        }
    }

    @Override
    public void save() {

        client.put(KEY_TASKS, gson.toJson(tasks.values()));
        client.put(KEY_SUBTASKS, gson.toJson(subtasks.values()));
        client.put(KEY_EPICS, gson.toJson(epics.values()));

        client.put(KEY_HISTORY, gson.toJson(this.getHistory().stream().map(Task::getId).collect(Collectors.toList())));

    }

}