package ru.smartel.school.math.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StageService {

    @Autowired
    private ApplicationContext springContext;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Object changeScene(String scenePath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(scenePath));
        fxmlLoader.setControllerFactory(springContext::getBean);
        Pane pane = fxmlLoader.load();
        stage.setScene(new Scene(pane));
        return fxmlLoader.getController();
    }
}
