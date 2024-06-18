package managers;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.HashMap;
import java.util.List;

public interface TaskManager {

    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Epic> getEpics();

    HashMap<Integer, Subtask> getSubtasks();

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
