package com.grupo01.java6.faal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlaDTO {
    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private boolean isPaused;
    private LocalDateTime pauseTime;
    private Duration pausedDuration = Duration.ZERO;
    private Duration remainingTime;
    private String formattedRemainingTime;

    public void calculateRemaining() {
        if (isPaused && pausedDuration != null) {
            this.remainingTime = Duration.between(startTime, pauseTime).minus(pausedDuration);
        } else {
            this.remainingTime = Duration.between(LocalDateTime.now(), deadline);
        }
        this.formattedRemainingTime = formatDuration(remainingTime);
    }

    private String formatDuration(Duration duration) {
        if (duration.isNegative()) return "00:00:00";

        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}