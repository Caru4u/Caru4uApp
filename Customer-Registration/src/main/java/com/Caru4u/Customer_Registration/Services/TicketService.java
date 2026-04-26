package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Model.SupportTicket;
import com.Caru4u.Customer_Registration.Model.TicketResponse;

public interface TicketService {
    public TicketResponse createTicket(Long customerId, Long bookingId, String issue);
}
