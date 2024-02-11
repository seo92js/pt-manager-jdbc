package com.seojs.ptmanagerjdbc.web.dto;

import com.seojs.ptmanagerjdbc.domain.message.Message;
import com.seojs.ptmanagerjdbc.domain.reserve.Reserve;
import com.seojs.ptmanagerjdbc.domain.trainer.Trainer;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TrainerResponseDto {
    private Long id;
    private String loginId;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Message> sentMessages = new ArrayList<>();
    private List<Message> receivedMessages = new ArrayList<>();
    private List<Reserve> reserves = new ArrayList<>();

    public TrainerResponseDto(Trainer trainer) {
        this.id = trainer.getId();
        this.loginId = trainer.getLoginId();
        this.name = trainer.getName();
        this.startTime = trainer.getStartTime();
        this.endTime = trainer.getEndTime();
        this.sentMessages = trainer.getSentMessages();
        this.receivedMessages = trainer.getReceivedMessages();
        this.reserves = trainer.getReserves();
    }
}
