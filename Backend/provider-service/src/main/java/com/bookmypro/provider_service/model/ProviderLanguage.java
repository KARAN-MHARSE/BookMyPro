package com.bookmypro.provider_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "provider_language")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "provider_language_id")
    private UUID providerLanguageId;

    @Column(name = "provider_id")
    private UUID providerId;

    @Column(name = "language")
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "proficiency")
    private com.bookmypro.provider_service.enums.LanguageProficiency proficiency; // e.g. CONVERSATIONAL, FLUENT, NATIVE
}
