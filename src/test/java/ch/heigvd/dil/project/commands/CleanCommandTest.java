package ch.heigvd.dil.project.commands;

import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CleanCommandTest {

    private final static String TEST_FOLDER = "./website";

    @Before
    public void initMockProject () {
        // Here we use another command (init)
        String[] args = new String[] {TEST_FOLDER};
        CommandLine cmd = new CommandLine(new InitCommand());
        cmd.execute(args);
    }

    @Test
    public void shouldCleanBuildFolder() {
        String[] args = new String[] {TEST_FOLDER};
        CommandLine cmd = new CommandLine(new CleanCommand());
        cmd.execute(args);

        assertFalse(new File(TEST_FOLDER + "/build").exists());
    }

    @Test
    public void shouldIgnoreIfNoBuildFolder() {
        String[] args = new String[] {TEST_FOLDER};
        CommandLine cmd = new CommandLine(new CleanCommand());
        cmd.execute(args);

        assertFalse(new File(TEST_FOLDER + "/build").exists());
    }

    @Test
    public void shouldIgnoreIfNotAStatiqueProject() {
        // First we build a project
        String[] args = new String[] {TEST_FOLDER};
        CommandLine cmd = new CommandLine(new BuildCommand());
        cmd.execute(args);

        args = new String[] {TEST_FOLDER};
        cmd = new CommandLine(new CleanCommand());
        cmd.execute(args);

        assertTrue(new File(TEST_FOLDER + "/build").exists());
    }

    @After
    public void clearProject () throws IOException {
        FileUtils.deleteDirectory(new File(TEST_FOLDER));
    }
}