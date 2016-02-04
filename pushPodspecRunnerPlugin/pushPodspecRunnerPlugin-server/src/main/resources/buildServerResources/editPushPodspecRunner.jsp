<%@ include file="/include.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="bean" class="com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin.PushPodspecBean"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<l:settingsGroup title="Basic Settings">
    <tr>
        <th>Podspec Path:<l:star/></th>
        <td>
            <props:textProperty name="${bean.podspecPathKey}" className="longField" />
            <bs:vcsTree fieldId="${bean.podspecPathKey}"/>
            <span class="error" id="error_${bean.podspecPathKey}"></span>
            <span class="smallNote">Path to the podspec, relative to the checkout directory.</span>
        </td>
    </tr>
    <tr>
        <th>Podspec Repo Name:<l:star/></th>
        <td>
            <props:textProperty name="${bean.repoNameKey}" className="longField" />
            <span class="error" id="error_${bean.repoNameKey}"></span>
            <span class="smallNote">The Cocoapods repository name.</span>
        </td>
    </tr>
</l:settingsGroup>
<l:settingsGroup title="Additional Options" className="advancedSettings">
    <tr class="advancedSetting">
        <th>Sources:</th>
        <td>
            <props:multilineProperty name="${bean.sourcesKey}" linkTitle="Sources" cols="60" rows="5" expanded="true"/>
            <span class="smallNote">
                Leave blank to use all CocoaPod repo's registered on the agent.<br/>
                Place each source on its own line.
            </span>
        </td>
    </tr>
    <tr class="advancedSetting">
        <th>Use Libraries:</th>
        <td>
            <props:checkboxProperty name="${bean.useLibrariesKey}"/>
            <label for="${bean.useLibrariesKey}">Use static libraries while linting podspec.</label>
        </td>
    </tr>
    <tr class="advancedSetting">
        <th>Allow Warnings:</th>
        <td>
            <props:checkboxProperty name="${bean.allowWarningsKey}"/>
            <label for="${bean.allowWarningsKey}">Allow push with warnings.</label>
            <span class="smallNote">Useful on private repos.</span>
        </td>
    </tr>
    <tr class="advancedSetting">
        <th>Additional Command Line Arguments:</th>
        <td>
            <props:multilineProperty name="${bean.additionalCommandLineArgumentsKey}" linkTitle="Additional Command Line Arguments" cols="60" rows="5" expanded="true"/>
        </td>
    </tr>
</l:settingsGroup>