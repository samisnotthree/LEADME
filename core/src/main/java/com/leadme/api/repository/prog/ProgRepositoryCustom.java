package com.leadme.api.repository.prog;

import com.leadme.api.dto.ProgDto;
import com.leadme.api.dto.ProgSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProgRepositoryCustom {
    Page<ProgDto> searchProgs(ProgSearchCondition condition, Pageable pageable);
}
