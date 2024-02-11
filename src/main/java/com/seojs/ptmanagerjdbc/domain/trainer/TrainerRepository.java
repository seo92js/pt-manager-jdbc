package com.seojs.ptmanagerjdbc.domain.trainer;

import com.seojs.ptmanagerjdbc.mapper.TrainerMapper;
import com.seojs.ptmanagerjdbc.web.dto.TrainerTimeUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrainerRepository {
    private final TrainerMapper trainerMapper;

    public Long save(Trainer trainer) {
        trainerMapper.save(trainer);
        return trainer.getId();
    }

    public Optional<Trainer> findById(Long id) {
        return trainerMapper.findById(id);
    }

    public Optional<Trainer> findByLoginId(String loginId) {
        return trainerMapper.findByLoginId(loginId);
    }

    public List<Trainer> findAll(String name) {
        return trainerMapper.findAll(name);
    }

    public void update(Long id, TrainerTimeUpdateDto trainerTimeUpdateDto) {
        trainerMapper.update(id, trainerTimeUpdateDto);
    }
}
