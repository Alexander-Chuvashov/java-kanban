import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();



    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubstaks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getEpicSubtasks(Epic epic) {
        return epic.getSubtasksList();
    }



    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            epic.setStat(Stat.NEW);
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        ArrayList<Subtask> epicSubtasks = epics.remove(id).getSubtasksList();
        for (Subtask subtask : epicSubtasks) {
            subtasks.remove(subtask.getId());
        }
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        int epicId = subtask.getEpicID();
        subtasks.remove(id);
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> subtaskList = epic.getSubtasksList();
        subtaskList.remove(subtask);
        epic.setSubtasksList(subtaskList);
        updateEpicStat(epic);
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
        if (!tasks.containsKey(taskID)) return null;
        tasks.replace(taskID, task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        Integer epicId = epic.getId();
        if (!epics.containsKey(epicId)) return null;
        Epic oldEpic = epics.get(epicId);
        ArrayList<Subtask> oldEpicSubtaskList = oldEpic.getSubtasksList();
        if (!oldEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : oldEpicSubtaskList) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.replace(epicId, epic);
        ArrayList<Subtask> newEpicSubtaskList = epic.getSubtasksList();
        if (!newEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : newEpicSubtaskList) {
                subtasks.put(subtask.getId(), subtask);
            }
        }
        updateEpicStat(epic);
        return epic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        Integer subtaskId = subtask.getId();
        int epicID = subtask.getEpicID();
        Subtask oldenSubtask = subtasks.get(subtaskId);
        subtasks.replace(subtaskId, subtask);
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

    private int nextID = 1;

    private int getNextID() {
        return nextID++;
    }

}
