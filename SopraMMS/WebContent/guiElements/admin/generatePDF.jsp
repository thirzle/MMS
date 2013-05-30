<%@page import="user.UserAdministration,java.util.List"%>
<%
UserAdministration ua = new UserAdministration();
List<String> facList = ua.getAllFacultiesByName();
%>

<h1>Neues Modulhandbuch erstellen</h1>
<p>W&auml;hlen Sie eine Fakult&auml;t aus:</p>
<select form="newPDFForm" name="facSelect">

</select>