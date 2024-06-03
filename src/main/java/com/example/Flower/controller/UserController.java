/* UserController.java */
package com.example.Flower.controller;

import com.example.Flower.dto.UsersForm;
import com.example.Flower.entity.Users;
import com.example.Flower.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class UserController extends SessionController{

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users/new")
    public String newUserForm() {
        return "users/new";
    }

    @PostMapping("/users/create")
    public String createUser(UsersForm form) {
        log.info(form.toString());
        Users user = form.toEntity();
        log.info(user.toString());
        Users saved = usersRepository.save(user);
        log.info(saved.toString());
        return "redirect:/users/" + saved.getId();
    }

    @GetMapping("/users")
    public String userIndex(Model model) {
        List<Users> userEntityList = usersRepository.findAll();
        model.addAttribute("userList", userEntityList);
        return "users/index";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        Users userEntity = usersRepository.findById(id).orElse(null);
        if (userEntity != null) {
            model.addAttribute("user", userEntity);
            model.addAttribute("isAdmin", userEntity.getClasses() == 0);
            model.addAttribute("isUser", userEntity.getClasses() == 1);
        }
        return "users/edit";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute UsersForm form) {
        Users userEntity = form.toEntity();
        Users target = usersRepository.findById(userEntity.getId()).orElse(null);
        if (target != null) {
            target.setUserId(userEntity.getUserId());
            target.setPassword(userEntity.getPassword());
            target.setName(userEntity.getName());
            target.setAge(userEntity.getAge());
            target.setPhoneNumber(userEntity.getPhoneNumber());
            target.setEmail(userEntity.getEmail());
            target.setClasses(userEntity.getClasses());
            usersRepository.save(target);
        }
        return "redirect:/users/" + userEntity.getId();
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        log.info("id = " + id);
        Users userEntity = usersRepository.findById(id).orElse(null);
        model.addAttribute("user", userEntity);
        return "users/show";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes rttr) {
        Users target = usersRepository.findById(id).orElse(null);
        if (target != null) {
            usersRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        return "redirect:/users";
    }

    @Autowired
    private HttpServletRequest request;

}