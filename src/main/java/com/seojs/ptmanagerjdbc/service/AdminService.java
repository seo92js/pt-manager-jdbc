package com.seojs.ptmanagerjdbc.service;

import com.seojs.ptmanagerjdbc.domain.admin.Admin;
import com.seojs.ptmanagerjdbc.domain.admin.AdminRepository;
import com.seojs.ptmanagerjdbc.exception.AdminNotFoundEx;
import com.seojs.ptmanagerjdbc.web.dto.AdminDto;
import com.seojs.ptmanagerjdbc.web.dto.AdminResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    @Transactional
    public Long save(AdminDto adminDto) {
        return adminRepository.save(new Admin(adminDto.getLoginId(), adminDto.getName(), adminDto.getPassword()));
    }

    @Transactional
    public AdminResponseDto findById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new AdminNotFoundEx("관리자가 없습니다. id = " + id));
        return new AdminResponseDto(admin);
    }

    @Transactional
    public List<AdminResponseDto> findAll(String name){
        return adminRepository.findAll(name).stream()
                .map(AdminResponseDto::new)
                .collect(Collectors.toList());
    }
}
