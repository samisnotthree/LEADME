package com.leadme.api.service;

import com.leadme.api.entity.Prog;
import com.leadme.api.repository.guide.GuideRepository;
import com.leadme.api.repository.prog.ProgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgService {
    private final ProgRepository progRepository;
    private final GuideRepository guideRepository;

    @Transactional
    public Long addProg(Prog prog) {
        Optional.ofNullable(prog.getGuide()).orElseThrow(() -> new IllegalStateException("가이드 정보가 올바르지 않습니다."));
        Optional.ofNullable(prog.getName()).orElseThrow(() -> new IllegalStateException("프로그램명이 올바르지 않습니다."));

        guideRepository.findById(prog.getGuide().getGuideId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 가이드입니다."));
        Optional<Prog> foundProg = progRepository.findByName(prog.getName());
        foundProg.ifPresent(p -> {
            throw new IllegalStateException("이미 존재하는 프로그램명입니다.");
        });

        return progRepository.save(prog).getProgId();
    }

    @Transactional
    public void deleteProg(Long progId) {
        Optional<Prog> progOptional = progRepository.findById(progId);
        progOptional.ifPresent(prog -> prog.setOutDate(LocalDateTime.now()));
    }
}
