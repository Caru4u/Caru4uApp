package com.Caru4u.employee_registration.controller;

import com.Caru4u.employee_registration.model.EmployeeModel;
import com.Caru4u.employee_registration.service.EmployeeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> saveEmployee(

            @RequestParam("empFirstName")
            String empFirstName,

            @RequestParam("empLastName")
            String empLastName,

            @RequestParam("empDateOfBirth")
            String empDateOfBirth,

            @RequestParam("empMobileNumber")
            String empMobileNumber,

            @RequestParam("empMailId")
            String empMailId,

            @RequestParam(value = "empEmergencyNumber", required = false)
            String empEmergencyNumber,

            @RequestParam(value = "empAddress", required = false)
            String empAddress,

            @RequestParam("empAdharNumber")
            String empAdharNumber,

            @RequestParam("empPanNumber")
            String empPanNumber,

            @RequestParam(value = "empGender", required = false)
            String empGender,

            @RequestParam("empBankAccountNumber")
            String empBankAccountNumber,

            @RequestParam(value = "empIfscCode", required = false)
            String empIfscCode,

            @RequestParam(value = "empJoiningDate", required = false)
            String empJoiningDate,

            @RequestParam(value = "empPhoto", required = false)
            MultipartFile empPhoto,

            @RequestParam(value = "empAdharPhoto", required = false)
            MultipartFile empAdharPhoto,

            @RequestParam(value = "empPanPhoto", required = false)
            MultipartFile empPanPhoto,

            @RequestParam(value = "empBankPassBookPhoto", required = false)
            MultipartFile empBankPassBookPhoto
    ) {

        try {

            EmployeeModel employee = new EmployeeModel();

            employee.setEmpFirstName(empFirstName);
            employee.setEmpLastName(empLastName);

            employee.setEmpDateOfBirth(
                    LocalDate.parse(empDateOfBirth)
            );

            employee.setEmpMobileNumber(empMobileNumber);
            employee.setEmpMailId(empMailId);

            employee.setEmpEmergencyNumber(empEmergencyNumber);
            employee.setEmpAddress(empAddress);

            employee.setEmpAdharNumber(empAdharNumber);
            employee.setEmpPanNumber(empPanNumber);

            employee.setEmpGender(empGender);

            employee.setEmpBankAccountNumber(
                    empBankAccountNumber
            );

            employee.setEmpIfscCode(empIfscCode);

            if (empJoiningDate != null &&
                    !empJoiningDate.isBlank()) {

                employee.setEmpJoiningDate(
                        LocalDate.parse(empJoiningDate)
                );
            }

            validateEmployeePhoto(empPhoto);

            validateDocument(empAdharPhoto);
            validateDocument(empPanPhoto);
            validateDocument(empBankPassBookPhoto);

            if (empPhoto != null && !empPhoto.isEmpty()) {
                employee.setEmpPhoto(empPhoto.getBytes());
            }

            if (empAdharPhoto != null &&
                    !empAdharPhoto.isEmpty()) {

                employee.setEmpAdharPhoto(
                        empAdharPhoto.getBytes()
                );
            }

            if (empPanPhoto != null &&
                    !empPanPhoto.isEmpty()) {

                employee.setEmpPanPhoto(
                        empPanPhoto.getBytes()
                );
            }

            if (empBankPassBookPhoto != null &&
                    !empBankPassBookPhoto.isEmpty()) {

                employee.setEmpBankPassBookPhoto(
                        empBankPassBookPhoto.getBytes()
                );
            }

            String response =
                    service.saveEmployee(employee);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                            "Invalid request: "
                                    + e.getMessage()
                    );
        }
    }

    private void validateEmployeePhoto(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {
            return;
        }

        validateFileSize(file);

        String contentType =
                file.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException(
                    "Invalid employee photo"
            );
        }

        if (!contentType.equals("image/jpeg")
                && !contentType.equals("image/png")) {

            throw new IllegalArgumentException(
                    "Employee photo must be JPG, JPEG or PNG"
            );
        }
    }

    private void validateDocument(
            MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {
            return;
        }

        validateFileSize(file);

        String contentType =
                file.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException(
                    "Invalid document"
            );
        }

        if (!contentType.equals("image/jpeg")
                && !contentType.equals("image/png")
                && !contentType.equals("application/pdf")) {

            throw new IllegalArgumentException(
                    "Only JPG, JPEG, PNG and PDF files are allowed"
            );
        }
    }

    private void validateFileSize(
            MultipartFile file
    ) {

        if (file.getSize() > MAX_FILE_SIZE) {

            throw new IllegalArgumentException(
                    "File size must not exceed 5 MB"
            );
        }
    }
    @GetMapping("/{id}/adhar-photo")
    public ResponseEntity<byte[]> getAdharPhoto(
            @PathVariable Long id) {

        EmployeeModel employee =
                service.getEmployeeById(id);

        byte[] photo = employee.getEmpAdharPhoto();

        if (photo == null || photo.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(photo);
    }
}