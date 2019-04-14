package ru.smartel.school.math.entity;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TestSession {

    private List<Pair<Task, Double>> taskAnswers = new ArrayList<Pair<Task, Double>>();
    private Integer currentTaskIndex = 0;

    public int getTasksCount() {
        if (taskAnswers == null) return 0;
        return taskAnswers.size();
    }

    public List<Pair<Task, Double>> getTaskAnswers() {
        return taskAnswers;
    }

    public void setTaskAnswers(List<Pair<Task, Double>> taskAnswers) {
        this.taskAnswers = taskAnswers;
    }

    public void addTask(Task task) {
        this.taskAnswers.add(new Pair<>(task, null));
    }

    public void answer(Double answer) {
        Pair<Task, Double> taskAnswer = taskAnswers.get(currentTaskIndex);
        taskAnswers.set(currentTaskIndex, new Pair<>(taskAnswer.getKey(), answer));
    }

    public Integer getCurrentTaskIndex() {
        return currentTaskIndex;
    }

    public void setCurrentTaskIndex(Integer currentTaskIndex) {
        this.currentTaskIndex = currentTaskIndex;
    }
}
