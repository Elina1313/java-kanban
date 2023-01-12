package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import TaskType.TaskType;
import tasks.TaskStatus;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV
 * id, type, name, status, description, epic
 * 1, TASK, Task1, NEW, Description task1,
 * 2, EPIC, Epic2, DONE, Description epic2,
 * 3, SUBTASK, Subtask2, DONE, Description subtask3,2
 * <p>
 * 2,3
 */

public class CSVTaskFormat {

    public static TaskType getType(Task task) {

        if (task instanceof Subtask) {
            return TaskType.SUBTASK;
        } else if (task instanceof Epic) {
            return TaskType.EPIC;
        }
        return TaskType.TASK;
    }

    public static String toString(Task task) {
        String epicId = "";
        if (task instanceof Subtask) {
            epicId = String.valueOf(((Subtask) task).getEpicId());
        }
        return task.getId() + "," + getType(task) + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "," + epicId;
    }

    public static Task taskFromString(String value) {
        final String[] values = value.split(",");
        final int id = Integer.parseInt(values[0]);
        final TaskType type = TaskType.valueOf(values[1]);
        if (values[1].equals("EPIC")) {
            Epic epic = new Epic(values[4], values[2], TaskStatus.valueOf(values[3].toUpperCase()));
            epic.setId(Integer.parseInt(values[0]));
            epic.setStatus(TaskStatus.valueOf(values[3].toUpperCase()));
            return epic;
        } else if (values[1].equals("SUBTASK")) {
            Subtask subtask = new Subtask(values[4], values[2], TaskStatus.valueOf(values[3].toUpperCase()),
                    Integer.parseInt(values[5]), Instant.ofEpochMilli(60L), 0);
            subtask.setId(Integer.parseInt(values[0]));
            return subtask;
        } else {
            Task task = new Task(values[4], values[2], TaskStatus.valueOf(values[3].toUpperCase()));
            task.setId(Integer.parseInt(values[0]));
            return task;
        }
    }




    public static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder sb = new StringBuilder();

        if (history.isEmpty()) {
            return "";
        }

        for (Task task : history) {
            sb.append(task.getId()).append(",");
        }

        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    public static List<Integer> historyFromString(String value) {
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

}
