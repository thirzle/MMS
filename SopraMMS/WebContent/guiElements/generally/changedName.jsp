
<%
	String firstname = (String) session.getAttribute("newFirstname");
	String lastname = (String) session.getAttribute("newLastname");
%>
<h1>Name &auml;ndern</h1>
<h2>Ihr Name wurde erfolgreich zu<%out.print(" "+firstname+" "+lastname+" ");%>ge&auml;ndert</h2>
<%
	session.removeAttribute("newFristname");
	session.removeAttribute("newLastname");
%>