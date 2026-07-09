package com.bookmypro.identity_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
	
	@NotBlank(message = "Recipient email is required")
    @Email(message = "Must be a valid email address")
	private String to;
	
    @NotBlank(message = "Subject is required")
	private String subject;
	
    @NotBlank(message = "Body is required")
	private String body;

}
