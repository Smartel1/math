package ru.smartel.school.math.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartel.school.math.service.StageService;
import ru.smartel.school.math.service.TestSessionService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IntroController implements Initializable {
    @FXML Label tasksErrorLabel;
    @FXML TextField tasksTextField;
    @FXML TextField minutesTextField;
    @FXML GridPane introGridPane;
    @FXML Button introStartButton;

    @Autowired
    StageService stageService;
    @Autowired
    TestSessionService testSessionService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //целочисленное поле
        tasksTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("([1-9][0-9]*)?")) {
                tasksTextField.setText(oldValue);
            }
        });
        //числовое поле
        minutesTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                minutesTextField.setText(oldValue);
            }
        });
    }

    public void showMainScene() throws IOException {

        int maxTasksCount = testSessionService.getTotalTasksCount();

        if (Integer.parseInt(tasksTextField.getText()) > maxTasksCount) {
            tasksErrorLabel.setText("не более " + maxTasksCount);
            return;
        }

        MainController controller = (MainController) stageService.changeScene("/fxml/main.fxml");
        controller.setUp(Integer.parseInt(tasksTextField.getText()), Float.parseFloat(minutesTextField.getText()));
    }
}
