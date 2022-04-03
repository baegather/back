package com.example.baegether.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeStamp {

    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

}
