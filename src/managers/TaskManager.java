package managers;

import task.Epic;
import task.Subtask;
import task.Task;


import java.util.List;
import java.util.Map;

public interface TaskManager {

    List<Task> getTasks();

    List<Epic> getEpics();

    List<Subtask> getSubtasks();

    Task addTask(Task task);

    Epic addEpic(Epic epic);

    Subtask addSubtask(Subtask subtask);

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    void deleteTasks();

    void deleteEpics();

    void deleteSubtasks();

    Task deleteTaskById(int i);

    List<Task> getHistory();

}
