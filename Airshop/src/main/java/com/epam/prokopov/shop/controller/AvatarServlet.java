package com.epam.prokopov.shop.controller;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class AvatarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String PHOTO_REP;

    @Override
    public void init() throws ServletException {
        ServletContext sc = getServletContext();
        PHOTO_REP = getServletContext().getInitParameter("directory");

    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        String path = request.getParameter("photo");
        File f = new File(PHOTO_REP + path);
        BufferedImage bi = ImageIO.read(f);
        OutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        out.close();
    }

}
