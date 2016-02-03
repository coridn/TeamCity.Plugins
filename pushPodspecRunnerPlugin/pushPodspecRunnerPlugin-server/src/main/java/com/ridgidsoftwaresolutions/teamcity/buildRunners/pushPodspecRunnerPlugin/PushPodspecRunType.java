package com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin;

import jetbrains.buildServer.requirements.Requirement;
import jetbrains.buildServer.serverSide.InvalidProperty;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.serverSide.RunType;
import jetbrains.buildServer.serverSide.RunTypeRegistry;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PushPodspecRunType extends RunType {
    private final PluginDescriptor pluginDescriptor;

    public PushPodspecRunType(final RunTypeRegistry registry, final PluginDescriptor descriptor) {
        registry.registerRunType(this);
        this.pluginDescriptor = descriptor;
    }

    @NotNull
    @Override
    public String getType() {
        return PluginConstants.PLUGIN_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return PluginConstants.PLUGIN_DISPLAY_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return PluginConstants.PLUGIN_DESCRIPTION;
    }

    @Nullable
    @Override
    public PropertiesProcessor getRunnerPropertiesProcessor() {
        return new PropertiesProcessor() {

            private void checkNotEmpty(Map<String, String> props,
                                       String key,
                                       String message,
                                       Collection<InvalidProperty> res) {
                if(StringUtil.isEmptyOrSpaces(props.get(key))) {
                    res.add(new InvalidProperty(key, message));
                }
            }

            @Override
            public Collection<InvalidProperty> process(Map<String, String> map) {
                Collection<InvalidProperty>result = new ArrayList<InvalidProperty>();
                if(map == null) return result;

                checkNotEmpty(map, PluginConstants.PODSPEC_PATH_KEY, "Path to podspec is required.", result);
                checkNotEmpty(map, PluginConstants.REPO_NAME_KEY, "Repo name is required.", result);

                return result;
            }
        };
    }

    @Nullable
    @Override
    public String getEditRunnerParamsJspFilePath() {
        return this.pluginDescriptor.getPluginResourcesPath("editPushPodspecRunner.jsp");
    }

    @Nullable
    @Override
    public String getViewRunnerParamsJspFilePath() {
        return this.pluginDescriptor.getPluginResourcesPath("viewPushPodspecRunner.jsp");
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultRunnerProperties() {
        final Map<String, String> defaults = new HashMap<String, String>();
        return defaults;
    }

    @NotNull
    @Override
    public List<Requirement> getRunnerSpecificRequirements(@NotNull Map<String, String> runParameters) {
        List<Requirement> requirements = new ArrayList<Requirement>();

        requirements.addAll(super.getRunnerSpecificRequirements(runParameters));
        //Need to eventually check that cocoapods is installed.
        return requirements;
    }

    @NotNull
    @Override
    public String describeParameters(@NotNull Map<String, String> parameters) {
        StringBuilder builder = new StringBuilder();

        builder.append("Podspec: ").append(parameters.get(PluginConstants.PODSPEC_PATH_KEY)).append('\n');
        builder.append("Repo Name: ").append(parameters.get(PluginConstants.REPO_NAME_KEY)).append('\n');

        return builder.toString();
    }
}