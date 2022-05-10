package com.leadme.api.repository.member;

import com.leadme.api.dto.MemberDto;
import com.leadme.api.dto.MemberSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<MemberDto> searchMembers(MemberSearchCondition condition, Pageable pageable);
}
