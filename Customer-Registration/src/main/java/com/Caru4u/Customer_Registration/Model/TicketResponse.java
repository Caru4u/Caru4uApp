package com.Caru4u.Customer_Registration.Model;

public class TicketResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String message;
    private Long ticketId;
    private String status;

    public TicketResponse(String message, Long ticketId, String status) {
        this.message = message;
        this.ticketId = ticketId;
        this.status = status;
    }
}
