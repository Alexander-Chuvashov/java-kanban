package task;

import status.Stat;

public class Subtask extends Task {

    private final int epicID;

    public Subtask(String name, String description, int epicID) {
        super(name, description);
        this.epicID = epicID;
    }

    public Subtask(int id, String name, String description, Stat stat, int epicID) {
        super(id, name, description, stat);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "task.Subtask{" +
                "name='" + getName() + "\'" +
                ", description=" + getDescription() + "\'" +
                ", id=" + getId() +
                ", epicID=" + epicID +
                ", stat=" + getStat() +
                "}";
    }
}
