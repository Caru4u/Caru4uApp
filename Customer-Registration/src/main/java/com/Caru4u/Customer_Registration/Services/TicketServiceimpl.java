package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.CustomerRegistorRepository;
import com.Caru4u.Customer_Registration.Dao.SupportTicketRepository;
import com.Caru4u.Customer_Registration.Model.SupportTicket;
import com.Caru4u.Customer_Registration.Model.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceimpl implements TicketService {

    @Autowired
    private SupportTicketRepository ticketRepo;

    @Autowired
    private CustomerRegistorRepository customerRepo;


    @Autowired
    private EmailService emailService;

    private static final String ADMIN_EMAIL = "shinewash360@gmail.com";

    public TicketResponse createTicket(Long customerId, Long bookingId, String issue) {

        // ✅ 1. Create Ticket
        SupportTicket ticket = new SupportTicket();
        ticket.setCustomerId(customerId);
        ticket.setBookingId(bookingId);
        ticket.setIssueDescription(issue);
        ticket.setStatus("OPEN");
        ticket.setCreatedAt(LocalDateTime.now());

        // ✅ 2. Save
        SupportTicket savedTicket = ticketRepo.save(ticket);

        // ✅ 3. Email Content
        String msg = "Support Ticket Created\n\n"
                + "Ticket ID: TCK-" + savedTicket.getTicketId() + "\n"
                + "Status: " + savedTicket.getStatus() + "\n"
                + "Issue: " + savedTicket.getIssueDescription();

        // ✅ 4. Fetch Email
        String customerEmail = customerRepo.getCustomerEmail(customerId);

        // ✅ 5. Send Email
        try {
            emailService.sendMail(customerEmail, "Ticket Created", msg);
            emailService.sendMail(ADMIN_EMAIL, "New Ticket Alert", msg);
        } catch (Exception e) {
            System.out.println("Email failed: " + e.getMessage());
        }

        // ✅ 6. RETURN SUCCESS RESPONSE
        return new TicketResponse(
                "Ticket created successfully",
                savedTicket.getTicketId(),
                savedTicket.getStatus()
        );
    }
}