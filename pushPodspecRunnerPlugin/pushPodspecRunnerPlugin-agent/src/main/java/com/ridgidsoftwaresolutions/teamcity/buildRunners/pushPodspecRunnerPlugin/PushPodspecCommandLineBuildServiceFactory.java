package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import org.jetbrains.annotations.NotNull;

public class PushPodspecCommandLineBuildServiceFactory implements CommandLineBuildServiceFactory {

    @NotNull
    @Override
    public CommandLineBuildService createService() {
        return new PushPodspecBuildService();
    }

    @NotNull
    @Override
    public AgentBuildRunnerInfo getBuildRunnerInfo() {
        return new AgentBuildRunnerInfo() {
            @NotNull
            @Override
            public String getType() {
                return PluginConstants.PLUGIN_TYPE;
            }

            @Override
            public boolean canRun(@NotNull BuildAgentConfiguration buildAgentConfiguration) {
                return false;
            }
        };
    }
}
