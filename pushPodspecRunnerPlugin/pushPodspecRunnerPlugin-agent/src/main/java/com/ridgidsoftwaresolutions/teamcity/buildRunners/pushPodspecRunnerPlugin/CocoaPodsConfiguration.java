package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.agent.BuildAgentConfiguration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

/**
 * Created by coridn on 2/5/16.
 */
public class CocoaPodsConfiguration {

    String executablePath;

    String podspecPath;
    String repositoryName;

    String[] sources;
    boolean useLibraries;
    boolean allowWarnings;
    String additionalCommandLineArguments;

    private final String nonElCapitanPodPath = "/usr/bin/pod";
    private final String elCapitanPodPath = "/usr/local/bin/pod";
    private final String rvmPath = "~/.rvm/";

    CocoaPodsConfiguration(BuildAgentConfiguration buildAgentConfiguration,
                                  AgentRunningBuild agentRunningBuild,
                                  Map<String, String> runningParameters) {
        //Find Cocoapods
        this.executablePath = findCocoaPodsExecutable(agentRunningBuild);
    }

    private String findCocoaPodsExecutable(AgentRunningBuild agentRunningBuild) {
        //Check the known locations.
        if(new File(nonElCapitanPodPath).exists()){
            return nonElCapitanPodPath;
        }
        if(new File(elCapitanPodPath).exists()) {
            return elCapitanPodPath;
        }
        //Check if there is RVM's to check.
        File rvmDir = new File(rvmPath);
        if(rvmDir.exists() && rvmDir.isDirectory()) {
            try {
                Files.walkFileTree(Paths.get(rvmPath), new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        return super.visitFile(file, attrs);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    boolean canRun() {
        return false;
    }
}
