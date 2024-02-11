package com.seojs.ptmanagerjdbc.service;

import com.seojs.ptmanagerjdbc.domain.member.Member;
import com.seojs.ptmanagerjdbc.domain.member.MemberRepository;
import com.seojs.ptmanagerjdbc.domain.reserve.Reserve;
import com.seojs.ptmanagerjdbc.domain.reserve.ReserveRepository;
import com.seojs.ptmanagerjdbc.domain.ticket.Ticket;
import com.seojs.ptmanagerjdbc.domain.ticket.TicketRepository;
import com.seojs.ptmanagerjdbc.domain.trainer.Trainer;
import com.seojs.ptmanagerjdbc.domain.trainer.TrainerRepository;
import com.seojs.ptmanagerjdbc.exception.*;
import com.seojs.ptmanagerjdbc.web.dto.ReserveDto;
import com.seojs.ptmanagerjdbc.web.dto.ReserveFindDto;
import com.seojs.ptmanagerjdbc.web.dto.TicketUpdateNumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final ReserveRepository reserveRepository;

    @Transactional
    public void reserve(ReserveDto reserveDto) {
        Member member = memberRepository.findById(reserveDto.getMemberId()).orElseThrow(() -> new MemberNotFoundEx("멤버가 없습니다. id = " + reserveDto.getMemberId()));
        Trainer trainer = trainerRepository.findById(reserveDto.getTrainerId()).orElseThrow(() -> new TrainerNotFoundEx("트레이너가 없습니다. id + " + reserveDto.getTrainerId()));
        Ticket ticket = ticketRepository.findById(reserveDto.getTicketId()).orElseThrow(() -> new TicketNotFoundEx("이용권이 없습니다. id + " + reserveDto.getTicketId()));

        ticket.use();

        Reserve reserve = new Reserve(member, trainer, ticket, reserveDto.getReserveTime().truncatedTo(ChronoUnit.HOURS));

        reserveDuplicateCheck(member, reserve);

        member.addReserve(reserve);

        reserveRepository.save(reserve);

        ticketService.updateRemainNum(reserveDto.getTicketId(), new TicketUpdateNumDto(ticket.getRemainNum(), ticket.getStatus()));
    }

    @Transactional
    public List<Reserve> findByMemberIdAndDate(ReserveFindDto reserveFindDto) {
        return reserveRepository.findByMemberIdAndDate(reserveFindDto);
    }

    void reserveDuplicateCheck(Member member, Reserve reserve) {
        LocalDateTime reserveTime = reserve.getReserveTime();

        if (member.getReserves().stream().anyMatch(r -> r.getReserveTime().isEqual(reserveTime)))
            throw new ReserveDuplicateEx("중복 된 예약이 있습니다. reserveTime = " + reserveTime);
    }
}
