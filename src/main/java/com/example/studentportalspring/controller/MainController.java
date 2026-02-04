package com.example.studentportalspring.controller;

import com.example.studentportalspring.model.User;
import com.example.studentportalspring.service.UserService;
import com.example.studentportalspring.service.security.SpringUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${student.portal.upload.image.directory.path}")
    private String imageDirectoryPath;

    private final UserService userService;

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal SpringUser userPrincipal,
                           ModelMap modelMap) {
        if (userPrincipal != null) {
            modelMap.addAttribute("user", userPrincipal.getUser());
        }
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(@RequestParam(required = false) String msg, ModelMap modelMap) {
        modelMap.addAttribute("msg", msg);
        return "loginPage";
    }

    @GetMapping("/registerPage")
    public String registerPage(@RequestParam(required = false) String msg, ModelMap modelMap) {
        modelMap.addAttribute("msg", msg);
        return "registerPage";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return "redirect:/registerPage?msg=Username already exists!";
        }
        userService.save(user);
        return "redirect:/loginPage?msg=Registration successful, pls login!";
    }

    @GetMapping("/image/get")
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) {
        File file = new File(imageDirectoryPath + picName);
        if (file.exists() && file.isFile()) {
            try {
                return FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
