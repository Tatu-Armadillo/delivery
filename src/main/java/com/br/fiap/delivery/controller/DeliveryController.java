package com.br.fiap.delivery.controller;

import com.br.fiap.delivery.module.Delivery;
import com.br.fiap.delivery.module.Location;
import com.br.fiap.delivery.record.DeliveryDTO;
import com.br.fiap.delivery.record.LocationDTO;
import com.br.fiap.delivery.service.delivery.DeliveryAssignmentService;
import com.br.fiap.delivery.service.tracking.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/delivery")
@Tag(name = "Delivery", description = "Delivery related endpoint")
public class DeliveryController {

    private final DeliveryAssignmentService deliveryAssignmentService;
    private final TrackingService trackingService;

    @Autowired
    public DeliveryController(DeliveryAssignmentService deliveryAssignmentService, TrackingService trackingService) {
        this.deliveryAssignmentService = deliveryAssignmentService;
        this.trackingService = trackingService;
    }

    @PostMapping("/assign")
    @Operation(summary = "Assign a new delivery",
            description = "Assigns a new delivery to an available driver based on the provided order ID and destination address.")
    public ResponseEntity<DeliveryDTO> assignDelivery(@RequestParam Long orderId, @RequestParam String destinationAddress) {
        Delivery delivery = deliveryAssignmentService.assignDelivery(orderId, destinationAddress);
        return ResponseEntity.ok(DeliveryDTO.toDto(delivery));
    }

    @PutMapping("/track/{deliveryId}")
    @Operation(summary = "Update delivery location",
            description = "Updates the current location of a delivery in progress based on the delivery ID, latitude, and longitude.")
    public ResponseEntity<LocationDTO> updateLocation(@PathVariable Long deliveryId,
                                                      @RequestParam double latitude, @RequestParam double longitude) {
        Location location = trackingService.updateLocation(deliveryId, latitude, longitude);
        return ResponseEntity.ok(LocationDTO.toDto(location));
    }

    @GetMapping("/track/{deliveryId}")
    @Operation(summary = "Get delivery tracking locations",
            description = "Retrieves all tracking locations for a specific delivery, based on the delivery ID.")
    public ResponseEntity<List<LocationDTO>> getDeliveryLocations(@PathVariable Long deliveryId) {
        List<Location> locations = trackingService.getDeliveryLocations(deliveryId);
        List<LocationDTO> locationDTOs = locations.stream()
                .map(LocationDTO::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(locationDTOs);
    }
}