package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.CreateTicketDTO;
import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.services.impl.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/ticket")
@Slf4j
public class TicketController {

    private final TicketService ticketingService;

    public TicketController(TicketService ticketingService) {
        this.ticketingService = ticketingService;
    }

    @PostMapping("/create")
    public String createTicket(
            @Valid @ModelAttribute CreateTicketDTO createTicketDTO,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors in ticket creation: {}", bindingResult.getAllErrors());
            model.addAttribute("createTicketDTO", createTicketDTO);
            return "ticket";
        }

        try {
            String userEmail = authentication.getName();
            TicketingDTO created = ticketingService.createTicketFromForm(createTicketDTO, userEmail);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Ticket creado exitosamente con ID: " + created.getId());

            return "redirect:/ticket/list";
        } catch (Exception e) {
            log.error("Error creating ticket: ", e);
            model.addAttribute("errorMessage", "Error al crear el ticket: " + e.getMessage());
            model.addAttribute("createTicketDTO", createTicketDTO);
            return "ticket";
        }
    }

    @GetMapping("/list")
    public String listTickets(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        List<TicketingDTO> tickets = ticketingService.findUserTickets(userEmail);
        log.info("User {} requested their ticket list", userEmail);
        log.info("User {} requested their ticket list", userEmail);

        model.addAttribute("tickets", tickets);
        model.addAttribute("userEmail", userEmail);

        return "ticket-list"; // View: ticket-list.html
    }

    @GetMapping("/admin")
    public String adminTickets(Model model, Authentication authentication) {
        log.info("Admin access attempted by: {}", authentication.getName());
        List<TicketingDTO> allTickets = ticketingService.findAll();
        log.debug("Retrieved {} tickets for admin view", allTickets.size());
        model.addAttribute("tickets", allTickets);
        return "admin-tickets"; // View: admin-tickets.html
    }

    @PostMapping("/approve/{id}")
    public String approveTicket(
            @PathVariable Integer id,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            String approvedEmail = authentication.getName();
            ticketingService.approveTicket(id, approvedEmail);
            redirectAttributes.addFlashAttribute("successMessage", "Ticket aprobado exitosamente");
        } catch (Exception e) {
            log.error("Error approving ticket: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al aprobar ticket: " + e.getMessage());
        }

        return "redirect:/ticket/admin";
    }

    @GetMapping("/create-form")
    public String showCreateForm(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        List<TicketingDTO> userTickets = ticketingService.findUserTickets(userEmail);
        model.addAttribute("userTickets", userTickets);  // for historial dropdown
        return "ticket"; // the view name for your form page
    }

}
// ask if i can do a @Restcontolor to get json
//    @GetMapping("/{id}")
//    public ResponseEntity<TicketingDTO> getById(@PathVariable Integer id) {
//        return ResponseEntity.ok(ticketingService.findById(id));
//    }
//    @GetMapping("/all")
//    public ResponseEntity<List<TicketingDTO>> findAll() {
//        return ResponseEntity.ok(ticketingService.findAll());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Integer id) {
//        ticketingService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/approve/{id}")
//    public String approveTicket(@PathVariable Integer id, HttpExchange.Principal principal) {
//        String email = principal.getName(); // Retrieves the logged-in user's email
//        ticketingService.approveTicket(id, email);
//        return "redirect:/ticket"; // Redirect to ticket list or confirmation page
//    }
//    @GetMapping("/aprobado")
//    public ResponseEntity<Page<TicketingDTO>> findByAprobado(@RequestParam Boolean aprobado, Pageable pageable) {
//        return ResponseEntity.ok(ticketingService.findByAprobado(aprobado, pageable));
//    }
//}
