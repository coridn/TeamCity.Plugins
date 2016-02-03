<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="bean" class="com.ridgidsoftwaresolutions.teamcity.buildRunners.pushPodspecRunnerPlugin.PushPodspecBean"/>

<div class="parameter">
    Podspec Path: <strong><props:displayValue name="${bean.podspecPathKey}"/></strong>
</div>
<div class="parameter">
    Repo Name: <strong><props:displayValue name="${bean.repoNameKey}"/></strong>
</div>