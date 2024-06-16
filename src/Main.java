import managers.Managers;
import managers.TaskManager;
import status.Stat;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {

    private static final TaskManager inMemoryTaskManager = Managers.getDefault();

    public static void main(String[] args) {

        addTasks();
        printAllTasks();
        printHistory();

    }
        private static void addTasks() {
        Task washDishes = new Task("Помыть посуду", "Загрузить посудомойку");
        inMemoryTaskManager.addTask(washDishes);

        Task washDishesToUpdate = new Task(washDishes.getId(), "Помыть посуду", "Купить средство для мытья посуды", Stat.IN_PROGRESS);
        inMemoryTaskManager.updateTask(washDishesToUpdate);
        inMemoryTaskManager.addTask(new Task("Убраться в комнате", "Включить робот-пылесос"));
        inMemoryTaskManager.addTask(new Task("Вынести мусор", "Проверить наличие новых мешков"));

        Epic familyHolidays = new Epic(1, "Организовать семейный праздник", "Составить список участников", Stat.IN_PROGRESS);
        inMemoryTaskManager.addEpic(familyHolidays);

        Subtask familyHolidaysSubtask1 = new Subtask("Организовать семейный праздник", "Продумать меню", familyHolidays.getId());
        Subtask familyHolidaysSubtask2 = new Subtask("Организовать семейный праздник", "Подобрать праздничную одежду" , familyHolidays.getId());
        Subtask familyHolidaysSubtask3 = new Subtask("Организовать семейный праздник", "Подобрать украшения" , familyHolidays.getId());
        inMemoryTaskManager.addSubtask(familyHolidaysSubtask1);
        inMemoryTaskManager.addSubtask(familyHolidaysSubtask2);
        inMemoryTaskManager.addSubtask(familyHolidaysSubtask3);

        familyHolidaysSubtask2.setStat(Stat.DONE);
        inMemoryTaskManager.updateSubtask(familyHolidaysSubtask3);
    }

    private static void printAllTasks() {
        System.out.println("Tasks:");
        for (Task task: Main.inMemoryTaskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println();

        System.out.println("Epics:");
        for (Epic epic : Main.inMemoryTaskManager.getEpics()) {
            System.out.println(epic);
            for (Task task : Main.inMemoryTaskManager.getEpicSubtasks(epic)) {
                System.out.println("--> " + task);
            }
        }
        System.out.println();

        System.out.println("Subtasks:");
        for (Task subtask : Main.inMemoryTaskManager.getSubtasks()) {
            System.out.println(subtask);
        }
        System.out.println();
    }

    private static void printHistory() {
        Main.inMemoryTaskManager.getTaskById(1);
        Main.inMemoryTaskManager.getEpicById(2);
        Main.inMemoryTaskManager.getEpicById(3);
        Main.inMemoryTaskManager.getTaskById(2);
        Main.inMemoryTaskManager.getSubtaskById(2);
        Main.inMemoryTaskManager.getSubtaskById(3);
        Main.inMemoryTaskManager.getSubtaskById(1);
        Main.inMemoryTaskManager.getTaskById(3);
        Main.inMemoryTaskManager.getEpicById(1);

        System.out.println("History:");
        for (Task task : Main.inMemoryTaskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
