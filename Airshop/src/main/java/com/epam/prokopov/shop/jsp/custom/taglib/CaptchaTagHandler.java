package com.epam.prokopov.shop.jsp.custom.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CaptchaTagHandler extends SimpleTagSupport {

	private String CAPTCHA_INSERT_FIRST_PART = "<img src='captcha-image.jpg?id=";
	private String CAPTCHA_INSERT_LAST_PART = "'/>";
	private String HIDDEN_INSERT_FIRST_PART = "<input type='hidden' name='captcha_id' value='";
	private String HIDDEN_INSERT_LAST_PART = "'>";
	private String id;
	private boolean hidden;

	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		out.println(CAPTCHA_INSERT_FIRST_PART + id + CAPTCHA_INSERT_LAST_PART);
		if (hidden) {
			out.println(HIDDEN_INSERT_FIRST_PART + id + HIDDEN_INSERT_LAST_PART);
		}
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public boolean getHidden() {
		return hidden;
	}

}
