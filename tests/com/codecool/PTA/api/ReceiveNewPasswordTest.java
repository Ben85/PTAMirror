package com.codecool.PTA.api;

import com.codecool.PTA.model.course.Course;
import com.codecool.PTA.model.course.CourseType;
import com.codecool.PTA.model.user.GenderEnum;
import com.codecool.PTA.model.user.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ReceiveNewPasswordTest extends JSONTest {

    @BeforeEach
    void setup() {
        createExamples();
        super.mockClasses();
    }

    private void createExamples() {
        Course course = new Course(CourseType.ORIENTATION, "orientation");
        GenderEnum gender = GenderEnum.OTHER;
        this.student = new Student("username", "password", "first_name", "last_name", "email", course, gender);
    }

    @Test
    void testGetNewPasswordForAppropriateStudentFromJSON() throws IOException, ServletException {

        String json = "{old: password, new: new_password}";

        when(req.getParameter("password")).thenReturn(student.getPassword());
        when(req.getReader()).thenReturn(new BufferedReader(new StringReader(json)));
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("student")).thenReturn(student);

        when(passwordHashing.isPasswordCorrect("password", "password")).thenReturn(true);
        when(passwordHashing.hashPassword("new_password")).thenReturn("new_password");

        new ReceiveNewPassword(pim, passwordHashing).doPost(req, resp);

        assertEquals("new_password", student.getPassword());
    }
}