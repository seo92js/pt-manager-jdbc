package com.seojs.ptmanagerjdbc.service;

import com.seojs.ptmanagerjdbc.domain.member.Member;
import com.seojs.ptmanagerjdbc.domain.member.MemberRepository;
import com.seojs.ptmanagerjdbc.domain.memberticket.MemberTicket;
import com.seojs.ptmanagerjdbc.domain.memberticket.MemberTicketRepository;
import com.seojs.ptmanagerjdbc.domain.message.Message;
import com.seojs.ptmanagerjdbc.domain.message.MessageRepository;
import com.seojs.ptmanagerjdbc.domain.reserve.Reserve;
import com.seojs.ptmanagerjdbc.domain.reserve.ReserveRepository;
import com.seojs.ptmanagerjdbc.domain.ticket.Ticket;
import com.seojs.ptmanagerjdbc.domain.ticket.TicketRepository;
import com.seojs.ptmanagerjdbc.domain.trainer.TrainerRepository;
import com.seojs.ptmanagerjdbc.exception.MemberDuplicateEx;
import com.seojs.ptmanagerjdbc.exception.MemberNotFoundEx;
import com.seojs.ptmanagerjdbc.exception.TicketNotFoundEx;
import com.seojs.ptmanagerjdbc.web.dto.MemberDto;
import com.seojs.ptmanagerjdbc.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    private final MessageRepository messageRepository;

    private final TrainerRepository trainerRepository;

    private final TicketRepository ticketRepository;

    private final MemberTicketRepository memberTicketRepository;

    private final ReserveRepository reserveRepository;

    private final TicketService ticketService;

    @Transactional
    public Long save(MemberDto memberDto) {
        memberDuplicateCheck(memberDto.getLoginId());

        return memberRepository.save(new Member(memberDto.getLoginId(), memberDto.getName(), memberDto.getPassword()));
    }

    @Transactional
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundEx("멤버가 없습니다. id = " + id));

        //보낸메시지
        List<Message> sentMessages = messageRepository.findBySendMemberId(id);
        member.addAllSentMessage(sentMessages);

        //받은메시지
        List<Message> receivedMessages = messageRepository.findByReceiveMemberId(id);
        member.addAllReceivedMessage(receivedMessages);

        //이용권
        List<MemberTicket> memberTickets = memberTicketRepository.findByMemberId(id);
        List<Ticket> tickets = new ArrayList<>();
        for (MemberTicket memberTicket : memberTickets) {
            Long ticketId = memberTicket.getTicketId();
            Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundEx("이용권이 없습니다. id = " + ticketId));
            tickets.add(ticket);
        }
        member.addAllTicket(tickets);

        //예약
        List<Reserve> reserves = reserveRepository.findByMemberId(id);
        member.addAllReserve(reserves);

        return new MemberResponseDto(member);
    }

    @Transactional
    public List<MemberResponseDto> findAll(String name) {
        return memberRepository.findAll(name).stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    void memberDuplicateCheck(String loginId) {
        memberRepository.findByLoginId(loginId).ifPresent(existingMember -> {
            throw new MemberDuplicateEx("중복 된 유저가 있습니다. loginId=" + loginId);
        });
    }
}
