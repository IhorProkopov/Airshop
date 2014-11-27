package com.epam.prokopov.shop.controller.captcha;

import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.prokopov.shop.model.Captcha;

public abstract class CaptchaSaver {

	protected long lifeTime;
	protected Map<String, Captcha> captchaMap;

	public CaptchaSaver(long lifeTime) {
		this.lifeTime = lifeTime;
		captchaMap = Collections
				.synchronizedMap(new LinkedHashMap<String, Captcha>());
	}

	public abstract void saveCaptcha(HttpServletRequest request,
			HttpServletResponse response, String id, String text);

	public abstract boolean isCorrect(HttpServletRequest request,
			String userText, String id);

	public abstract void addHiddenFieldIfNeed(HttpServletRequest request);

	public abstract boolean isHiddenFieldNeed();

	protected void cleanMap() {
		if (captchaMap.size() < 500) {
			return;
		}

		long time = Calendar.getInstance().getTimeInMillis();
		Set<Entry<String, Captcha>> captchaSet = captchaMap.entrySet();
		synchronized (captchaMap) {
			Iterator<Entry<String, Captcha>> it = captchaSet.iterator();
			for (int i = 0; i < 50 || it.hasNext(); i++) {
				Entry<String, Captcha> entry = it.next();
				if (time >= entry.getValue().getDeadTime()) {
					it.remove();
				} else {
					break;
				}
			}
		}

	}

	protected boolean checkOnValid(Captcha cap, String userText, String id) {
		boolean res = false;
		if (cap != null) {
			if (cap.equals(new Captcha(userText, id, Calendar.getInstance()
					.getTimeInMillis()))
					&& Calendar.getInstance().getTimeInMillis() < cap
							.getDeadTime()) {
				res = true;
			}
			captchaMap.remove(id);
		}
		return res;
	}

}
