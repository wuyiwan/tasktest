package com.example.demo.contorller;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserContorller {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/getAll")
    @ResponseBody
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public User getOne(@PathVariable String id) {
        return userService.getOne(id);
    }

    @GetMapping("/delete/{id}")
    public String remove(@PathVariable String id) {
        userService.remove(id);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    @ResponseBody
    public int removeById(@RequestParam String id) {
        return userService.remove(id);
    }

    @GetMapping("/Add1")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping("/AddUser")
    public String addUser(@ModelAttribute User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @PostMapping("/add")
    @ResponseBody
    public int add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        User user = userService.getOne(id);
        model.addAttribute("user", user);
        return "add";
    }

    @PostMapping("/modify")
    @ResponseBody
    public int modify(@RequestBody User user) {
        return userService.modify(user);
    }
}
