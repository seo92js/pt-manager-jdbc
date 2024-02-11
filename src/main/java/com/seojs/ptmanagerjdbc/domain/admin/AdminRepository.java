package com.seojs.ptmanagerjdbc.domain.admin;

import com.seojs.ptmanagerjdbc.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AdminRepository {
    private final AdminMapper adminMapper;

    public Long save(Admin admin) {
        adminMapper.save(admin);
        return admin.getId();
    }

    public Optional<Admin> findById(Long id) {
        return adminMapper.findById(id);
    }

    public List<Admin> findAll(String name) {
        return adminMapper.findAll(name);
    }
}
