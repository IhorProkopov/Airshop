package com.epam.prokopov.shop.controller.captcha;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CaptchaCreator cc;
	private CaptchaSaver cs;
	
	@Override
	public void init() throws ServletException {
		cc = new CaptchaCreator();
		ServletContext sc = getServletContext();
		cs = (CaptchaSaver) sc.getAttribute("captchaClass");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Random rand = new Random();
		Integer text = rand.nextInt(89999) + 10000;
		Integer id = Integer.parseInt(request.getParameter("id"));
		cs.saveCaptcha(request, response, id.toString(), text.toString());
		cc.createCaptcha(text.toString(), "jpg", request, response);
	}

}
