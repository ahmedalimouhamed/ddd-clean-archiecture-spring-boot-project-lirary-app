package com.library.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateMemberRequest {
    @NotBlank(message = "Member ID is required")
    @Pattern(regexp = "^M\\d{6}$", message = "Member ID must be in format M0000001")
    private String memberId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^\\+?[0-9\\s\\-]{10,}$", message = "Invalid phone number")
    private String phone;
}
