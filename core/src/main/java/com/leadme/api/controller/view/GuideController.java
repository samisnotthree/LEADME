package com.leadme.api.controller.view;

import com.leadme.api.dto.GuideDto;
import com.leadme.api.dto.GuideHashtagDto;
import com.leadme.api.entity.Guide;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.service.GuideHashtagService;
import com.leadme.api.service.GuideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GuideController {
    private final GuideService guideService;
    private final GuideRepository guideRepository;
    private final GuideHashtagService guideHashtagService;

    @Transactional
    @PostMapping("/guides/v2")
    public String joinGuide(@Valid GuideDto guideDto, BindingResult result) {
        if (result.hasErrors()) {
            return "members/mypage";
        }
        guideService.joinGuide(1L, guideDto.getDesc());

        return "redirect:/";
    }

//    @GetMapping("/guides/{id}")
//    public String findGuide(@PathVariable("id") Long guideId, Model model) {
//        List<GuideDto> guides = guideRepository.findById(guideId)
//                .stream()
//                .map(GuideDto::new)
//                .collect(Collectors.toList());
//        model.addAttribute("guides", guides);
//        return "";
//    }

    @Transactional
    @PostMapping("/guides/v2/update/{desc}")
    public void changeDesc(Long guideId, @PathVariable("desc") String desc) {
        Optional<Guide> guide = guideRepository.findById(guideId);
        guide.ifPresent(g -> g.changeDesc(desc));
    }

    @Transactional
    @PostMapping("/guides/v2/delete/{id}")
    public void deleteGuide(@PathVariable("id") Long guideId) {
        guideService.deleteGuide(guideId);
    }

    /**
     *  가이드 해시태그 등록
     */
    @Transactional
    @PostMapping("/guide-hashtags/v2")
    public ResponseEntity<Long> addGuideHashtag(@RequestBody GuideHashtagDto guideHashtagDto) {
        return ResponseEntity.ok(guideHashtagService.addGuideHashtag(guideHashtagDto.getGuide().getGuideId(), guideHashtagDto.getHashtag().getHashtagId()).getGuideHashtagId());
    }
}
