package manager;

import TaskType.TaskType;
import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static manager.CSVTaskFormat.*;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    private static final String CSVTaskFormat = "id,name,type,status,description,startTime,duration,epicId\n";

    public FileBackedTaskManager(HistoryManager historyManager) {

        super(historyManager);
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    public void loadFromFile(File file) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {

            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if (line.equals("")) {
                    break;
                }
                Task task = fromString(line);

                if (task instanceof Epic epic) {
                    createEpic(epic);
                } else if (task instanceof Subtask subtask) {
                    createSubtask(subtask);
                } else {
                    createTask(task);
                }

            }

            String lineWithHistory = bufferedReader.readLine();
            for (int id : historyFromString(lineWithHistory)) {
                addToHistory(id);
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось считать данные из файла.");
        }

    }

    public void save() {

        try {
            if (Files.exists(file.toPath())) {
                Files.delete(file.toPath());
            }
            Files.createFile(file.toPath());
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось найти файл для записи данных");
        }

        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(CSVTaskFormat);

            for (Task task : getAllTasks()) {
                writer.write(toString(task) + "\n");
            }

            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic) + "\n");
            }

            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask) + "\n");
            }

            writer.write("\n");
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить в файл", e);
        }
    }

    private String toString(Task task) {
        String[] toJoin = {Integer.toString(task.getId()), getType(task).toString(), task.getName(),
                task.getStatus().toString(), task.getDescription(), String.valueOf(task.getStartTime()),
                String.valueOf(task.getDuration()), getParentEpicId(task)}; //getParentEpicId(task)
        return String.join(",", toJoin);
    }

    private Task fromString(String value) {
        String[] params = value.split(",");
        int id = Integer.parseInt(params[0]);
        String type = params[1];
        String name = params[2];
        TaskStatus status = TaskStatus.valueOf(params[3].toUpperCase());
        String description = params[4];
        Instant startTime = Instant.parse(params[5]);
        long duration = Long.parseLong(params[6]);
        Integer epicId = type.equals("SUBTASK") ? Integer.parseInt(params[7]) : null;

        if (type.equals("EPIC")) {
            Epic epic = new Epic(description, name, status, startTime, duration);
            epic.setId(id);
            epic.setStatus(status);
            return epic;
        } else if (type.equals("SUBTASK")) {
            Subtask subtask = new Subtask(description, name, status, epicId, startTime, duration);
            subtask.setId(id);
            return subtask;
        } else {
            Task task = new Task(description, name, status, startTime, duration);
            task.setId(id);
            return task;
        }
    }

    private TaskType getType(Task task) {
        if (task instanceof Epic) {
            return TaskType.EPIC;
        } else if (task instanceof Subtask) {
            return TaskType.SUBTASK;
        }
        return TaskType.TASK;
    }

    private String getParentEpicId(Task task) {
        if (task instanceof Subtask) {
            return Integer.toString(((Subtask) task).getEpicId());
        }
        return "";
    }

    @Override
    public Task addNewTask(Task task) {
        super.addNewTask(task);
        save();
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        super.addNewSubtask(subtask);
        save();
        return subtask;
    }

    public Task createTask(Task task) {
        return super.addNewTask(task);
    }

    public Epic createEpic(Epic epic) {
        return super.addNewEpic(epic);
    }

    public Subtask createSubtask(Subtask subtask) {
        return super.addNewSubtask(subtask);
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    /*@Override
    public void deleteAllSubtasksByEpic(Epic epic) {  //удалить?
        super.deleteAllSubtasksByEpic(epic);
        save();
    }*/

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> toReturn = new ArrayList<>();
        if (value != null) {
            String[] id = value.split(",");
            for (String number : id) {
                toReturn.add(Integer.parseInt(number));
            }
            return toReturn;
        }
        return toReturn;
    }

    static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory(); // = getHistoryManager().getHistory();
        StringBuilder str = new StringBuilder();
        if (history.isEmpty()) {
            return "";
        }
        for (Task task : history) {
            str.append(task.getId()).append(",");
        }

        if (str.length() != 0) {
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

}



