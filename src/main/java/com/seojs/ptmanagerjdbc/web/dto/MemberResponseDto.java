package com.seojs.ptmanagerjdbc.web.dto;

import com.seojs.ptmanagerjdbc.domain.member.Member;
import com.seojs.ptmanagerjdbc.domain.message.Message;
import com.seojs.ptmanagerjdbc.domain.reserve.Reserve;
import com.seojs.ptmanagerjdbc.domain.ticket.Ticket;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MemberResponseDto {
    private String loginId;
    private String name;
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<Reserve> reserves = new ArrayList<>();

    public MemberResponseDto(Member member) {
        this.loginId = member.getLoginId();
        this.name = member.getName();
        this.sentMessages = member.getSentMessages();
        this.receivedMessages = member.getReceivedMessages();
        this.tickets = member.getTickets();
        this.reserves = member.getReserves();
    }
}
