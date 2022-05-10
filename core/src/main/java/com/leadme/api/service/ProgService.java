package com.leadme.api.service;

import com.leadme.api.entity.Prog;
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

    @Transactional
    public Long addProg(Prog prog) {
        return progRepository.save(prog).getProgId();
    }

    @Transactional
    public void deleteProg(Long progId) {
        Optional<Prog> progOptional = progRepository.findById(progId);
        progOptional.ifPresent(prog -> prog.setOutDate(LocalDateTime.now()));
    }
}
