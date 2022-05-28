package com.leadme.api.service;

import com.leadme.api.entity.ProgDaily;
import com.leadme.api.repository.order.OrderRepository;
import com.leadme.api.repository.prog.ProgRepository;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgDailyService {

    private final ProgDailyRepository progDailyRepository;
    private final ProgRepository progRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long addProgDaily(ProgDaily progDaily) {
        Optional.ofNullable(progDaily.getProg()).orElseThrow(() -> new IllegalStateException("프로그램 정보가 올바르지 않습니다."));
        Optional.ofNullable(progDaily.getProgDate()).orElseThrow(() -> new IllegalStateException("프로그램 일정이 올바르지 않습니다."));

        progRepository.findById(progDaily.getProg().getProgId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 프로그램입니다."));

        if (!validateProgDaily(progDaily)) {
            throw new IllegalStateException("해당 일시에 이미 등록되어 있습니다.");
        }
        return progDailyRepository.save(progDaily).getProgDailyId();
    }

    //TODO 소요시간 고려하는 로직 추가하기
    public boolean validateProgDaily(ProgDaily progDaily) {
        List<ProgDaily> foundProgDailies = progDailyRepository.findByProgAndProgDate(progDaily.getProg(), progDaily.getProgDate());
        return foundProgDailies.isEmpty();
    }

    @Transactional
    public void deleteProgDaily(Long progDailyId) {
        Optional.ofNullable(progDailyId).orElseThrow(() -> new IllegalStateException("프로그램 일정 정보가 올바르지 않습니다."));

        Optional<ProgDaily> progDaily = progDailyRepository.findById(progDailyId);
        progDaily.ifPresentOrElse(
            pd -> {
                if (!validateDeleteProgDaily(pd)) {
                    throw new IllegalStateException("구매자가 있어 삭제할 수 없습니다.");
                }
                progDailyRepository.delete(pd);
            },
            () -> progDaily.orElseThrow(() -> new IllegalStateException("존재하지 않는 프로그램 일정입니다."))
        );
    }

    public boolean validateDeleteProgDaily(ProgDaily progDaily) {
        return orderRepository.findByProgDaily(progDaily).isEmpty();
    }
}
