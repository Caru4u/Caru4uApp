package com.Caru4u.Customer_Registration.Controller;

import com.Caru4u.Customer_Registration.Model.SupportTicket;
import com.Caru4u.Customer_Registration.Model.TicketRequest;
import com.Caru4u.Customer_Registration.Model.TicketResponse;
import com.Caru4u.Customer_Registration.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class CaruCustomerSupportTicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest request) {

        TicketResponse response = ticketService.createTicket(
                request.getCustomerId(),
                request.getBookingId(),
                request.getIssueDescription()
        );

        return ResponseEntity.ok(response);
    }
}
