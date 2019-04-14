package ru.smartel.school.math.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smartel.school.math.entity.Task;
import ru.smartel.school.math.service.StageService;
import ru.smartel.school.math.service.TestSessionService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class FinalController implements Initializable {
    @FXML VBox vBox;
    @FXML ScrollPane scrollPane;
    @FXML GridPane finalGridPane;
    @FXML Label finalLabel1;
    @FXML Label finalLabel2;

    @Autowired
    StageService stageService;
    @Autowired
    TestSessionService testSessionService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Pair<Task, Double>> taskAnswers = testSessionService.getTaskAnswers();

        int[] index = {1};
        int[] rightCount = {0};
        taskAnswers.forEach(taskAnswer -> {
            Double solution = taskAnswer.getKey().getSolution();
            Double answer = taskAnswer.getValue();
            boolean right = solution.equals(answer);
            addVBoxElement(
                    index[0] + "",
                    solution+ "",
                    answer == null ? "-" : answer+ "",
                    right);
            if (right) rightCount[0]++;
            index[0]++;
        });

        finalLabel2.setText("правильно решено " + rightCount[0] + " из " + taskAnswers.size());

    }

    private void addVBoxElement(String first, String second, String third, boolean right){
        try {
            HBox finalTaskElement = new FXMLLoader(getClass().getResource("/fxml/finalTaskElement.fxml")).load();

            finalTaskElement.getStyleClass().add(right ? "right" : "wrong");

            ((Label) finalTaskElement.getChildren().get(0)).setText(first);
            ((Label) finalTaskElement.getChildren().get(1)).setText(second);
            ((Label) finalTaskElement.getChildren().get(2)).setText(third);

            finalTaskElement.setOnMouseClicked(event -> {
                showModal(Integer.parseInt(((Label) finalTaskElement.getChildren().get(0)).getText()) - 1);
            });
            vBox.getChildren().add(finalTaskElement);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showIntroScene() throws IOException {
        stageService.changeScene("/fxml/intro.fxml");
    }

    public void showModal(int taskIndex) {
        Stage modalStage = new Stage();
        modalStage.setResizable(false);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/modal.fxml"));
        try {
            Pane pane = fxmlLoader.load();
            modalStage.setScene(new Scene(pane));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModalController controller = fxmlLoader.getController();
        controller.setUp(testSessionService.getTaskAnswers().get(taskIndex));
        modalStage.show();
    }

    public void setUp(boolean byTimer) {
        if (byTimer) {
            finalLabel1.setText("время вышло");
        } else {
            finalLabel1.setText("все ответы даны");
        }
    }
}
