package com.Caru4u.Caru4u_Products.controller;

import com.Caru4u.Caru4u_Products.dto.CarWashPlanResponse;
import com.Caru4u.Caru4u_Products.dto.PackagePriceResponse;
import com.Caru4u.Caru4u_Products.services.CarWash;
import com.Caru4u.Caru4u_Products.services.CarWashService;
import com.Caru4u.Caru4u_Products.services.PackagePriceService;
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
    private  final PackagePriceService packagePriceService;

    public CarWashController(CarWash carWash, PackagePriceService packagePriceService) {
        this.carWash = carWash;
        this.packagePriceService = packagePriceService;
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


    @GetMapping("/validate")
    public ResponseEntity<PackagePriceResponse> validatePackagePrice(
            @RequestParam Long productId,
            @RequestParam Long packageId,
            @RequestParam Long vehicleTypeId,
            @RequestParam Long frequencyId) {

        return ResponseEntity.ok(
                packagePriceService.validatePackagePrice(
                        productId,
                        packageId,
                        vehicleTypeId,
                        frequencyId
                )
        );
    }
}
