import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int nextID = 1;

    public int getNextID() {
        return nextID++;
    }

    public Task addTask(Task task) {
        task.setId(getNextID());
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic addEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStat(epic);
        return subtask;
    }

    public Task updateTask(Task task) {
        Integer taskID = task.getId();
        if (task == null || tasks.containsKey(taskID)) {
            return null;
        }
        tasks.replace(taskID, task);
        return task;
    }



    public Subtask updateSubtask(Subtask subtask) {
        Integer sibtaskID = subtask.getId();
        if (sibtaskID == null || !subtasks.containsKey(sibtaskID)) {
            return null;
        }
        int epicID = subtask.getEpicID();
        Subtask oldenSubtask = subtasks.get(sibtaskID);
        subtasks.replace(sibtaskID, subtask);
        Epic epic = epics.get(epicID);
        ArrayList<Subtask> subtaskList = epic.getSubtasksList();
        subtaskList.remove(oldenSubtask);
        subtaskList.add(subtask);
        epic.setSubtasksList(subtaskList);
        updateEpicStat(epic);
        return subtask;
    }



    private void updateEpicStat(Epic epic) {
        int allIsDoneCount = 0;
        int allIsNewCount = 0;
        ArrayList<Subtask> list = epic.getSubtasksList();
        for (Subtask subtask: list) {
            if (subtask.getStat() == Stat.DONE) {
                allIsDoneCount++;
            }
            if (subtask.getStat() == Stat.NEW) {
                allIsNewCount++;
            }

        }
        if (allIsDoneCount == list.size()) {
            epic.setStat(Stat.DONE);
        } else if (allIsNewCount == list.size()) {
            epic.setStat(Stat.NEW);
        } else {
            epic.setStat(Stat.IN_PROGRESS);
        }
    }

}
