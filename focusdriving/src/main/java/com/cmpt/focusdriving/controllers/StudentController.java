package com.cmpt.focusdriving.controllers;

import java.util.Map;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cmpt.focusdriving.models.Student;
import com.cmpt.focusdriving.models.StudentRepository;
import com.cmpt.focusdriving.models.email;

import jakarta.servlet.http.HttpServletResponse;




@Controller
public class StudentController {  
    
    @Autowired
    private StudentRepository studentRepo;

    @Autowired
   private email senderService;
    

    @PostMapping("/student/add")
    public String addStudent(@RequestParam Map<String, String> newStudent) {
        System.out.println("ADD student");
        String name = newStudent.get("name");
        String email=newStudent.get("email");
        String phoneNumber=newStudent.get("phoneNumber");
        String requestMessage=newStudent.get("requestMessage");



       // Student student = new Student(name, email, phoneNumber, requestMessage);
       // studentRepo.save(student);
        return "redirect:/student/submissionComplete";
    }

    @PostMapping({"/userrequest"})
   public String sendEmail(@RequestParam Map<String, String> userid, HttpServletResponse response) {
      String emailString = (String)userid.get("email");
      String nameString = (String)userid.get("name");
      String messageString = (String)userid.get("message");
      String phoneString = (String)userid.get("phone");
      String timeString = (String)userid.get("time");
      String messageConcat = nameString + "\n" + emailString + "\n" + phoneString + "\n" + timeString + "\n" + messageString;
      senderService.sendEmail("cmpt276.groupproject@gmail.com", "New Request by" + nameString, messageConcat); // send email to our selfs as a notfication for a new request

      Student student = new Student(nameString,emailString,phoneString,messageString,timeString);
      studentRepo.save(student);

      senderService.sendEmail(emailString, "Attention Your request has been sent", "Dear " + nameString + "\n" + "your request has been sent to our invoice and we will respond back shortly");
      return "redirect:/home.html";
   }
}