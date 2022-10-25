package com.example.user_management.user.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUserDto {
    @NotEmpty(message = "Không được để trống")
    private String name;
    @NotEmpty(message = "Không được để trống")
    private String email;
    @NotEmpty(message = "Không được để trống")
    private String password;
    @NotEmpty(message = "Không được để trống")
    private String confirmPassword;
}
