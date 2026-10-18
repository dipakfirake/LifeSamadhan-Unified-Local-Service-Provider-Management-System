package com.lifesamadhan.api.controller;

import com.lifesamadhan.api.model.ServiceRequest;
import com.lifesamadhan.api.model.ServiceAssignment;
import com.lifesamadhan.api.model.Rating;
import com.lifesamadhan.api.model.User;
import com.lifesamadhan.api.model.CustomerProfile;
import com.lifesamadhan.api.service.CustomerService;
import com.lifesamadhan.api.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
public class CustomerController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final JwtUtils jwtUtil;
    private final com.lifesamadhan.api.dto.DTOMapper dtoMapper;

    public CustomerController(CustomerService customerService, JwtUtils jwtUtil, com.lifesamadhan.api.dto.DTOMapper dtoMapper) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
        this.dtoMapper = dtoMapper;
    }

    private Long parseLong(Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof Number)
            return ((Number) obj).longValue();
        try {
            return Long.valueOf(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/providers")
    public ResponseEntity<?> getAvailableProviders(
            @RequestParam("serviceId") Long serviceId,
            @RequestParam("locationId") Long locationId,
            HttpServletRequest httpRequest) {
        try {
            
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid authorization header"));
            }

            List<com.lifesamadhan.api.dto.ProviderDTO> providers = customerService.findAvailableProviders(serviceId,
                    locationId);
            return ResponseEntity.ok(providers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/request")
    public ResponseEntity<?> createServiceRequest(@RequestBody Map<String, Object> requestData,
            HttpServletRequest httpRequest) {
        log.info(">>>> RECEIVED CREATE SERVICE REQUEST. Payload: {}", requestData);
        try {
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Request missing valid Bearer token");
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid authorization header"));
            }

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            log.info("Auth User: {}", username);

            
            User user = customerService.getUserByEmail(username);
            log.info("Database User ID: {}", user.getId());

            CustomerProfile profile = customerService.getCustomerProfileByUserId(user.getId());

            if (profile == null) {
                log.info("Creating new consumer profile for user {}", user.getId());
                profile = CustomerProfile.builder()
                        .userId(user.getId())
                        .locationId(1L) 
                        .build();
                profile = customerService.saveCustomerProfile(profile);
            }

            Long providerId = parseLong(requestData.get("providerId"));
            Long catId = parseLong(requestData.get("serviceCategoryId"));

            
            Long serviceId = parseLong(requestData.get("serviceId"));
            if (serviceId == null && catId != null) {
                serviceId = customerService.resolveServiceId(catId, providerId);
            }

            if (serviceId == null) {
                log.warn("Failed to resolve serviceId for payload: {}", requestData);
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Service ID or valid Category ID is required"));
            }

            Long locationId = parseLong(requestData.get("locationId"));
            if (locationId == null)
                locationId = profile.getLocationId();

            
            String serviceAddress = (String) requestData.get("serviceAddress");
            Double amount = null;
            Object amt = requestData.get("amount");
            if (amt instanceof Number)
                amount = ((Number) amt).doubleValue();

            java.time.LocalDateTime scheduledDate = null;
            try {
                String dateStr = (String) requestData.get("scheduledDate");
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    dateStr = dateStr.trim();
                    if (dateStr.endsWith("Z")) {
                        java.time.Instant instant = java.time.Instant.parse(dateStr);
                        scheduledDate = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault());
                    } else if (dateStr.contains("+") || (dateStr.length() > 10 && dateStr.substring(10).contains("-"))) {
                        java.time.ZonedDateTime zdt = java.time.ZonedDateTime.parse(dateStr);
                        scheduledDate = zdt.withZoneSameInstant(java.time.ZoneId.systemDefault()).toLocalDateTime();
                    } else {
                        if (dateStr.length() == 16) {
                            dateStr = dateStr + ":00";
                        }
                        scheduledDate = java.time.LocalDateTime.parse(dateStr);
                    }
                }
            } catch (Exception e) {
                log.warn("Failed to parse scheduledDate: {}", requestData.get("scheduledDate"), e);
            }

            if (scheduledDate == null) {
                log.warn("Missing or invalid scheduledDate in booking request: {}", requestData.get("scheduledDate"));
                return ResponseEntity.badRequest().body(Map.of("message", "A valid scheduled date and time is required."));
            }

            // Check if scheduled date/time is in the past (allow 2 minutes grace for network transit)
            if (scheduledDate.isBefore(java.time.LocalDateTime.now().minusMinutes(2))) {
                log.warn("Attempted to book service in the past: {} (Server now: {})", scheduledDate, java.time.LocalDateTime.now());
                return ResponseEntity.badRequest().body(Map.of("message", "Cannot book a service for a past date or time. Please select an upcoming date and time."));
            }

            String paymentStatus = (String) requestData.get("paymentStatus");
            if (paymentStatus == null)
                paymentStatus = "PENDING";

            
            ServiceRequest request = ServiceRequest.builder()
                    .customerId(profile.getId())
                    .serviceId(serviceId)
                    .locationId(locationId)
                    .serviceAddress(serviceAddress)
                    .scheduledDate(scheduledDate)
                    .amount(amount)
                    .paymentStatus(paymentStatus)
                    .status("PENDING")
                    .build();
            log.info("Triggering customerService.createServiceRequest with scheduledDate: {}...", scheduledDate);
            ServiceAssignment assignment = customerService.createServiceRequest(request, providerId);

            if (assignment == null) {
                log.warn("customerService.createServiceRequest returned null");
                return ResponseEntity.ok(Map.of("message", "No provider available in this location."));
            }

            log.info("Request successful. Assignment ID: {}", assignment.getId());
            return ResponseEntity.ok(dtoMapper.toLegacyAssignmentMap(assignment));

        } catch (IllegalArgumentException e) {
            log.warn("Validation error in createServiceRequest: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            log.error("CRITICAL ERROR in createServiceRequest: ", e);
            String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getName();
            return ResponseEntity.status(500).body(Map.of("message", "Error: " + msg));
        }
    }

    @GetMapping({ "/bookings", "/assignments", "/requests" })
    public ResponseEntity<?> getBookings(HttpServletRequest httpRequest) {
        try {
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid authorization header"));
            }

            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);

            List<ServiceAssignment> bookings = customerService.getCustomerBookings(username);

            
            List<Map<String, Object>> bookingMaps = bookings.stream()
                    .map(dtoMapper::toLegacyAssignmentMap)
                    .collect(java.util.stream.Collectors.toList());

            return ResponseEntity.ok(bookingMaps);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCustomerProfile(java.security.Principal principal) {
        try {
            String username = principal.getName();
            log.info("Fetching profile for: {}", username);
            var profile = customerService.getCustomerProfileData(username);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            log.error("Profile fetch error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateCustomerProfile(@RequestBody Map<String, Object> profileData,
            java.security.Principal principal) {
        try {
            log.info("=== UPDATE CUSTOMER PROFILE ===");
            log.info("Received profileData: {}", profileData);

            String username = (principal != null) ? principal.getName() : null;

            
            if (username == null && profileData.containsKey("email")) {
                username = (String) profileData.get("email");
                log.info("Using email from request body as fallback: {}", username);
            }

            if (username == null) {
                log.warn("No username/principal found in request");
                return ResponseEntity.status(401).body(Map.of("message", "Authentication required"));
            }

            log.info("Updating profile for: {}", username);
            var updatedProfile = customerService.updateCustomerProfileData(username, profileData);
            log.info("Update successful for {}", username);
            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            log.error("Profile update error for {}: {}", principal != null ? principal.getName() : "unknown",
                    e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/available-locations")
    @PreAuthorize("permitAll")
    public ResponseEntity<?> getLocations() {
        try {
            var locations = customerService.getAllActiveLocations();
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/cancel/{assignmentId}")
    public ResponseEntity<?> cancelRequest(@PathVariable("assignmentId") Long assignmentId) {
        try {
            ServiceAssignment assignment = customerService.cancelRequest(assignmentId);
            return ResponseEntity.ok(Map.of(
                    "message", "Service request cancelled successfully",
                    "assignment", dtoMapper.toLegacyAssignmentMap(assignment)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/rating/{id}")
    public ResponseEntity<?> submitRating(@PathVariable("id") Long id,
            @RequestBody Map<String, Object> ratingData) {
        try {
            log.info("Submitting rating for ID: {} with data: {}", id, ratingData);

            
            ServiceAssignment assignment = customerService.getAssignmentById(id);
            if (assignment == null) {
                assignment = customerService.getAssignmentByRequestId(id);
            }

            if (assignment == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("message", "Assignment or Request not found for ID: " + id));
            }

            
            Integer stars = 5;
            if (ratingData.containsKey("stars")) {
                stars = parseLong(ratingData.get("stars")).intValue();
            }

            String feedback = (String) ratingData.get("feedback");
            if (feedback == null) {
                feedback = (String) ratingData.get("comment"); 
            }

            Rating rating = Rating.builder()
                    .assignmentId(assignment.getId())
                    .stars(stars)
                    .feedback(feedback)
                    .build();

            Rating savedRating = customerService.submitRating(assignment.getId(), rating);
            return ResponseEntity.ok(savedRating);
        } catch (Exception e) {
            log.error("Rating submission error: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

}