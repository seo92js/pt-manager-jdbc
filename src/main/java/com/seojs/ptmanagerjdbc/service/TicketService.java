package com.seojs.ptmanagerjdbc.service;

import com.seojs.ptmanagerjdbc.domain.member.Member;
import com.seojs.ptmanagerjdbc.domain.member.MemberRepository;
import com.seojs.ptmanagerjdbc.domain.memberticket.MemberTicket;
import com.seojs.ptmanagerjdbc.domain.memberticket.MemberTicketRepository;
import com.seojs.ptmanagerjdbc.domain.ticket.Ticket;
import com.seojs.ptmanagerjdbc.domain.ticket.TicketRepository;
import com.seojs.ptmanagerjdbc.exception.MemberNotFoundEx;
import com.seojs.ptmanagerjdbc.exception.TicketDuplicateEx;
import com.seojs.ptmanagerjdbc.exception.TicketNotFoundEx;
import com.seojs.ptmanagerjdbc.web.dto.MemberTicketDto;
import com.seojs.ptmanagerjdbc.web.dto.TicketDto;
import com.seojs.ptmanagerjdbc.web.dto.TicketResponseDto;
import com.seojs.ptmanagerjdbc.web.dto.TicketUpdateNumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final MemberRepository memberRepository;
    private final TicketRepository ticketRepository;
    private final MemberTicketRepository memberTicketRepository;

    @Transactional
    public Long save(TicketDto ticketDto) {
        ticketDuplicateCheck(ticketDto.getTotalNum());

        return ticketRepository.save(new Ticket(ticketDto.getTotalNum(), ticketDto.getPrice()));
    }

    @Transactional
    public TicketResponseDto findById(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundEx("이용권이 없습니다. id = " + id));

        return new TicketResponseDto(ticket);
    }

    @Transactional
    public List<TicketResponseDto> findAll() {
        return ticketRepository.findAll().stream()
                .map(TicketResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void buyTicket(MemberTicketDto memberTicketDto) {
        Member member = memberRepository.findById(memberTicketDto.getMemberId()).orElseThrow(() -> new MemberNotFoundEx("멤버가 없습니다. id = " + memberTicketDto.getMemberId()));
        Ticket ticket = ticketRepository.findById(memberTicketDto.getTicketId()).orElseThrow(() -> new TicketNotFoundEx("이용권이 없습니다. id = " + memberTicketDto.getTicketId()));

        MemberTicket memberTicket = new MemberTicket(memberTicketDto.getMemberId(), memberTicketDto.getTicketId());
        memberTicketRepository.save(memberTicket);

        member.addTicket(ticket);
    }

    @Transactional
    public Long updateRemainNum(Long id, TicketUpdateNumDto ticketUpdateRemainNumDto) {
        return ticketRepository.updateNum(id, ticketUpdateRemainNumDto);
    }

    void ticketDuplicateCheck(Integer totalNum) {
        ticketRepository.findByTotalNum(totalNum).ifPresent(existingTicket -> {
            throw new TicketDuplicateEx("중복 된 이용권이 있습니다. totalNum=" + totalNum);
        });
    }
}
