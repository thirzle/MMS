
<%
	String firstname = (String) session.getAttribute("newFirstname");
	String lastname = (String) session.getAttribute("newLastname");
	String loginname = (String) session.getAttribute("newLoginname");
%>
<h1>Name &auml;ndern</h1>
<h2>Ihr Name wurde erfolgreich zu<%out.print(" "+firstname+" "+lastname+" ");%>ge&auml;ndert.
Ihr neuer Loginname ist:<%out.print(" "+loginname); %></h2>
<%
	session.removeAttribute("newFristname");
	session.removeAttribute("newLastname");
	session.removeAttribute("newLoginname");
%>