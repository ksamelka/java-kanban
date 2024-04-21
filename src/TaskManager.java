import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private int taskId = 0;
    private int subtaskId = 0;
    private int epicId = 0;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();


    public void addTask(Task task) { //добавление task в хэш-таблицу
        task.setId(taskId);
        tasks.put(taskId, task);
        taskId++;
    }

    public List<Task> getAllTasks() { //получение списка task
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() { //удаление всех task из хэш-таблицы
        tasks.clear();
    }

    public Task getTaskById(int id) { //получение task по идентификатору
        return tasks.get(id);
    }

    public void removeTaskById(int id) { //удаление task по идентификатору
        tasks.remove(id);
    }

    public void updateTask(Task task) { //обновление task
        tasks.put(task.getId(), task);
    }

    public void addSubTask(Subtask subtask) { //добавление subtask в хэш-таблицу
        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setId(subtaskId);
            subtasks.put(subtaskId, subtask);
            subtaskId++;
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtaskId().add(subtask.getId());
            status(epic.getId());
        }

    }

    public List<Subtask> getAllSubTasks() { //получение списка subtask
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllSubTasks() { //удаление всех subtask из хэш-таблицы
        subtasks.clear();
        for (Epic epic : epics.values()) {
            status(epic.getId());
        }
    }

    public Subtask getSubTaskById(int id) { //получение subtask по идентификатору
        return subtasks.get(id);
    }

    public void removeSubTaskById(int id) { //удаление subtask по идентификатору
        Subtask subtask = getSubTaskById(id);
        subtasks.remove(id);
        Epic epic = epics.get(subtask.getEpicId());
        status(epic.getId());
    }

    public void updateSubtask(Subtask subtask) { //обновление subtask
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        status(epic.getId());
    }

    public void addEpic(Epic epic) { //добавление epic в хэш-таблицу
        epic.setId(epicId);
        epics.put(epicId, epic);
        epicId++;
    }

    public List<Task> getAllEpics() { //получение списка epic
        return new ArrayList<>(epics.values());
    }

    public void removeAllEpics() { //удаление всех epic из хэш-таблицы
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpicById(int id) { //получение epic по идентификатору
        return epics.get(id);
    }

    public void removeEpicById(int id) { //удаление epic по идентификатору
        for (Integer subtaskId : epics.get(id).getSubtaskId()) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    public void updateEpic(Epic epic) { //обновление epic
        epics.put(epic.getId(), epic);
    }

    public ArrayList<Subtask> getAllSubtaskOfEpic(int id) { //Получение списка всех подзадач определённого эпика
        ArrayList<Subtask> value = new ArrayList<>();
        for (Integer subtaskId : epics.get(id).getSubtaskId()) {
            value.add(subtasks.get(subtaskId));
        }
        return value;
    }

    public void status(int epicId) {
        ArrayList<Subtask> value = getAllSubtaskOfEpic(epicId);
        Epic epic = epics.get(epicId);
        if (value.isEmpty()) {
            epic.setStatus(Status.NEW);
            epics.put(epicId, epic);
            return;
        }
        int countNew = 0;
        int countDone = 0;
        Subtask subtask;
        for (int i = 0; i < value.size(); i++) {
            subtask = value.get(i);
            if (subtask.getStatus().equals(Status.NEW)) {
                countNew++;
            } else if (subtask.getStatus().equals(Status.DONE)) {
                countDone++;
            }
        }
        if (countNew == value.size()) {
            epic.setStatus(Status.NEW);
            epics.put(epicId, epic);
        } else if (countDone == value.size()) {
            epic.setStatus(Status.DONE);
            epics.put(epicId, epic);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
            epics.put(epicId, epic);
        }
    }
}



