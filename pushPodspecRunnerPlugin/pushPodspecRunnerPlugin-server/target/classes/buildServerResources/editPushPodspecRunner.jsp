<%@ include file="/include.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="bean" class="com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin.PushPodspecBean"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<l:settingsGroup title="Awesome">
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
            <props:textProperty name="${bean.repoNameKey}" />
            <span class="error" id="error_${bean.repoNameKey}"></span>
            <span class="smallNote">The Cocoapods repository name.</span>
        </td>
    </tr>
</l:settingsGroup>