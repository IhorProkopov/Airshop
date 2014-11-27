package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.controller.captcha.CaptchaSaver;
import com.epam.prokopov.shop.model.CorrectFileExtentionals;
import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserRoles;
import com.epam.prokopov.shop.service.RegistrationValidator;
import com.epam.prokopov.shop.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

@MultipartConfig
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String directory;
    private final static String REGISTRATION_URL = "/registration.jsp";
    private final static String GOODS_URL = "/Airshop/goods";
    private UserService userService;
    private CaptchaSaver captchaSaver;
    private Random idGenerator;
    private RegistrationValidator validator;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        userService = (UserService) servletContext.getAttribute("userService");
        captchaSaver = (CaptchaSaver) servletContext
                .getAttribute("captchaClass");
        validator = (RegistrationValidator) servletContext.getAttribute("validator");
        directory = servletContext.getInitParameter("directory");
        idGenerator = new Random();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        Integer id = idGenerator.nextInt();
        request.setAttribute("captca_id", id);
        request.setAttribute("invalid_captcha", false);
        request.getRequestDispatcher(REGISTRATION_URL).forward(request,
                response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        RegistrationDataBean rb = new RegistrationDataBean();
        setUpUserInf(request, rb);
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
                REGISTRATION_URL);
        Integer id = idGenerator.nextInt();
        boolean res = true;
        Map<String, String> userData = new HashMap<>();
        List<String> validationResult = validator.validate(rb);
        if (validationResult.size() > 0) {
            res = false;
        }
        try {
            if (userService.exist(rb.getEmail())) {
                validationResult.add("User already exist");
                res = false;
            }
        } catch (Exception e) {
            sendToErrorPage(request, response);
        }
        String captchaId = request.getParameter("captcha_id");
        if (!captchaSaver.isCorrect(request, rb.getUserCaptcha(), captchaId)) {
            validationResult.add("Captcha is incorrect");
            res = false;
        }
        if (res) {
            res = saveImage(request, rb, validationResult);
        }
        if (res) {
            try {
                userService.addNewUser(new User(rb.getName(), rb.getSurname(),
                        rb.getEmail(), rb.getPass(), rb.isDelivery(), rb
                        .getPhoto(), UserRoles.USER));
            } catch (Exception e) {
                sendToErrorPage(request, response);
            }
            response.sendRedirect(GOODS_URL);
        } else {
            userData.put("email", rb.getEmail());
            userData.put("name", rb.getName());
            userData.put("surname", rb.getSurname());
            userData.put("captca_id", id.toString());
            captchaSaver.addHiddenFieldIfNeed(request);
            addComments(userData, request);
            request.setAttribute("validation", validationResult);
            rd.forward(request, response);
        }
    }

    private void setUpUserInf(HttpServletRequest request,
                              RegistrationDataBean rb) {
        rb.setName(request.getParameter("name"));
        rb.setSurname(request.getParameter("surname"));
        rb.setPass(request.getParameter("password"));
        rb.setConfPass(request.getParameter("confPassword"));
        rb.setEmail(request.getParameter("email"));
        rb.setUserCaptcha(request.getParameter("captcha"));
        String tempDelivery = request.getParameter("delivery");
        if (tempDelivery == null) {
            rb.setDelivery(false);
        } else {
            rb.setDelivery(true);
        }
    }

    private void addComments(Map<String, String> userData,
                             HttpServletRequest request) {
        if (userData != null) {
            for (Entry<String, String> e : userData.entrySet()) {
                request.setAttribute(e.getKey(), e.getValue());
            }
        }
    }

    private boolean saveImage(HttpServletRequest request,
                              RegistrationDataBean rb,
                              List<String> validationResult) throws IllegalStateException,
            IOException, ServletException {
        Part filePart = request.getPart("photo");
        if (filePart != null) {
            String fileExtentional = getFileExtention(filePart);
            String fileName = fileExtentional == null ? "defaultAvatar" : rb
                    .getEmail();
            fileExtentional = fileName.equals("defaultAvatar") ? ".jpg"
                    : fileExtentional;
            try {
                CorrectFileExtentionals.valueOf(fileExtentional.substring(1)
                        .toLowerCase());
            } catch (IllegalArgumentException e) {
                validationResult.add("Incorrect file");
                return false;
            }
            rb.setPhoto(fileName + fileExtentional);
            if (fileName.equals("defaultAvatar")) {
                return true;
            }
            try (InputStream fileStream = filePart.getInputStream()) {
                Files.copy(fileStream,
                        Paths.get(directory + fileName + fileExtentional));
            }
        }

        return true;
    }

    private String getFileExtention(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        String fileName = null;
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                fileName = token.substring(token.indexOf("=") + 2,
                        token.length() - 1);
            }
        }
        if (fileName.equals("")) {
            return null;
        }
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex);
    }

    private void sendToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        request.getRequestDispatcher("/500.jsp").forward(request,
                response);
    }

}
