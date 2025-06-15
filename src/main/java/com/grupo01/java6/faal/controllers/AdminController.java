package com.grupo01.java6.faal.controllers;

import com.grupo01.java6.faal.dtos.TicketingDTO;
import com.grupo01.java6.faal.services.PriorityService;
import com.grupo01.java6.faal.services.impl.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
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

    @GetMapping("/admin")
    public String showAdminTickets(Model model, Authentication authentication) {
        log.info("Admin access attempted by: {}", authentication.getName());
        model.addAttribute("ticketingDTO", new TicketingDTO());
        model.addAttribute("ticketsList", ticketingService.findAll());
        return "admin-tickets";
    }
//
//
    //// aprove a Ticket  usuarioAprobador esta en el ux
//
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
    //
//    // update ticket for admin ( nombre asunto descreption tipo ticket )
//    @PutMapping("/{id}")
//    public ResponseEntity<TicketingDTO> updateTicket (@PathVariable Integer id,
//                                                     @Valid @RequestBody TicketingDTO ticketDTO) {
//        TicketingDTO updatedTicket = ticketingService.updateTicket(id, ticketDTO);
//        return ResponseEntity.ok(updatedTicket);
//    }
//
//
//    // Guardar el ticket
//
    @PostMapping("/save")
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
//    // restfull pethod
//// cerrar el ticket


//// Close a ticket with reason


//// Methodo cerrar el ticket
//@PostMapping("/{ticketId}/close")
//public ResponseEntity<TicketingDTO> closeTicket(
//        @PathVariable Integer ticketId, // extraer la id del url
//        @RequestParam Integer loginId) {
//
//    TicketingDTO closedTicket = ticketingService.closeTicket(ticketId, loginId);
//    return ResponseEntity.ok(closedTicket);
//}


    //// my other implimentation if i unify the html ////

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
