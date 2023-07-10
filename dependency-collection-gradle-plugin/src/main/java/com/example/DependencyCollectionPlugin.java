package com.example;

import com.example.plugin.DefaultAction;
import com.example.plugin.DependencyCollectionTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

import static com.example.util.Constants.BUILD_TASK;
import static com.example.util.Constants.TASK_NAME;

public class DependencyCollectionPlugin implements Plugin<Project> {


    private DefaultAction defaultAction = new DefaultAction();

    @Override
    public void apply(Project project) {

        Task buildTask = project.getTasks()
                .findByName(BUILD_TASK);

        DependencyCollectionTask thisTask = project.getTasks()
                .create(TASK_NAME, DependencyCollectionTask.class, defaultAction);

        buildTask.dependsOn(thisTask);

    }
}