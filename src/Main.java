
public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Название таски", "Описание таски");
        Task task2 = new Task("Название таски2", "Описание таски2");
        Epic epic1 = new Epic("Название эпик1", "Описание эпик1");
        Epic epic2 = new Epic("Название эпик2", "Описание эпик2");
        Subtask subtask1 = new Subtask("Название сабтаски1", "Описание сабтаски1",3);
        Subtask subtask2 = new Subtask("Название сабтаски2", "Описание сабтаски2",3);
        Subtask subtask3 = new Subtask("Название сабтаски3", "Описание сабтаски3",4);
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubTask(subtask1);
        taskManager.addSubTask(subtask2);
        taskManager.addSubTask(subtask3);
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTasks());
        task2.setStatus(Status.IN_PROGRESS);
        task1.setStatus(Status.DONE);
        subtask3.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask1.setStatus(Status.DONE);
        taskManager.updateTask(task2);
        taskManager.updateTask(task1);
        taskManager.updateSubtask(subtask3);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask1);
        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getSubTaskById(5));
        System.out.println(taskManager.getSubTaskById(6));
        System.out.println(taskManager.getSubTaskById(7));
        System.out.println(taskManager.getEpicById(3));
        System.out.println(taskManager.getEpicById(4));
        taskManager.removeTaskById(1);
        taskManager.removeEpicById(3);
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubTasks());
    }
}
