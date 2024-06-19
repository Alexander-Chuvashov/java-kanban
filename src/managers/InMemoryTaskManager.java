package managers;

import task.Epic;
import task.Subtask;
import task.Task;
import status.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, Task> tasks = new HashMap<>(); //Объявление задач
    protected Map<Integer, Epic> epics = new HashMap<>(); //Объявление мап для эпик
    protected Map<Integer, Subtask> subtasks = new HashMap<>(); //Объявления мап для подзадач
    protected HistoryManager historyManager = Managers.getDefaultHistory();

    private int nextID = 1;


    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }


    // Методы
    //Добавить задачу
    @Override
    public Task addTask(Task task) {
        task.setId(getNextID());
        tasks.put(task.getId(), task);
        return task;
    }

    //Добавить эпик
    @Override
    public Epic addEpic(Epic epic) {
        epic.setId(getNextID());
        epics.put(epic.getId(), epic);
        return epic;
    }

    //Добавить подзадачу
    @Override
    public Subtask addSubtask(Subtask subtask) {
        subtask.setId(getNextID());
        Epic epic = epics.get(subtask.getEpicID());
        epic.addSubtask(subtask);
        subtasks.put(subtask.getId(), subtask);
        updateEpicStat(epic);
        return subtask;
    }

    // Методы для удаления
    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic :epics.values()) {
            epic.clearSubtasks();
            epic.setStat(Stat.NEW);
        }
    }

    @Override
    public Task deleteTaskById(int id) {
        return tasks.remove(id);
    }


    // Методы для обновления
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
        List<Subtask> oldEpicSubtaskList = oldEpic.getSubtasksList();
        if (!oldEpicSubtaskList.isEmpty()) {
            for (Subtask subtask : oldEpicSubtaskList) {
                subtasks.remove(subtask.getId());
            }
        }
        epics.replace(epicId, epic);
        List<Subtask> newEpicSubtaskList = epic.getSubtasksList();
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
        if (!subtasks.containsKey(subtaskId)) {
            return null;
        }
        int epicID = subtask.getEpicID();
        Subtask oldenSubtask = subtasks.get(subtaskId);
        subtasks.replace(subtaskId, subtask);
        Epic epic = epics.get(epicID);
        List<Subtask> subtaskList = epic.getSubtasksList();
        subtaskList.remove(oldenSubtask);
        subtaskList.add(subtask);
        updateEpicStat(epic);
        return subtask;
    }

    //получить задачу по id
    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    //получить эпик по id
    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    //получить подзадачу по id
    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    //Получаем историю
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    // обновление статистики эпика
    private void updateEpicStat(Epic epic) {
        int allIsDoneCount = 0;
        int allIsNewCount = 0;
        List<Subtask> list = epic.getSubtasksList();
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

    //ПОлучение следующего id
    private int getNextID() {
        return nextID++;
    }
}