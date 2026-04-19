package com.example.demo.contorller;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
    public String addUser(@ModelAttribute User user, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {
            String savedPath = saveImage(imageFile);
            if (savedPath != null) {
                user.setImagePath(savedPath);
            }
        }
        
        User existingUser = userService.getOne(user.getId());
        if (existingUser != null) {
            existingUser.setLevel(user.getLevel());
            existingUser.setDescription(user.getDescription());
            existingUser.setDate(user.getDate());
            existingUser.setPic(user.getPic());
            existingUser.setDescriptionDetail(user.getDescriptionDetail());
            if (user.getImagePath() != null) {
                existingUser.setImagePath(user.getImagePath());
            }
            userService.modify(existingUser);
        } else {
            userService.add(user);
        }
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

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable String id, Model model) {
        User user = userService.getOne(id);
        model.addAttribute("user", user);
        return "detail";
    }

    private String saveImage(MultipartFile imageFile) {
        try {
            String uploadDir = "D:\\image";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            String originalFilename = imageFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String uniqueFileName = UUID.randomUUID().toString() + "_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + extension;
            
            String fullPath = uploadDir + "\\" + uniqueFileName;
            File destFile = new File(fullPath);
            imageFile.transferTo(destFile);
            
            return uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
