package com.seojs.ptmanagerjdbc.domain.admin;

import lombok.Getter;

@Getter
public class Admin {
    private Long id;
    private String loginId;
    private String name;
    private String password;

    public Admin(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
