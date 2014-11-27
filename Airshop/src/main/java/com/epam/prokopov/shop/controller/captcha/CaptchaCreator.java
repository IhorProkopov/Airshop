package com.epam.prokopov.shop.controller.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaCreator {

	public void createCaptcha(String text, String image_extension,
			HttpServletRequest request, HttpServletResponse response)
			throws java.io.IOException {
		response.setContentType("image/jpg");
		BufferedImage bufferedImage;
		int width = 150;
		int height = 50;
		bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		Font font = new Font("Arial", Font.BOLD, 18);
		g2d.setFont(font);
		g2d.setPaint(new GradientPaint(0, height / 2, new Color(218, 232, 215),
				0, height, new Color(254, 254, 254), true));
		g2d.setPaint(new GradientPaint(0, 0, new Color(180, 180, 180), 0,
				height / 2, new Color(222, 222, 222), true));
		g2d.setColor(new Color(151, 100, 60));
		g2d.drawString(text, 40, 30);
		for (int i = 0; i < 100; i += 5) {
			g2d.drawLine(20 + i, 10, 30 + i, 38);
		}
		g2d.dispose();
		OutputStream imgOut = response.getOutputStream();
		ImageIO.write(bufferedImage, image_extension, imgOut);		
	}
	
}
