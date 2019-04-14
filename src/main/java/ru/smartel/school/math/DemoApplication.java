package ru.smartel.school.math;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.smartel.school.math.controller.IntroController;
import ru.smartel.school.math.service.StageService;

@SpringBootApplication
public class DemoApplication extends Application {

	private ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() throws Exception {
		springContext = SpringApplication.run(DemoApplication.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Math-v0.0.1");
		StageService stageService = springContext.getBean(StageService.class);
		stageService.setStage(primaryStage);
		IntroController controller = (IntroController) stageService.changeScene("/fxml/intro.fxml");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void initialize(){
	}

	@Override
	public void stop() {
		springContext.stop();
	}
}
