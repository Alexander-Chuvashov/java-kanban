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
     }
        private static void addTasks() {
        Task washDishes = new Task("Помыть посуду", "Загрузить посудомойку");
        inMemoryTaskManager.addTask(washDishes);
        System.out.println(washDishes);
            System.out.println();

        Task washDishesToUpdate = new Task(washDishes.getId(), "Помыть посуду", "Купить средство для мытья посуды", Stat.IN_PROGRESS);
        inMemoryTaskManager.updateTask(washDishesToUpdate);
        inMemoryTaskManager.addTask(new Task("Убраться в комнате", "Включить робот-пылесос"));
        System.out.println(washDishesToUpdate);
        inMemoryTaskManager.addTask(new Task("Вынести мусор", "Проверить наличие новых мешков"));
        System.out.println(washDishesToUpdate);
            System.out.println();

        Epic familyHolidays = new Epic(1, "Организовать семейный праздник", "Составить список участников", Stat.IN_PROGRESS);
        inMemoryTaskManager.addEpic(familyHolidays);
        System.out.println(familyHolidays);
            System.out.println();

        Subtask familyHolidaysSubtask1 = new Subtask("Организовать семейный праздник", "Продумать меню", familyHolidays.getId());
        Subtask familyHolidaysSubtask2 = new Subtask("Организовать семейный праздник", "Подобрать праздничную одежду" , familyHolidays.getId());
        Subtask familyHolidaysSubtask3 = new Subtask("Организовать семейный праздник", "Подобрать украшения" , familyHolidays.getId());
        inMemoryTaskManager.addSubtask(familyHolidaysSubtask1);
        inMemoryTaskManager.addSubtask(familyHolidaysSubtask2);
        inMemoryTaskManager.addSubtask(familyHolidaysSubtask3);
        System.out.println(familyHolidaysSubtask1);
        System.out.println(familyHolidaysSubtask2);
        System.out.println(familyHolidaysSubtask3);

            familyHolidaysSubtask2.setStat(Stat.DONE);
        inMemoryTaskManager.updateSubtask(familyHolidaysSubtask3);
    }

}
