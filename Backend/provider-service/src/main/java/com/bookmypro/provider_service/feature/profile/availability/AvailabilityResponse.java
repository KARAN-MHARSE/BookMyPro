package com.bookmypro.provider_service.feature.profile.availability;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse {
    private List<String> workingDays;
    private String startTime;
    private String endTime;
    private String breakStart;
    private String breakEnd;
    private Integer serviceRadius;
    private Integer maxBookings;
    private Boolean homeService;
    private Boolean emergencyService;
    private Boolean weekendAvailable;
}
