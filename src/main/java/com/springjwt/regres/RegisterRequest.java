package com.springjwt.regres;

import com.springjwt.model.KisiRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

//bu kisim sadece register ve login kismini yapiyoruz
@Getter
@Setter
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Size(min=6,max=40)
    private String password;

    private String email;

    private Set<String> role;


}
