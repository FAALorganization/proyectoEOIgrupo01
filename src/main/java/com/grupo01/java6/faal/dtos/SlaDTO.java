package com.grupo01.java6.faal.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SlaDTO {

    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private boolean isPaused;
    private LocalDateTime pauseTime;
    private Duration pausedDuration = Duration.ZERO;
    private Duration frozenRemainingTime = null;


}
