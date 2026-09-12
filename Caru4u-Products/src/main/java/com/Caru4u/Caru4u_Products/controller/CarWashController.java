package com.Caru4u.Caru4u_Products.controller;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.services.CarWash;
import com.Caru4u.Caru4u_Products.services.CarWashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/car-wash")
public class CarWashController {


    private  final CarWash carWash;

    public CarWashController(CarWash carWash) {
        this.carWash = carWash;
    }

    @GetMapping("/plans")
    public ResponseEntity<CarWashPlanResponse> getPlans(@RequestParam(defaultValue = "HATCHBACK") String valueType){
        return  ResponseEntity.ok(carWash.getPlans(valueType));
    }
}
