package com.example;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.example.util.Constants.*;
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;

class DependencyCollectionPluginTest {

    @Test
    void apply() throws IOException {

        final File file = new File("src/test/resources/data/simpleTest");

        final BuildResult buildResult = GradleRunner.create()
                .withProjectDir(file)
                .withArguments(TASK_NAME)
                .build();

        Assertions.assertFalse(buildResult.getTasks().isEmpty());
        Assertions.assertEquals(buildResult.task(DEPENDENCY_COLLECTION_TASK).getOutcome(), SUCCESS);
    }
}