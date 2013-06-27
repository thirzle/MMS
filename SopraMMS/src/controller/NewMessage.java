//package controller;
//
//import java.io.IOException;
//import javax.servlet.Servlet;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * Servlet implementation class NewMessage
// */
//@WebServlet("/NewMessage")
//public class NewMessage extends SessionCheck implements Servlet {
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * @see SessionCheck#SessionCheck()
//     */
//    public NewMessage() {
//	super();
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		HttpSession session = request.getSession();
//		if (isLoggedIn(request, response)) {
//			String infotext = "Ihre Nachricht wurde erfolgreich verschickt.";
//		    session.setAttribute("content", "home");
//		    response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&infotext="+infotext);
//		} else {
//			String error = "Ihre Session ist abgelaufen, bitte loggen Sie sich erneut ein.";
//			response.sendRedirect("/SopraMMS/guiElements/home.jsp?home=true&errortext="+error);
//		}
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    }
//
//}
