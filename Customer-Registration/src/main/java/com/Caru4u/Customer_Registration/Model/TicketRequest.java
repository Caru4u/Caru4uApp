package com.Caru4u.Customer_Registration.Model;

import lombok.Data;

@Data
public class TicketRequest {
    private Long customerId;
    private Long bookingId;
    private String issueDescription;
}
