package com.springjwt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="roller")
public class KisiRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING) //enumaraterden cekiyoruz role bilgilerini
    //table koyarken enumun typeni belirtmek icin yukardaki annotasyon kullanilir
    private Eroller name;

    public KisiRole(Eroller name) {

        this.name = name;
    }


}
