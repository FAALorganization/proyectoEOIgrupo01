package com.grupo01.java6.faal.controllers;

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
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/ticket")
@Slf4j
public class TicketController {

    private final TicketService ticketingService;

    public TicketController(TicketService ticketingService) {
        this.ticketingService = ticketingService;
    }
    // visitor panel //


    @PostMapping("/submit")
    public String createTicket(
            @Valid @ModelAttribute TicketingDTO ticketingDTO,
            BindingResult bindingResult,
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            log.warn("Validation errors in ticket creation: {}", bindingResult.getAllErrors());
            model.addAttribute("ticketingDTO", ticketingDTO);
            return "ticket";
        }

        try {
            String userEmail = authentication.getName();
            TicketingDTO created = ticketingService.createTicket( ticketingDTO, userEmail);

            redirectAttributes.addFlashAttribute("successMessage",
                    "Ticket creado exitosamente con ID: " + created.getId());

            return "redirect:/ticket/list";

        } catch (Exception e) {
            log.error("Error creating ticket: ", e);
            model.addAttribute("errorMessage", "Error al crear el ticket: " + e.getMessage());
            model.addAttribute("ticketingDTO", ticketingDTO);
            return "ticket";
        }
    }
//Gets all tickets submitted by the current user.
    @GetMapping("/list")
    public String listTickets(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        List<TicketingDTO> tickets = ticketingService.findUserTickets(userEmail);
        log.info("User {} requested their ticket list", userEmail);

        model.addAttribute("tickets", tickets);
        model.addAttribute("userEmail", userEmail);

        return "ticket-list"; // View: ticket-list.html
    }


    ///  admin section ///
    /// mostrar ///

    @GetMapping("/admin")
    public String showAdminTickets(Model model, Authentication authentication) {
        log.info("Admin access attempted by: {}", authentication.getName());
        model.addAttribute("ticketingDTO", new TicketingDTO());
        model.addAttribute("ticketsList", ticketingService.findAll());
        return "admin-tickets";
    }


// aprove a Ticket  usuarioAprobador esta en el ux
    @PostMapping("/approve/{id}")
    public String approveTicket(
            @PathVariable Integer id,@RequestParam("usuarioAprobador") String usuarioAprobador,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        // just to check
        log.info("Quien ha Aprobado el Ticket: {}", usuarioAprobador);

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


    // guardar el ticket

    @PostMapping("/tickets/save")
    public String saveTicket(@Valid @ModelAttribute TicketingDTO ticketingDTO,
                             BindingResult result, @RequestParam("telefono") String telefono,
                             @RequestParam("correoGerente") String correoGerente, @RequestParam("fechaqueja") LocalDate fechaqueja,
                             Model model,
                             Authentication authentication) {

        if (result.hasErrors()) {
            model.addAttribute("ticketsList", ticketingService.findAll());
            return "admin-tickets";
        }
        // not saved in DTO
        /// to do : modify later the entities
        log.info("Teléfono: {}", telefono);
        log.info("Correo del gerente: {}", correoGerente);
        log.info("Fecha queja : {}", fechaqueja);

        String userEmail = authentication.getName(); //  current user's email
        ticketingService.save(ticketingDTO, userEmail);

        return "redirect:/tickets/list";
    }

    // Update the ticket Admin

/*
    @GetMapping("/create-form")
    public String showCreateForm(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        List<TicketingDTO> userTickets = ticketingService.findUserTickets(userEmail);
        model.addAttribute("userTickets", userTickets);  // for historial dropdown
        return "ticket"; // the view name for your form page
    }

}*/

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
}
