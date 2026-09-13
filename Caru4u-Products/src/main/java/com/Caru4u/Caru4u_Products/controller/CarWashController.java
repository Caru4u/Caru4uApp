package com.Caru4u.Caru4u_Products.controller;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.services.CarWash;
import com.Caru4u.Caru4u_Products.services.CarWashService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/car-wash")
@CrossOrigin(origins = "http://localhost:3000")
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
