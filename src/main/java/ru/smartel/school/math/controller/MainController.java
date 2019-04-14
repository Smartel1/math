package ru.smartel.school.math.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartel.school.math.service.StageService;
import ru.smartel.school.math.entity.Task;
import ru.smartel.school.math.service.TestSessionService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {
    @FXML VBox vbox;
    @FXML Button mainCommitButton;
    @FXML ImageView imageView;
    @FXML Label descriptionLabel;
    @FXML Label timerLabel;
    @FXML TextField answerField;
    private Timeline timeline;

    @Autowired
    StageService stageService;
    @Autowired
    TestSessionService testSessionService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        descriptionLabel.setMaxWidth(Double.MAX_VALUE);
        //числовое поле
        answerField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                answerField.setText(oldValue);
            }
        });
    }

    public void answer(ActionEvent event){
        testSessionService.answer(Double.parseDouble(answerField.getText()));
        testSessionService.toNextTask();
        showCurrentTask();
    }

    public void showCurrentTask(){
        answerField.setText("0");
        Task currentTask = testSessionService.getCurrentTask();

        if (currentTask == null) {
            timeline.stop();
            showFinalScene(false);
            return;
        }

        descriptionLabel.setText(currentTask.getDescription());
        imageView.setImage(new Image(currentTask.getImageUrl()));
    }

    public void showFinalScene(boolean byTimer){
        try {
            FinalController controller = (FinalController) stageService.changeScene("/fxml/final.fxml");
            controller.setUp(byTimer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUp(int tasksCount, float minutesCount) {
        testSessionService.initTaskSession(tasksCount);
        showCurrentTask();
        int timeLimit = Math.round(minutesCount * 60);
        //Оборачиваем количество секунд в массив, чтобы был доступ из лямбды
        int[] time = {timeLimit};

        timeline = new Timeline (
                new KeyFrame(
                        Duration.seconds(1),
                        ae -> {
                            time[0]--;
                            timerLabel.setText(
                                    LocalTime.parse("00:00")
                                        .plusSeconds(time[0])
                                        .format(DateTimeFormatter.ISO_LOCAL_TIME)
                            );

                            if (time[0] == 0) {
                                showFinalScene(true);
                                timeline.stop();
                            }
                        }
                )
        );
        timeline.setCycleCount(-1);
        timeline.play();
    }
}
