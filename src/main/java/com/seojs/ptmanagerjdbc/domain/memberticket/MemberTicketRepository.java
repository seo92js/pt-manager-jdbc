package com.seojs.ptmanagerjdbc.domain.memberticket;

import com.seojs.ptmanagerjdbc.mapper.MemberTicketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberTicketRepository {
    private final MemberTicketMapper memberTicketMapper;

    public void save(MemberTicket memberTicket) {
        memberTicketMapper.save(memberTicket);
    }

    public List<MemberTicket> findByMemberId(Long memberId) {
        return memberTicketMapper.findByMemberId(memberId);
    }
}
