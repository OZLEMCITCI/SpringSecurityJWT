package com.springjwt.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="personel")
@Getter
@Setter
@NoArgsConstructor
public class Kisi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Length(message = "Length is not valid", min = 3, max = 30)
    @NotBlank
    @Size(min=3,max=30)
    private String username;

    @Length(message = "length should be between 4 and 120 character", min = 4, max = 120)
    @NotBlank
    @Size(min=4, max=120)
    private String password;

    @Email
    @NotBlank
    private String email;

    @ManyToMany(fetch=FetchType.LAZY) //1 kisi many role alabilir bir role 1 den fazla kisi tarafindan kullanilabilir
    @JoinTable(name="kisi_roller", //adi Kisi_roller olan bir tablo olusturuyoruz
               joinColumns =@JoinColumn(name="kisi_id" ),//1. kolumun adi kisi_id ve default olarak kisi tablosunun id sini aliyor
               //degismek istersen referencedColumnName ="username" yapabilirisn.
               inverseJoinColumns = @JoinColumn(name="role_id"))//yukardaki aciklama ile ayni
    private Set <KisiRole> roller=new HashSet<>();

    public Kisi(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
