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

/**
 * @author Lisa
 *
 */
@WebServlet("/Mail")
public class Mail extends SessionCheck  {
	
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * reads request content and sends it to {@ link EmailTelnet#send_mail}.
	 * <p>
	 * 
	 * @see EmailTelnet
	 * @see EmailTelnet#send_mail
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		//read request
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
		// TODO Auto-generated method stub
	}

}
