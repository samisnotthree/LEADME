package com.leadme.api.repository.member;

import com.leadme.api.dto.MemberDto;
import com.leadme.api.dto.condition.MemberSearchCondition;
import com.leadme.api.dto.sdto.MemberGuideDto;
import com.leadme.api.entity.Guide;
import com.leadme.api.entity.Member;
import com.leadme.api.service.GuideService;
import com.leadme.api.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberQueryRepositoryTest {

    @Autowired MemberQueryRepository memberQueryRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    GuideService guideService;

    @BeforeEach
    void init_data() {
        Member member = Member.builder()
                .name("name11")
                .email("email11")
                .build();
        Long joinedMemberId = memberService.joinMember(member);

        guideService.joinGuide(joinedMemberId, "desc11");

        Member member2 = Member.builder()
                .name("name22")
                .email("email22")
                .build();

        memberService.joinMember(member2);
    }

    @Test
    @DisplayName("사용자 전체 조회")
    @Transactional
    void search_members() {
        //given
        MemberSearchCondition condition = new MemberSearchCondition();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("inDate").descending());

        //when
        Page<MemberGuideDto> members = memberQueryRepository.searchMembers(condition, pageable);

        //then
        List<MemberGuideDto> foundMembers = members.getContent();
        assertThat(foundMembers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("사용자 이름으로 조회")
    @Transactional
    void search_members_name() {
        //given
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setName("name2");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("inDate").descending());

        //when
        Page<MemberGuideDto> members = memberQueryRepository.searchMembers(condition, pageable);

        //then
        List<MemberGuideDto> foundMembers = members.getContent();
        assertThat(foundMembers.get(0).getName()).isEqualTo("name22");
        assertThat(foundMembers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("사용자 이메일로 조회")
    @Transactional
    void search_members_email() {
        //given
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setEmail("email2");

        Pageable pageable = PageRequest.of(0, 10, Sort.by("inDate").descending());

        //when
        Page<MemberGuideDto> members = memberQueryRepository.searchMembers(condition, pageable);

        //then
        List<MemberGuideDto> foundMembers = members.getContent();
        assertThat(foundMembers.get(0).getEmail()).isEqualTo("email22");
        assertThat(foundMembers.size()).isEqualTo(1);
    }
}