package task;

import status.Stat;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private Stat stat;
    
    public Task(int id, String name, String description, Stat stat) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stat = stat;
    }
    
    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.stat = Stat.NEW;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash += name.hashCode();
        }
        hash = hash * 31;
        if (description != null) {
            hash = hash + description.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "task.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", stat=" + stat +
                '}';
    }
}
