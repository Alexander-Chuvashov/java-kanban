package task;

import status.Stat;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Subtask> subtasksList = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, String description, Stat stat) { super(id, name, description, stat); }



    public void addSubtask(Subtask subtask) {
        subtasksList.add(subtask);
    }

    public void clearSubtasks() {
        subtasksList.clear();
    }


    public List<Subtask> getSubtasksList() {
        return subtasksList;
    }

    public void setSubtasksList(List<Subtask> subtasksList) {
        this.subtasksList = subtasksList;
    }

    @Override
    public String toString() {
        return "task.Epic{" +
                "name=" + getName() + "\'" +
                ", description=" + getDescription() + "\'" +
                ", id=" + getId() +
                ", subtaskList.size=" + subtasksList.size() +
                ", stat=" + getStat() +
                '}';
    }
}
