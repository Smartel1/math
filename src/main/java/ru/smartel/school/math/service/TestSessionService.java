package ru.smartel.school.math.service;

import javafx.util.Pair;
import org.springframework.stereotype.Service;
import ru.smartel.school.math.entity.Task;
import ru.smartel.school.math.entity.TestSession;

import java.util.*;

@Service
public class TestSessionService {
    private TestSession testSession;

    private List<Task> tasks;
    private Map<Task, Double> answers;

    public TestSessionService(TestSession testSession, List<Task> tasks) {
        this.testSession = testSession;
        this.tasks = tasks;
    }

    public void initTaskSession(int tasksCount){
        //todo найти другой способ проверки на уникальность задания
        Set<Task> tasksToUse = new HashSet<>(tasksCount);

        //Выбираем taskCount случайных заданий и включаем их в сессию, добавляя ответ null
        do {
            int randomIndex = new Random().nextInt(tasksCount);
            if (tasksToUse.add(tasks.get(randomIndex))){
                testSession.addTask(tasks.get(randomIndex));
            }
        } while (testSession.getTaskAnswers().size() < tasksCount);

        testSession.setCurrentTaskIndex(0);
    }

    public int getTotalTasksCount(){
        return tasks.size();
    }

    public void answer(Double answer) {
        testSession.answer(answer);
    }

    public void toNextTask() {
        testSession.setCurrentTaskIndex(testSession.getCurrentTaskIndex() + 1);
    }

    public Task getCurrentTask(){
        int currentTask = testSession.getCurrentTaskIndex();
        if (testSession.getTasksCount() == currentTask) return null;
        return testSession.getTaskAnswers().get(currentTask).getKey();
    }

    public List<Pair<Task, Double>> getTaskAnswers() {
        return testSession.getTaskAnswers();
    }
}
