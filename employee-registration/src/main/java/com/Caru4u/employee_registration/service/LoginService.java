package com.Caru4u.employee_registration.service;

import com.Caru4u.employee_registration.model.ChangeMobileRequest;
import com.Caru4u.employee_registration.model.LoginRequest;

public interface LoginService {
     String login(LoginRequest request);
     String changeMobileNumber(ChangeMobileRequest request);
}
