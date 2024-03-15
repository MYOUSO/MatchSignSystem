package com.matchsystem.matchsystem.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id ;
    private String account;
    private String password;
    private String doublePassword;
    private String email;
    private String phone;
    private String token;
    private int permission;
    private String newPassword;
}