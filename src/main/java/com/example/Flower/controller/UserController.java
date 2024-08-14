/* UserController.java */
package com.example.Flower.controller;

import com.example.Flower.dto.UserForm;
import com.example.Flower.entity.UserRole;
import com.example.Flower.entity.User;
import com.example.Flower.repository.UserRepository;
import com.example.Flower.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class UserController extends SessionCheckController {

    @Autowired
    private UserService userService; // UserService 의존성 주입

    @ModelAttribute
    public void addAttributes(Model model, @SessionAttribute(name="userId", required=false) Long userId) {
        // 로그인된 사용자의 닉네임을 모델에 추가
        if (userId != null) {
            User loginUser = userService.getLoginUserById(userId);
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
            }
        }
    }

    @ModelAttribute
    public void adminUserCheck(Model model, @SessionAttribute(name="userId", required=false) Long userId) {
        // 로그인된 사용자가 관리자(admin)인지 여부를 모델에 추가
        if (userId != null) {
            User loginUser = userService.getLoginUserById(userId);
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
                model.addAttribute("isAdmin", loginUser.getRole().equals(UserRole.ADMIN));
            }
        }
    }

    @GetMapping("/user/new")
    public String newUserForm() {
        // 새 사용자 폼 페이지로 이동
        return "users/new";
    }

    @GetMapping("/user")
    public String userIndex(Model model) {
        // 모든 사용자 목록을 모델에 추가하고 사용자 인덱스 페이지로 이동
        List<User> userEntityList = userService.findAllUsers();
        model.addAttribute("userList", userEntityList);
        return "users/index";
    }

    //비활성화된 사용자 목록 확인하기
    @GetMapping("/user/inactive")
    public String inactiveUserIndex(Model model) {
        // 비활성화된 사용자 목록을 모델에 추가하고 해당 페이지로 이동
        List<User> inactiveUserList = userService.findInactiveUsers();
        model.addAttribute("inactiveUserList", inactiveUserList);
        return "users/inactive_index";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        // 특정 사용자를 편집할 수 있는 페이지로 이동
        User userEntity = userService.findUserById(id);
        model.addAttribute("user", userEntity);
        return "users/edit";
    }

    @GetMapping("/user/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        // 특정 사용자의 상세 정보를 보여주는 페이지로 이동
        log.info("id = " + id);
        User userEntity = userService.findUserById(id);
        model.addAttribute("user", userEntity);

        // 사용자 활성화 상태에 따라 다른 페이지로 이동
        if (userEntity.getActive() == 1) {
            return "users/show"; // 활성화된 사용자 상세 페이지
        } else {
            return "users/inactive_show"; // 비활성화된 사용자 상세 페이지
        }
        //return "users/show";
    }

//    @GetMapping("/user/{id}/delete")
//    public String deleteUser(@PathVariable Long id, RedirectAttributes rttr) {
//        // 특정 사용자를 삭제하고 사용자 인덱스 페이지로 리다이렉트
//        userService.deleteUser(id);
//        rttr.addFlashAttribute("msg", "삭제됐습니다!");
//        return "redirect:/user";
//    }

    //사용자 계정 삭제(비활성화)
    @GetMapping("/user/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes rttr) {
        userService.deactivateUser(id); // 비활성화 메서드를 호출
        rttr.addFlashAttribute("msg", "계정이 삭제되었습니다!");//실제로는 비활성화
        return "redirect:/user";
    }

    //사용자 계정 되살리기(활성화)
    @GetMapping("/user/{id}/activate")
    public String activateUser(@PathVariable Long id, RedirectAttributes rttr) {
        User user = userService.activateUser(id);
        if (user != null) {
            rttr.addFlashAttribute("msg", "계정을 되살렸습니다!");
        } else {
            rttr.addFlashAttribute("msg", "계정을 되살릴 수 없습니다.");
        }
        return "redirect:/user";
    }

}