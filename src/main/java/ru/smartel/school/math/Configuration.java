package ru.smartel.school.math;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import ru.smartel.school.math.entity.Task;
import ru.smartel.school.math.entity.TestSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public List<Task> tasks() throws IOException {
        ApplicationHome home = new ApplicationHome(DemoApplication.class);
        String jarPath = home.getDir().getAbsolutePath();

        byte[] jsonData = Files.readAllBytes(Paths.get(jarPath+"/tasks/entry.json"));

        List<Task> tasks = Arrays.asList(new ObjectMapper().readValue(jsonData, Task[].class));

        for (Task task: tasks) {
            task.setImageUrl("file:" + jarPath + "/tasks/images/" + task.getImageUrl());
        }

        return tasks;
    }

    @Bean
    public TestSession testSession(){
        return new TestSession();
    }
}
