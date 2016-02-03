package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by coridn on 2/3/16.
 */
public class PushPodspecBuildService extends BuildServiceAdapter {

    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        return createProgramCommandline("pod", new ArrayList<String>());
    }
}
