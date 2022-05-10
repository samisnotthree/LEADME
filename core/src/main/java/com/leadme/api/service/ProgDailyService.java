package com.leadme.api.service;

import com.leadme.api.entity.ProgDaily;
import com.leadme.api.repository.progDaily.ProgDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgDailyService {

    private final ProgDailyRepository progDailyRepository;

    @Transactional
    public Long addProgDaily(ProgDaily progDaily) {
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
        if (!validateDeleteProgDaily(progDailyId)) {
            throw new IllegalStateException("구매자가 있어 삭제할 수 없습니다.");
        }
        progDailyRepository.deleteById(progDailyId);
    }

    public boolean validateDeleteProgDaily(Long progDailyId) {
        Optional<ProgDaily> foundProgDaily = progDailyRepository.findById(progDailyId);
        AtomicBoolean canDelete = new AtomicBoolean(false);

        foundProgDaily.ifPresent(progDaily -> {
            canDelete.set(progDaily.getOrders().isEmpty());
        });

        return canDelete.get();
    }
}
