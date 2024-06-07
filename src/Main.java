public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task washDishes = new Task("Помыть посуду", "Загрузить посудомойку");
        Task washDishesCreated = taskManager.addTask(washDishes);
        System.out.println(washDishesCreated);

        Task washDishesToUpdate = new Task(washDishes.getId(), "Помыть посуду", "Купить средство для мытья посуды", Stat.IN_PROGRESS);
        Task washDishesUpdated = taskManager.updateTask(washDishesToUpdate);
        System.out.println(washDishesUpdated);

        Epic familyHolidays = new Epic("Организовать семейный праздник", "Составить список участников");
        taskManager.addEpic(familyHolidays);
        System.out.println(familyHolidays);

        Subtask familyHolidaysSubtask1 = new Subtask("Организовать семейный праздник", "Продумать меню", familyHolidays.getId());
        Subtask familyHolidaysSubtask2 = new Subtask("Организовать семейный праздник", "Подобрать праздничную одежду" , familyHolidays.getId());
        taskManager.addSubtask(familyHolidaysSubtask1);
        taskManager.addSubtask(familyHolidaysSubtask2);
        System.out.println(familyHolidaysSubtask1);

        familyHolidaysSubtask2.setStat(Stat.DONE);
        taskManager.updateSubtask(familyHolidaysSubtask2);
        System.out.println(familyHolidaysSubtask2);
    }
}
