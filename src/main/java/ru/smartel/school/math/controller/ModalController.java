package ru.smartel.school.math.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartel.school.math.entity.Task;
import ru.smartel.school.math.service.StageService;
import ru.smartel.school.math.service.TestSessionService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ModalController implements Initializable {
    @FXML ImageView imageView;
    @FXML Label requiredLabel;
    @FXML Label factLabel;

    @Autowired
    StageService stageService;
    @Autowired
    TestSessionService testSessionService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void close(ActionEvent actionEvent){
        ((Stage)((Button)actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void setUp(Pair<Task, Double> taskAnswer) {
        imageView.setImage(new Image(taskAnswer.getKey().getImageUrl()));
        requiredLabel.setText("Правильный ответ: " + taskAnswer.getKey().getSolution());
        if (taskAnswer.getValue() != null){
            factLabel.setText("Дан ответ: " + taskAnswer.getValue());
        }
        else {
             factLabel.setText("Ответ не дан");
        }
    }
}
