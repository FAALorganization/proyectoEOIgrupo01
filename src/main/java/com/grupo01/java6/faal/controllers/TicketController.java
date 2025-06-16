package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.services.PriorityService;
import com.grupo01.java6.faal.services.impl.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
//@RequestMapping("/ticket")
@Slf4j
public class TicketController {

    private final PriorityService priorityService;

    private final TicketService ticketingService;

    public TicketController(PriorityService priorityService, TicketService ticketingService) {
        this.priorityService = priorityService;
        this.ticketingService = ticketingService;
    }
                         // visitor panel  : can Admin also creat a ticket yes bcs admi is also an user //
    @GetMapping("/ticket")
    public String showTicketForm(Model model,Authentication authentication) {
        model.addAttribute("ticketForm", new TicketingDTO());
        model.addAttribute("priorities", priorityService.getAllPriorityValues());

        if (authentication != null) {
            List<TicketingDTO> tickets = ticketingService.findUserTickets(authentication.getName());
            model.addAttribute("tickets", tickets);
        }
        return "ticket";
    }

    @PostMapping("/ticket/submit")
    public String createTicket(
            @Valid @ModelAttribute TicketingDTO ticketingDTO,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model) {
        // Validate priority
        try {
            priorityService.validatePriority(ticketingDTO.getPrioridad());
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("prioridad", "invalid.priority", e.getMessage());
        }

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors in ticket creation: {}", bindingResult.getAllErrors());
            model.addAttribute("priorities", priorityService.getAllPriorityValues());
            return "ticket";
        }

        try {
            String userEmail = authentication.getName();
            TicketingDTO created = ticketingService.createTicket(ticketingDTO, userEmail);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Ticket creado exitosamente con ID: " + created.getId());

            return "redirect:/ticket/list";

        } catch (Exception e) {
            log.error("Error creating ticket: ", e);
            model.addAttribute("errorMessage", "Error al crear el ticket: " + e.getMessage());
            model.addAttribute("priorities", priorityService.getAllPriorityValues());
            model.addAttribute("ticketForm", ticketingDTO);
            return "ticket";
        }}


//Gets all tickets submitted by the current user.
    @GetMapping("ticket/list")
    public String listTickets(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        List<TicketingDTO> tickets = ticketingService.findUserTickets(userEmail);
        log.info("User {} requested their ticket list", userEmail);

        model.addAttribute("tickets", tickets);
        model.addAttribute("userEmail", userEmail);

        return "ticket-list"; // View: ticket-list.html
    }

}
