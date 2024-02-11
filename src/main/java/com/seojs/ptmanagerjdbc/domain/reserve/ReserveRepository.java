package com.seojs.ptmanagerjdbc.domain.reserve;

import com.seojs.ptmanagerjdbc.mapper.ReserveMapper;
import com.seojs.ptmanagerjdbc.web.dto.ReserveFindDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReserveRepository {
    private final ReserveMapper reserveMapper;

    public void save(Reserve reserve) {
        reserveMapper.save(reserve);
    }
    public List<Reserve> findByMemberId(Long memberId) {
        return reserveMapper.findByMemberId(memberId);
    }

    public List<Reserve> findByTrainerId(Long trainerId) {
        return reserveMapper.findByTrainerId(trainerId);
    }

    public List<Reserve> findByMemberIdAndDate(ReserveFindDto reserveFindDto) {
        return reserveMapper.findByMemberIdAndDate(reserveFindDto);
    }
}
