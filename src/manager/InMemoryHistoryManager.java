package manager;


import tasks.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private static class CustomLinkedList {
        private final Map<Integer, Node> nodeMap = new HashMap<>();
        private final Map<Integer, Node> nodeHashMap = new HashMap<>();
        private Node head;
        private Node tail;

        private List<Task> getTasks() {
            List<Task> tasks = new ArrayList<>();
            Node element = head;
            while (element != null) {
                tasks.add(element.getTask());
                element = element.getNext();
            }
            return tasks;
        }

        private void linkLast(Task task) {
            Node element = new Node();
            element.setTask(task);

            if (nodeHashMap.containsKey(task.getId())) {
                removeNode(nodeHashMap.get(task.getId()));
            }

            if (head == null) {
                tail = element;
                head = element;
                element.setNext(null);
                element.setPrev(null);
            } else {
                element.setPrev(tail);
                element.setNext(null);
                tail.setNext(element);
                tail = element;
            }

            nodeHashMap.put(task.getId(), element);
        }

        private void removeNode(Node node) {
            if (node != null) {
                nodeHashMap.remove(node.getTask().getId());
                Node prev = node.getPrev();
                Node next = node.getNext();

                if (head == node) {
                    head = node.getNext();
                }

                if (tail == node) {
                    tail = node.getPrev();
                }

                if (prev != null) {
                    prev.setNext(next);
                }

                if (next != null) {
                    next.setPrev(prev);
                }
            }
        }

        private Node getNode(int id) {
            return nodeHashMap.get(id);
        }
    }

    private final CustomLinkedList list = new CustomLinkedList();

    @Override
    public void add(Task task) {
        list.linkLast(task);
    }

    @Override
    public void remove(int id) {
        list.removeNode(list.getNode(id));
    }

    @Override
    public List<Task> getHistory() {
        return list.getTasks();
    }
}

class Node {
    private Task task;
    private Node prev;
    private Node next;

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public Task getTask() {
        return task;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}