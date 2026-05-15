package com.Caru4u.Customer_Registration.Controller;

import com.Caru4u.Customer_Registration.Model.CallbackRequest;
import com.Caru4u.Customer_Registration.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth/Customer")
public class ContactController {
    @Autowired
    private ContactService service;

    @PostMapping("/request-callback")
    public ResponseEntity<String> requestCallback(@RequestBody CallbackRequest request) {
        String result = service.saveAndNotify(request);
        return ResponseEntity.ok(result);
    }
}
