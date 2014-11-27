package com.epam.prokopov.shop.controller.captcha;

import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.prokopov.shop.model.Captcha;

public class CookieCapthaSaver extends CaptchaSaver {

	public CookieCapthaSaver(int lifeTime) {
		super(lifeTime);
	}

	@Override
	public void saveCaptcha(HttpServletRequest request,
			HttpServletResponse response, String id, String text) {
		Cookie cookie = new Cookie("captcha_id", id);
		response.addCookie(cookie);
		captchaMap.put(id, new Captcha(text, id, Calendar.getInstance()
				.getTimeInMillis() + lifeTime));
		request.setAttribute("hidden_field", false);
		cleanMap();
	}

	@Override
	public boolean isCorrect(HttpServletRequest request, String userText,
			String id) {
		boolean res;
		Cookie[] cookies = request.getCookies();
		String idFromCookie = null;
		for (Cookie c : cookies) {
			if (c.getName().equals("captcha_id")) {
				idFromCookie = c.getValue();
				c.setMaxAge(0);
			}
		}
		if (idFromCookie == null) {
			return false;
		}
		Captcha tempCaptcha = captchaMap.get(idFromCookie);
		res = checkOnValid(tempCaptcha, userText, idFromCookie);
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
