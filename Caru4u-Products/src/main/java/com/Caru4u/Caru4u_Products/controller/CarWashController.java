package com.Caru4u.Caru4u_Products.controller;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceResponse;
import com.Caru4u.Caru4u_Products.services.CarWashServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/car-wash")
@CrossOrigin(origins = "http://localhost:3000")
public class CarWashController {


    private  final CarWashServices carWash;

    public CarWashController(CarWashServices carWash) {
        this.carWash = carWash;
    }

    @GetMapping("/plans")
    public ResponseEntity<CarWashPlanResponse> getPlans(@RequestParam(defaultValue = "HATCHBACK") String valueType){
        return  ResponseEntity.ok(carWash.getPlans(valueType));
    }

    @GetMapping("/validate")
    public ResponseEntity<PackagePriceResponse> validatePrice(

            @RequestParam Long productId,
            @RequestParam Long packageId,
            @RequestParam Long vehicleTypeId,
            @RequestParam Long frequencyId) {

        PackagePriceResponse response =
                carWash.validatePrice(
                        productId,
                        packageId,
                        vehicleTypeId,
                        frequencyId
                );
        return ResponseEntity.ok(response);
    }
}
