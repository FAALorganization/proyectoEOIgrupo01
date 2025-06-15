//package com.grupo01.java6.faal.repositories;
//
//import com.grupo01.java6.faal.entities.Sla;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface SLATrackingRepository extends JpaRepository<Sla, Integer> {
//
//    // Find by ticket ID
//    Optional<Sla> findByTicketId(Integer ticketId);
//
//    // Find all active SLAs that are not paused and deadline is approaching (for notifications)
//    @Query("SELECT s FROM Sla s WHERE s.isPaused = false AND s.deadline BETWEEN :now AND :warningTime")
//    List<Sla> findApproachingDeadlines(
//            @Param("now") LocalDateTime now,
//            @Param("warningTime") LocalDateTime warningTime
//    );
//
//    // Find all breached SLAs
//    @Query("SELECT s FROM Sla s WHERE s.isPaused = false AND s.deadline < :now")
//    List<Sla> findBreachedSLAs(@Param("now") LocalDateTime now);
//
//    // Pause SLA timer
//    @Modifying
//    @Query("UPDATE Sla s SET s.isPaused = true, s.pauseTime = :pauseTime WHERE s.ticket.id = :ticketId")
//    void pauseSLA(@Param("ticketId") Integer ticketId, @Param("pauseTime") LocalDateTime pauseTime);
//
//    // Resume SLA timer
//    @Modifying
//    @Query("UPDATE SLATracking s SET s.isPaused = false, " +
//            "s.pausedDuration = s.pausedDuration.plus(Duration.between(s.pauseTime, :resumeTime)) " +
//            " WHERE s.ticket.id = :ticketId")
//    void resumeSLA(@Param("ticketId") Integer ticketId, @Param("resumeTime") LocalDateTime resumeTime);
//
//    // Update deadline
//    @Modifying
//    @Query("UPDATE Sla s SET s.deadline = :newDeadline WHERE s.ticket.id = :ticketId")
//    void updateDeadline(@Param("ticketId") Integer ticketId, @Param("newDeadline") LocalDateTime newDeadline);
//
//    // Complete SLA (when ticket is closed)
//    @Modifying
//    @Query("UPDATE Sla s SET s.deadline = :completionTime WHERE s.ticket.id = :ticketId")
//    void completeSLA(@Param("ticketId") Integer ticketId, @Param("completionTime") LocalDateTime completionTime);
//}