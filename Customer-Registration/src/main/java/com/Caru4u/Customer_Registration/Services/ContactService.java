package com.Caru4u.Customer_Registration.Services;

import com.Caru4u.Customer_Registration.Model.CallbackRequest;

public interface ContactService {

     String saveAndNotify(CallbackRequest request);
}
