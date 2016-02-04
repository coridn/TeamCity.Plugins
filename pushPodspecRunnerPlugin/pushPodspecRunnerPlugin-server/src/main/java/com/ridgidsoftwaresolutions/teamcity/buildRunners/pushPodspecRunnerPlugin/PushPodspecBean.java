package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

/**
 * Created by coridn on 2/3/16.
 */
public class PushPodspecBean {

    public String getPodspecPathKey() {
        return PluginConstants.PODSPEC_PATH_KEY;
    }

    public String getRepoNameKey() {
        return PluginConstants.REPO_NAME_KEY;
    }

    public String getAllowWarningsKey() {
        return PluginConstants.ALLOW_WARNINGS_KEY;
    }

    public String getSourcesKey() {
        return PluginConstants.SOURCES_KEY;
    }

    public String getUseLibrariesKey() {
        return PluginConstants.USE_LIBRARIES_KEY;
    }

    public String getAdditionalCommandLineArgumentsKey() {
        return PluginConstants.ADDITIONAL_COMMAND_LINE_ARGS_KEY;
    }
}
