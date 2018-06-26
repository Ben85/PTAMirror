package com.codecool.PTA.controller;

import com.codecool.PTA.model.user.Student;
import com.codecool.PTA.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//TODO
@Controller
public class UserController extends AbstractController {

    @Autowired
    private StudentService studentService;

    @GetMapping("student/{id}")
    public String displayProfile(@PathVariable("id") Long id, Model model) {
        isNewFriendRequest();
        //TODO: returns Optional, must change that
        model.addAttribute("student", studentService.findById(id));

        return "profile/profile";
    }

    @PostMapping("student/{id}")
    public String changeProfilePicture(@PathVariable("id") Long id, @ModelAttribute Student student) {
        return "redirect:profile/profile";
    }

    @GetMapping("student/{id}/certificate")
    public String displayCertificate(@PathVariable("id") Long id, Model model) {
        return "profile/certificate";
    }

    @GetMapping("student/{id}/friends")
    public String listFriends(@PathVariable("id") Long id, Model model) {
        return "friends/friends";
    }

    @GetMapping("student/{id}/friend-requests")
    public String listFriendRequests(@PathVariable("id") Long id, Model model) {
        return "friends/friendRequests";
    }

    @GetMapping("student/{id}/friendable-students")
    public String listFriendableStudents(@PathVariable("id") Long id, Model model) {
        return "listStudents/listStudents";
    }

}
