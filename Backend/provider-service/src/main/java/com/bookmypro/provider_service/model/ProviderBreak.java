package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "provider_break")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderBreak {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "break_id")
    private UUID breakId;

    @Column(name = "availability_id")
    private UUID availabilityId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}
