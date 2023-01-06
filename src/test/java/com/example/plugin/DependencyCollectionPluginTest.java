package com.example.plugin;

import org.gradle.internal.impldep.org.junit.Rule;
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class DependencyCollectionPluginTest {
    @Test
    void apply() throws IOException {

        final File file = new File("src/test/resources/data/simpleTest");

        final BuildResult buildResult = GradleRunner.create()
                .withProjectDir(file)
                .withArguments("buildEnvironment")
                .build();

        System.out.println("Hello: " + buildResult.task(":buildEnvironment").getOutcome());
    }
}