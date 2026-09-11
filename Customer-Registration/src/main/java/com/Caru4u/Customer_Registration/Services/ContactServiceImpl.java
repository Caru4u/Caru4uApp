package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Dao.CallbackRepository;
import com.Caru4u.Customer_Registration.Model.CallbackEntity;
import com.Caru4u.Customer_Registration.Model.CallbackRequest;
import com.Caru4u.Customer_Registration.Utails.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private CallbackRepository repository;

    @Autowired
    private JavaMailSender mailSender;

    public String saveAndNotify(CallbackRequest request){

            // Save to DB
            CallbackEntity entity = new CallbackEntity();
            entity.setName(request.getName());
            entity.setEmail(request.getEmail());
            entity.setPhone(request.getPhone());
            entity.setMessage(request.getMessage());
            repository.save(entity);

            // Send email notification
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo("caru4uadmin@gmail.com"); // replace with your support email
            mail.setSubject("New Callback Request");
            mail.setText("Name: " + request.getName() +
                    "\nEmail: " + request.getEmail() +
                    "\nPhone: " + request.getPhone() +
                    "\nMessage: " + request.getMessage());
            mailSender.send(mail);

        // 2. Send confirmation to customer
        SimpleMailMessage customerMail = new SimpleMailMessage();
        customerMail.setTo(request.getEmail()); // customer’s email
        customerMail.setSubject("We received your callback request");
        customerMail.setText("Dear " + request.getName() + ",\n\n" +
                "Thank you for reaching out to Caru4u. We have received your request and our team will contact you shortly.\n\n" +
                "Details you submitted:\n" +
                "Phone: " + request.getPhone() + "\n" +
                "Message: " + request.getMessage() + "\n\n" +
                "Best regards,\nCaru4u Support Team");
        mailSender.send(customerMail);
        return Constants.Your_Call_Back_Request;
    }


}
