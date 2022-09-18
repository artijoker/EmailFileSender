package com.example.emailfilesender;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@WebServlet(name = "EmailSenderServlet", value = "/send-message")
@MultipartConfig(
        fileSizeThreshold = 5_242_880,
        maxFileSize = 20_971_520L,
        maxRequestSize = 41_943_040L
)
public class EmailSenderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {}

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        var to = request.getParameter("recipient");
        var subject = request.getParameter("subject");
        var body = request.getParameter("body");
        var part = request.getPart("file1");

        File file = null;
        if (!part.getSubmittedFileName().isEmpty())
            file = this.processFile(part);

        try {
            var serviceEmail = new EmailSender(getServletContext());
            if (file != null)
                serviceEmail.sendEmail(to, subject, body,file);
            else
                serviceEmail.sendEmail(to, subject, body);
        } catch (MessagingException e) {
            request.setAttribute("error", e.getMessage());
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
        response.sendRedirect("success.jsp");
    }

    private File processFile(Part part) throws IOException {
        var inputStream = part.getInputStream();

        var file = new File(part.getSubmittedFileName());
        var outputStream = new FileOutputStream(file);

        int read;
        final byte[] bytes = new byte[1024];
        while((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        outputStream.close();
        inputStream.close();
        return file;
    }
}
