package com.bookmypro.provider_service.feature.profile.availability;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityRequest {
    private List<String> workingDays; // e.g. ["MONDAY", "TUESDAY"]
    private String startTime; // "HH:mm"
    private String endTime; // "HH:mm"
    private String breakStart; // "HH:mm"
    private String breakEnd; // "HH:mm"
    private Integer serviceRadius;
    private Integer maxBookings;
    private Boolean homeService;
    private Boolean emergencyService;
    private Boolean weekendAvailable;
}
