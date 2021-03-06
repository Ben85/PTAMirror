package com.codecool.PTA.controller;

import com.codecool.PTA.config.TemplateEngineUtil;
import com.codecool.PTA.helper.Hash;
import com.codecool.PTA.persistence.PersistenceImplementation;
import com.codecool.PTA.model.user.Student;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class LoginController extends AbstractController {

    private PersistenceImplementation persistenceImplementation;
    private Hash hash;

    public LoginController(PersistenceImplementation persistenceImplementation, Hash hash) {
        this.persistenceImplementation = persistenceImplementation;
        this.hash = hash;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = new WebContext(req, resp, req.getServletContext());

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        engine.process("login/login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        List<Student> studentList = persistenceImplementation.findAllStudents();

        //TODO: error handling
        for (Student student : studentList) {
            if (username.equals(student.getUsername()) && hash.isPasswordCorrect(password, student.getPassword())) {
                session.setAttribute("student", student);
                resp.sendRedirect("/index");
                return;
            }
        }
        resp.sendRedirect("/login");
    }

}
