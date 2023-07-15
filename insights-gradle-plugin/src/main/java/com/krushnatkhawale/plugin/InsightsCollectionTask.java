package com.krushnatkhawale.plugin;

import com.krushnatkhawale.model.Dependency;
import com.krushnatkhawale.model.Info;
import com.krushnatkhawale.util.Utils;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class InsightsCollectionTask extends DefaultTask {

    final Logger logger = Logging.getLogger(InsightsCollectionTask.class);

    private Project project = getProject();

    @Input
    String targetUrl;

    @Input
    String appName;

    @Input
    boolean disablePost;

    public String getTargetUrl() {
        return targetUrl;
    }

    public String getAppName() {
        return appName;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    @TaskAction
    void taskAction() {

        Info projectInfo = getProjectInfo();

        projectInfo.print();

        post(projectInfo);
    }

    private void post(Info projectInfo) {

        if (disablePost) {
            logger.quiet("       Dependency posting disabled!");
        } else {
            Utils.post(projectInfo);
        }
    }

    private Info getProjectInfo() {

        String group = project.getGroup().toString();
        String appName = ofNullable(this.appName).orElse(project.getName());
        List<Dependency> collect = getDependencies(project);

        String host = ofNullable("http://"+targetUrl).orElse("http://localhost:8080/path");

        Info info = new Info();
        info.setGroupName(group);
        info.setAppName(appName);
        info.setDependencies(collect);
        info.setHost(host);
        return info;
    }

    private List<Dependency> getDependencies(Project project) {
        return project.getConfigurations()
                .stream()
                .filter(config -> config.getName().contains("compile"))
                .map(Configuration::resolve)
                .flatMap(Collection::stream)
                .map(Utils::toDependency)
                .sorted((Comparator.comparing(Dependency::getGroup)))
                .collect(Collectors.toList());
    }
}