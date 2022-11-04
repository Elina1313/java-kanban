package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    List<Task> viewHistory = new ArrayList<>();

    public void addTask(Task task) {
        if (viewHistory.size() > 10) {
            viewHistory.remove(0);
            viewHistory.add(task);
        } else {
            viewHistory.add(task);
        }
    }

    public List<Task> getHistory() {
        return viewHistory;
    }

}
