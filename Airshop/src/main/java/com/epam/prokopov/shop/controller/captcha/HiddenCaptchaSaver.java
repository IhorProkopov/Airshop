package com.epam.prokopov.shop.controller.captcha;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.prokopov.shop.model.Captcha;

public class HiddenCaptchaSaver extends CaptchaSaver {

	public HiddenCaptchaSaver(int lifeTime) {
		super(lifeTime);
	}

	@Override
	public void saveCaptcha(HttpServletRequest request,
			HttpServletResponse response, String id, String text) {
		captchaMap.put(id, new Captcha(text, id, Calendar.getInstance()
				.getTimeInMillis() + lifeTime));
		request.setAttribute("hidden_field", true);
		cleanMap();
	}

	@Override
	public boolean isCorrect(HttpServletRequest request, String userText,
			String id) {
		boolean res = false;
		Captcha tempCaptcha = captchaMap.get(id);
		if (tempCaptcha == null) {
			return false;
		}
		res = checkOnValid(tempCaptcha, userText, id);
		return res;
	}

	@Override
	public void addHiddenFieldIfNeed(HttpServletRequest request) {
		request.setAttribute("hidden_field", true);
	}

	@Override
	public boolean isHiddenFieldNeed() {
		return true;
	}

}
