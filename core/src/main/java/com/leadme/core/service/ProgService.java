package com.leadme.core.service;

import com.leadme.core.entity.Prog;
import com.leadme.core.repository.ProgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgService {
    private final ProgRepository progRepository;

    @Transactional
    public Long addProg(Prog prog) {

    }
    
}
