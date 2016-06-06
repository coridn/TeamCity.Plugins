package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

import jetbrains.buildServer.RunBuildException;
import jetbrains.buildServer.agent.runner.BuildServiceAdapter;
import jetbrains.buildServer.agent.runner.ProgramCommandLine;
import jetbrains.buildServer.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by coridn on 2/3/16.
 */
public class PushPodspecBuildService extends BuildServiceAdapter {

    @NotNull
    @Override
    public ProgramCommandLine makeProgramCommandLine() throws RunBuildException {
        Map<String, String> parameters = getRunnerParameters();
        String repoName = parameters.get(PluginConstants.REPO_NAME_KEY);
        String podspecPath = parameters.get(PluginConstants.PODSPEC_PATH_KEY);
        List<String> arguments = new ArrayList<String>();
        arguments.add("repo");
        arguments.add("push");
        arguments.add(repoName);
        arguments.add(podspecPath);
        String sources = parameters.get(PluginConstants.SOURCES_KEY);
        if(!StringUtil.isEmptyOrSpaces(sources)) {
            //Split them on new line, and trim, then join.
            String[] splitSources = sources.replaceAll(" ", "").split("\n");
            arguments.add("source=" + String.join(",", splitSources));
        }
        String useLibraries = parameters.get(PluginConstants.USE_LIBRARIES_KEY);
        if(StringUtil.isTrue(useLibraries)) {
            arguments.add("--use-libraries");
        }
        String allowWarnings = parameters.get(PluginConstants.ALLOW_WARNINGS_KEY);
        if(StringUtil.isTrue(allowWarnings)) {
            arguments.add("--allow-warnings");
        }
        String additionalArgs = parameters.get(PluginConstants.ADDITIONAL_COMMAND_LINE_ARGS_KEY);
        if(!StringUtil.isEmptyOrSpaces(additionalArgs)) {
            arguments.add(additionalArgs);
        }

        return createProgramCommandline("/Users/coridn/.rvm/gems/ruby-2.0.0-p643/bin/pod", arguments);
    }
}
