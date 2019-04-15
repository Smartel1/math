package ru.smartel.school.math.service;

import javafx.util.Pair;
import org.springframework.stereotype.Service;
import ru.smartel.school.math.entity.Task;
import ru.smartel.school.math.entity.TaskSession;

import java.util.*;

@Service
public class TestSessionService {
    private TaskSession taskSession;

    private List<Task> tasks;
    private Map<Task, Double> answers;

    public TestSessionService(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void initTaskSession(int tasksCount){
        taskSession = new TaskSession();

        //Выбираем taskCount случайных заданий и включаем их в сессию, добавляя ответ null
        do {
            int randomIndex = new Random().nextInt(tasksCount);
            taskSession.addTask(tasks.get(randomIndex));
        } while (taskSession.getTaskAnswers().size() < tasksCount);

        taskSession.setCurrentTaskIndex(0);
    }

    public int getTotalTasksCount(){
        return tasks.size();
    }

    public void answer(Double answer) {
        taskSession.answer(answer);
    }

    public void toNextTask() {
        taskSession.setCurrentTaskIndex(taskSession.getCurrentTaskIndex() + 1);
    }

    public Task getCurrentTask(){
        int currentTask = taskSession.getCurrentTaskIndex();
        if (taskSession.getTasksCount() == currentTask) return null;
        return taskSession.getTaskAnswers().get(currentTask).getKey();
    }

    public List<Pair<Task, Double>> getTaskAnswers() {
        return taskSession.getTaskAnswers();
    }
}
