package com.krushnatkhawale.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class InsightsPlugin implements Plugin<Project> {

    public static final String TASK_NAME = "projectInsights";
    public static final String BUILD_TASK = "build";
    @Override
    public void apply(Project project) {

        Task buildTask = project.getTasks()
                .findByName(BUILD_TASK);

        InsightsCollectionTask insightsTask = project.getTasks()
                .register(TASK_NAME, InsightsCollectionTask.class).get();

        buildTask.finalizedBy(insightsTask);
    }
}