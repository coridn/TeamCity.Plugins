package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

import jetbrains.buildServer.agent.AgentBuildRunnerInfo;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.agent.runner.CommandLineBuildService;
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory;
import jetbrains.buildServer.log.Loggers;
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
                //CocoaPods is a mac thing.
                if(!buildAgentConfiguration.getSystemInfo().isMac()) {
                    Loggers.SERVER.debug(getType() + " runner is supported only under Mac platform.");
                    return false;
                }

                //Check if CocoaPods is installed.
                //We have to check two places depending on system version.
                //El Capitan has CocoaPods in /usr/local/bin if installed with sudo.
                //Prior to El Capitan, it was in /usr/bin
                //If they're using RVM or something else, then it's in one of those folders.

                


                return true;
            }
        };
    }
}
