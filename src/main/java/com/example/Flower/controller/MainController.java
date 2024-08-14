package com.example.Flower.controller;


import com.example.Flower.entity.Announce;
import com.example.Flower.repository.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class MainController extends SessionCheckController{
    @Autowired
    private AnnounceRepository announceRepository;

    @GetMapping("/")
    public String home(Model model) {
        // 홈 페이지로 이동합니다.

        List<Announce> announceList = announceRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // 최대 3개의 공지사항만 추출
        int maxCount = 3;
        List<Announce> recentAnnounces = announceList.subList(0, Math.min(announceList.size(), maxCount));

        model.addAttribute("announceList", recentAnnounces);

        return "main";
    }
    @GetMapping("/streaming")
    public String main(Model model) {
        // 스트리밍 페이지로 이동합니다.
        return "stream";
    }
}
