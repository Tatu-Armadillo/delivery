package com.br.fiap.delivery.record;

import com.br.fiap.delivery.module.Driver;

public record DriverDTO(Long id,
                        String name,
                        String vehicle,
                        String phoneNumber,
                        Boolean available) {

    public static DriverDTO toDto(Driver driver) {
        return new DriverDTO(
                driver.getId(),
                driver.getName(),
                driver.getVehicle(),
                driver.getPhoneNumber(),
                driver.getAvailable()
        );
    }

    public static Driver toEntity(DriverDTO driverDTO) {
        return new Driver(
                driverDTO.id(),
                driverDTO.name(),
                driverDTO.vehicle(),
                driverDTO.phoneNumber(),
                driverDTO.available()
        );
    }
}