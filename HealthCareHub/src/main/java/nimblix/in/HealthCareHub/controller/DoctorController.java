package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.request.DoctorAvailabilityRequest;
import nimblix.in.HealthCareHub.request.DoctorRegistrationRequest;
import nimblix.in.HealthCareHub.response.DoctorAvailabilityResponse;
import nimblix.in.HealthCareHub.response.DoctorProfileResponse;
import nimblix.in.HealthCareHub.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/register")
    public String registerDoctor(@RequestBody DoctorRegistrationRequest request) {
        return doctorService.registerDoctor(request);

    }

    @GetMapping("/getDoctorDetails/{doctorId}/{hospitalId}")
    public ResponseEntity<?> getDoctorDetails(@PathVariable Long doctorId,
                                              @PathVariable Long hospitalId) {
        return doctorService.getDoctorDetails(doctorId, hospitalId);
    }

    @PutMapping("/updateDoctorDetails")
     public String updateDoctorDetails(@RequestBody DoctorRegistrationRequest request){
        return doctorService.updateDoctorDetails(request);

    }

    @GetMapping("/{doctorId}/profile")
    public ResponseEntity<Map<String, Object>> getDoctorProfile(
            @PathVariable Long doctorId) {

        DoctorProfileResponse response = doctorService.getDoctorProfile(doctorId);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put(HealthCareConstants.STATUS, HttpStatus.OK.value());
        result.put(HealthCareConstants.MESSAGE, HealthCareConstants.DOCTOR_PROFILE_FETCHED_SUCCESSFULLY);
        result.put(HealthCareConstants.DATA, response);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteDoctorDetails")
    public String deleteDoctorDetails(@RequestParam Long doctorId){
        return doctorService.deleteDoctorDetails(doctorId);
    }

    @PostMapping("/{doctorId}/timeslots")
    public ResponseEntity<Map<String, Object>> addTimeSlot(
            @PathVariable Long doctorId,
            @RequestBody DoctorAvailabilityRequest request) {

        request.setDoctorId(doctorId);
        DoctorAvailabilityResponse response =
                doctorService.addDoctorTimeSlot( request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put(HealthCareConstants.STATUS, HttpStatus.CREATED.value());
        result.put(HealthCareConstants.MESSAGE, HealthCareConstants.TIME_SLOT_ADDED_SUCCESSFULLY );
        result.put(HealthCareConstants.DATA, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{doctorId}/timeslots/{slotId}")
    public ResponseEntity<Map<String, Object>> updateTimeSlot(
            @PathVariable Long doctorId,
            @PathVariable Long slotId,
            @RequestBody DoctorAvailabilityRequest request) {
        request.setDoctorId(doctorId);
        request.setSlotId(slotId);

        DoctorAvailabilityResponse response =
                doctorService.updateDoctorTimeSlot(request);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put(HealthCareConstants.STATUS, HttpStatus.OK.value());
        result.put(HealthCareConstants.MESSAGE, HealthCareConstants.TIME_SLOT_UPDATED_SUCCESSFULLY );
        result.put(HealthCareConstants.DATA, response);

        return ResponseEntity.ok(result);
    }
}