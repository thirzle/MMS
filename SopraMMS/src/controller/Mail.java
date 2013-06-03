package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mail.EmailTelnet;

import user.User;

@WebServlet("/Mail")
public class Mail extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		String mail_address_from = user.getMail();
		String mail_from = (user.getFirstName()+" "+user.getLastName());
		String mail_address_to = req.getParameter("mailto");
		String subject = req.getParameter("subject");
		String content = req.getParameter("message");
		EmailTelnet.send_mail(mail_address_from, mail_from, subject, mail_address_to, content);
		session.setAttribute("content", "home");
		resp.sendRedirect("/SopraMMS/guiElements/home.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("user");
		String mail_address_from = user.getMail();
		String mail_from = (user.getFirstName()+" "+user.getLastName());
		String mail_address_to = req.getParameter("mailto");
		String subject = req.getParameter("subject");
		String content = req.getParameter("message");
		EmailTelnet.send_mail(mail_address_from, mail_from, subject, mail_address_to, content);
	}

}
