package com.seojs.ptmanagerjdbc.domain.reserve;

import com.seojs.ptmanagerjdbc.domain.member.Member;
import com.seojs.ptmanagerjdbc.domain.ticket.Ticket;
import com.seojs.ptmanagerjdbc.domain.trainer.Trainer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class Reserve {
    private Long id;
    private Member member;
    private Trainer trainer;
    private Ticket ticket;
    private LocalDateTime reserveTime;

    public Reserve(Member member, Trainer trainer, Ticket ticket, LocalDateTime reserveTime) {
        this.member = member;
        this.trainer = trainer;
        this.ticket = ticket;
        this.reserveTime = reserveTime;
    }
}
