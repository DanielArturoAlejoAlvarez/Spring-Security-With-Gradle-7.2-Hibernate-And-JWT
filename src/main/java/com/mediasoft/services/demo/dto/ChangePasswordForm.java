package com.mediasoft.services.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ChangePasswordForm {
    @NotNull
    private Long id;

    @NotBlank(message = "Current Password must not be blank!")
    private String currentPassword;
    @NotBlank(message = "New Password must not be blank!")
    private String newPassword;
    @NotBlank(message = "Confirm Password must not be blank!")
    private String confirmPassword;

}
