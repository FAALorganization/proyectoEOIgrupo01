package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.services.PriorityService;
import com.grupo01.java6.faal.services.impl.TicketService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/tickets")
@PreAuthorize("hasRole('ADMIN')")

public class AdminController {
    private final TicketService ticketingService;
    private final PriorityService priorityService;

    public AdminController(TicketService ticketService, PriorityService priorityService) {
        this.ticketingService = ticketService;
        this.priorityService = priorityService;
    }
    ///  admin section ///
    /// mostrar ///

    ///  admin section ///
    /// mostrar ///

    @GetMapping
    public String showAdminTickets(Model model, Authentication authentication) {
        log.info("Admin access attempted by: {}", authentication.getName());
        model.addAttribute("ticketingDTO", new TicketingDTO());
        model.addAttribute("priorities", priorityService.getAllPriorityValues());
        if (authentication != null) {
            List<TicketingDTO> tickets = ticketingService.findUserTickets(authentication.getName());
            model.addAttribute("tickets", tickets);
        }
        return "admin-tickets";
    }
// crear el ticket
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
        return "admin-tickets";
    }}

//
    //// aprove a Ticket  usuarioAprobador esta en el ux
//
//    @PostMapping("/approve/{id}")
//    public String approveTicket(
//            @PathVariable Integer id,@RequestParam("usuarioAprobador") String usuarioAprobador,
//            Authentication authentication,
//            RedirectAttributes redirectAttributes) {
//        // just to check
//        log.info("Quien ha Aprobado el Ticket: {}", usuarioAprobador);
//
//        try {
//            String approvedEmail = authentication.getName();
//            ticketingService.approveTicket(id, approvedEmail);
//            redirectAttributes.addFlashAttribute("successMessage", "Ticket aprobado exitosamente");
//        } catch (Exception e) {
//            log.error("Error approving ticket: ", e);
//            redirectAttributes.addFlashAttribute("errorMessage", "Error al aprobar ticket: " + e.getMessage());
//        }
//
//        return "redirect:/admin/tickets";
//    }

// reopne

    @PostMapping("/reopen/{id}")
    public String reopenTicket(
            @PathVariable Integer id,
            @RequestParam(required = false) String reason,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        try {
            String reopenedBy = authentication.getName();
            ticketingService.reopenTicket(id, reopenedBy, reason);
            redirectAttributes.addFlashAttribute("successMessage", "Ticket reabierto exitosamente");
        } catch (Exception e) {
            log.error("Error reopening ticket: ", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Error al reabrir ticket: " + e.getMessage());
        }

        return "redirect:/admin/tickets";
    }

//    // Guardar el ticket

//    @PostMapping("/save")
//    public String saveTicket(@Valid @ModelAttribute TicketingDTO ticketingDTO,
//                             BindingResult result, @RequestParam("telefono") String telefono,
//                             @RequestParam("correoGerente") String correoGerente, @RequestParam("fechaqueja") LocalDate fechaqueja,
//                             Model model,
//                             Authentication authentication) {
//
//        if (result.hasErrors()) {
//            model.addAttribute("ticketsList", ticketingService.findAll());
//            return "admin-tickets";
//        }
//
//        /// to do : modify later the entities
//        log.info("Teléfono: {}", telefono);
//        log.info("Correo del gerente: {}", correoGerente);
//        log.info("Fecha queja : {}", fechaqueja);
//
//        String userEmail = authentication.getName(); //  current user's email
//        ticketingService.save(ticketingDTO, userEmail);
//
//        return "redirect:/tickets/list";
//    }
//    // later implement restfull method apart
//// cerrar el ticket

//// Close a ticket with reason

//// Methodo cerrar el ticket
@PostMapping("/{ticketId}/close")
public ResponseEntity<TicketingDTO> closeTicket(
        @PathVariable Integer ticketId, // extraer la id del url
        @RequestParam Integer loginId) {

    TicketingDTO closedTicket = ticketingService.closeTicket(ticketId, loginId);
    return ResponseEntity.ok(closedTicket);
}
/// / update id
    @PostMapping("/update/{id}")
    public String updateTicket(
            @PathVariable Integer id,
            @Valid @ModelAttribute TicketingDTO ticketingDTO,
            BindingResult result,
            Model model,
            Authentication authentication) {

        if (result.hasErrors()) {
            model.addAttribute("priorities", priorityService.getAllPriorityValues());
            model.addAttribute("ticketsList", ticketingService.findAll());
            return "admin-tickets";
        }

        String updatedBy = authentication.getName();
        ticketingService.updateTicket(id, ticketingDTO, updatedBy);
        return "redirect:/admin/tickets";
    }

















    //// my other implimentation if i decide to unify the html ////

/*
    @GetMapping("/create-form")
    public String showCreateForm(Model model, Authentication authentication) {
        String userEmail = authentication.getName();
        List<TicketingDTO> userTickets = ticketingService.findUserTickets(userEmail);
        model.addAttribute("userTickets", userTickets);  // for historial dropdown
        return "ticket"; // the view name for your form page
    }

}*/

// ask if i can do a @Restcontolor apart

//    // update ticket for admin ( nombre asunto descreption tipo ticket )
//    @PutMapping("/{id}")
//    public ResponseEntity<TicketingDTO> updateTicket (@PathVariable Integer id,
//                                                     @Valid @RequestBody TicketingDTO ticketDTO) {
//        TicketingDTO updatedTicket = ticketingService.updateTicket(id, ticketDTO);
//        return ResponseEntity.ok(updatedTicket);
//    }
//
//

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
