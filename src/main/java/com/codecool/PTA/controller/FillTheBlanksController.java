package com.codecool.PTA.controller;

import com.codecool.PTA.config.TemplateEngineUtil;
import com.codecool.PTA.model.quest.FillInTheBlank;
import com.codecool.PTA.model.user.Student;
import com.codecool.PTA.persistence.PersistenceImplementation;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FillTheBlanksController extends AbstractController {
    
    private PersistenceImplementation persistenceImplementation;

    public FillTheBlanksController(PersistenceImplementation persistenceImplementation) {
        this.persistenceImplementation = persistenceImplementation;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(checkLogin(req)){
            WebContext context = new WebContext(req, resp, req.getServletContext());
            String id = req.getParameter("id");
        isNewFriendRequest(req);

            FillInTheBlank fill1 = persistenceImplementation.getEm().find(FillInTheBlank.class, Long.valueOf(id));
            context.setVariable("fill", fill1);

            Student student = (Student) getLoggedInUser(req);
            context.setVariable("student", student);
            context.setVariable("left", req.getParameter("left"));

            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            engine.process("fillInTheBlank/fillInTheBlank.html", context, resp.getWriter());
        } else {
            resp.sendRedirect("/login");
        }
    }
}
