package com.leadme.api.controller.view;

import com.leadme.api.dto.HashtagDto;
import com.leadme.api.dto.condition.MemberSearchCondition;
import com.leadme.api.dto.condition.OrderSearchCondition;
import com.leadme.api.dto.condition.ProgSearchCondition;
import com.leadme.api.dto.sdto.MemberGuideDto;
import com.leadme.api.dto.sdto.OrderProgDailyDto;
import com.leadme.api.dto.sdto.ProgGuideMemberDto;
import com.leadme.api.dto.sdto.ProgHashtagsDto;
import com.leadme.api.repository.hashtag.HashtagQueryRepository;
import com.leadme.api.repository.hashtag.HashtagRepository;
import com.leadme.api.repository.member.MemberQueryRepository;
import com.leadme.api.repository.order.OrderQueryRepository;
import com.leadme.api.repository.prog.ProgQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final ProgQueryRepository progQueryRepository;
    private final OrderQueryRepository orderQueryRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final HashtagRepository hashtagRepository;
    private final HashtagQueryRepository hashtagQueryRepository;

    @RequestMapping("/")
    public String home(Model model) {
        List<ProgHashtagsDto> hashtags = hashtagQueryRepository.searchHashtagsWithCount()
                .stream()
                .sorted(Comparator.comparing(ProgHashtagsDto::getCount).reversed())
                .limit(10)
                .map(ProgHashtagsDto::new)
                .collect(Collectors.toList());
        model.addAttribute("hashtags", hashtags);

        ProgSearchCondition condition = new ProgSearchCondition();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("progId").descending());

        List<ProgGuideMemberDto> progs = progQueryRepository.searchProgs(condition, pageable).getContent();
        model.addAttribute("progs", progs);

        return "home";
    }

    @GetMapping("/admin/v2")
    public String admin(Model model) {
        OrderSearchCondition orderCondition = new OrderSearchCondition();
        Pageable orderPageable = PageRequest.of(0, 10, Sort.by("orderId").descending());
        List<OrderProgDailyDto> orders = orderQueryRepository.searchOrdersByCondition(orderCondition, orderPageable).getContent();
        model.addAttribute("orders", orders);

        ProgSearchCondition progCondition = new ProgSearchCondition();
        Pageable progPageable = PageRequest.of(0, 10, Sort.by("progId").descending());
        List<ProgGuideMemberDto> progs = progQueryRepository.searchProgs(progCondition, progPageable).getContent();
        model.addAttribute("progs", progs);

        MemberSearchCondition memberCondition = new MemberSearchCondition();
        Pageable memberPageable = PageRequest.of(0, 10, Sort.by("memberId").descending());
        List<MemberGuideDto> members = memberQueryRepository.searchMembers(memberCondition, memberPageable).getContent();
        model.addAttribute("members", members);

        List<HashtagDto> hashtags = hashtagRepository.findByNameContains("")
                .stream()
                .map(HashtagDto::new)
                .collect(Collectors.toList());
        model.addAttribute("hashtags", hashtags);
        return "admins/admin";
    }
}