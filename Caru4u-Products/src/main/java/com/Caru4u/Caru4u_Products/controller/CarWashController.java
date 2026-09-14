package com.Caru4u.Caru4u_Products.controller;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceUpdateRequest;
import com.Caru4u.Caru4u_Products.dto.ProductResponse;
import com.Caru4u.Caru4u_Products.services.CarWashServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/car-wash")
@CrossOrigin(origins = "http://localhost:3000")
public class CarWashController {


    private  final CarWashServices carWashServices;

    public CarWashController(CarWashServices carWashServices) {
        this.carWashServices = carWashServices;
    }

    @GetMapping("/plans")
    public ResponseEntity<CarWashPlanResponse> getPlans(@RequestParam(defaultValue = "HATCHBACK") String valueType){
        return  ResponseEntity.ok(carWashServices.getPlans(valueType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackagePriceResponse> upDatePlans(@PathVariable Long id, @RequestBody PackagePriceUpdateRequest packagePriceUpdateRequest){
     return ResponseEntity.ok(carWashServices.updatePrice(id,packagePriceUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrice(@PathVariable Long id){
         carWashServices.deletePrice(id);
         return ResponseEntity.ok("Package Price Deleted Successfully");
    }

}
