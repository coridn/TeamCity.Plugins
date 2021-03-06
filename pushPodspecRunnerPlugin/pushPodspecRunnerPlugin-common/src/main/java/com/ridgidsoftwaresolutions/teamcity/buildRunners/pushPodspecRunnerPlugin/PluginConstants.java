package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

public interface PluginConstants {
    String PLUGIN_TYPE = "pushPodspecRunner";
    String PLUGIN_DISPLAY_NAME = "Push Podspec";
    String PLUGIN_DESCRIPTION = "Pushes a podspec to a cocoapods repo.";

    String PODSPEC_PATH_KEY = "pushPodspecPath";
    String REPO_NAME_KEY = "repoName";
    String ALLOW_WARNINGS_KEY = "allowWarnings";
    String SOURCES_KEY = "sources";
    String USE_LIBRARIES_KEY = "useLibraries";
    String ADDITIONAL_COMMAND_LINE_ARGS_KEY = "additionalCommandLineArgs";
}
