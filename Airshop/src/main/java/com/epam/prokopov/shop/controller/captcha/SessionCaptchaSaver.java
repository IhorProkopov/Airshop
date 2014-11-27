package com.epam.prokopov.shop.controller.captcha;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.prokopov.shop.model.Captcha;

public class SessionCaptchaSaver extends CaptchaSaver {

	public SessionCaptchaSaver(int lifeTime) {
		super(lifeTime);
	}

	@Override
	public void saveCaptcha(HttpServletRequest request,
			HttpServletResponse response, String id, String text) {
		HttpSession session = request.getSession();
		Captcha cap = new Captcha(text, id, Calendar.getInstance()
				.getTimeInMillis() + lifeTime);
		session.setAttribute("captcha", cap);
		request.setAttribute("hidden_field", false);
	}

	@Override
	public boolean isCorrect(HttpServletRequest request, String userText,
			String id) {
		HttpSession session = request.getSession();
		Captcha realCaptcha = (Captcha) session.getAttribute("captcha");
		if (realCaptcha == null) {
			return false;
		}
		boolean res = realCaptcha.getText().equals(userText)
				&& realCaptcha.getDeadTime() > Calendar.getInstance()
						.getTimeInMillis();
		return res;
	}

	@Override
	public void addHiddenFieldIfNeed(HttpServletRequest request) {
		request.setAttribute("hidden_field", false);
	}

	@Override
	public boolean isHiddenFieldNeed() {
		return false;
	}

}
