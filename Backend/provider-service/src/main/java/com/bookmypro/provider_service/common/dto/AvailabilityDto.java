package com.bookmypro.provider_service.common.dto;

import java.time.LocalTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvailabilityDto {
	private UUID availabilityId;
	private String dayOfWeek;
	private LocalTime startTime;
	private LocalTime endTime;
	private Boolean isAvailable;
}
