package com.epam.prokopov.shop.controller.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ResponseWrapper extends HttpServletResponseWrapper {


    private ServletOutputStream servletOutputStream;
    private PrintWriter printWriter;
    private ByteArrayOutputStream byteArrayOutputStream;

    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        byteArrayOutputStream = new ByteArrayOutputStream();
        servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }
        };
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }


    public byte[] getByteArray() throws IOException {
        servletOutputStream.flush();
        if(printWriter!=null){
        printWriter.flush();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter == null) {
            printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream, getResponse().getCharacterEncoding()));
        }
        return printWriter;
    }

    public void close() throws IOException {
        if (byteArrayOutputStream != null) {
            byteArrayOutputStream.close();
        }
        if (printWriter != null) {
            printWriter.close();
        }
    }

    public String getString(){
        return byteArrayOutputStream.toString();
    }

    @Override
    public void setContentLength(int len) {
    }
}
