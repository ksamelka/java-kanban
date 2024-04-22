import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private int id = 1;


    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();


    public void addTask(Task task) { //добавление task в хэш-таблицу
        task.setId(id);
        tasks.put(id, task);
        id++;
    }

    public List<Task> getAllTasks() { //получение списка task
        return new ArrayList<>(tasks.values());
    }

    public void removeAllTasks() { //удаление всех task из хэш-таблицы
        tasks.clear();
    }

    public Task getTaskById(int taskId) { //получение task по идентификатору
        return tasks.get(taskId);
    }

    public void removeTaskById(int taskId) { //удаление task по идентификатору
        tasks.remove(taskId);
    }

    public void updateTask(Task task) { //обновление task
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void addSubTask(Subtask subtask) { //добавление subtask в хэш-таблицу
        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setId(id);
            subtasks.put(id, subtask);
            id++;
            Epic epic = epics.get(subtask.getEpicId());
            epic.addSubTask(subtask.getId());
            updateStatus(epic.getId());
        }

    }

    public List<Subtask> getAllSubTasks() { //получение списка subtask
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllSubTasks() { //удаление всех subtask из хэш-таблицы
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubTask();
            updateStatus(epic.getId());
        }
    }

    public Subtask getSubTaskById(int subTaskId) { //получение subtask по идентификатору
        return subtasks.get(subTaskId);
    }

    public void removeSubTaskById(int subTaskId) { //удаление subtask по идентификатору
        Subtask subtask = subtasks.remove(subTaskId);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubTask(subTaskId);
            updateStatus(epic.getId());
        }
    }

    public void updateSubtask(Subtask subtask) { //обновление subtask
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            updateStatus(epic.getId());
        }
    }

    public void addEpic(Epic epic) { //добавление epic в хэш-таблицу
        epic.setId(id);
        epics.put(id, epic);
        id++;
    }

    public List<Task> getAllEpics() { //получение списка epic
        return new ArrayList<>(epics.values());
    }

    public void removeAllEpics() { //удаление всех epic из хэш-таблицы
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpicById(int epicId) { //получение epic по идентификатору
        return epics.get(epicId);
    }

    public void removeEpicById(int epicId) { //удаление epic по идентификатору
        Epic epic = epics.remove(epicId);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
        }

    }

    public void updateEpic(Epic updateEpic) { //обновление epic
        if (epics.containsKey(updateEpic.getId())) {
            Epic epic = epics.get(updateEpic.getId());
            epic.setName(updateEpic.getName());
            epic.setDescription(updateEpic.getDescription());
            epics.put(updateEpic.getId(), epic);
        }
    }

    public ArrayList<Subtask> getAllSubtaskOfEpic(int epicId) { //Получение списка всех подзадач определённого эпика
        ArrayList<Subtask> value = new ArrayList<>();
        for (Integer subtaskId : epics.get(epicId).getSubtaskIds()) {
            value.add(subtasks.get(subtaskId));
        }
        return value;
    }

    private void updateStatus(int epicId) {
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



