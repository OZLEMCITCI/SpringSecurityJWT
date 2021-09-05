package com.springjwt.service;

import com.springjwt.model.Kisi;
import com.springjwt.repository.KisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userDetailsService")
public class KisiService implements UserDetailsService {

@Autowired
KisiRepository kisiRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Kisi kisi=kisiRepository.findByUsername(username).
                orElseThrow(()->new UsernameNotFoundException("Kullanici Bulunamadi "+username));

        /*

        UserDetails user = User.withUsername(kisi.getEmail())
                .password(kisi.getPassword()).authorities("USER").build();

        return user;*/
        return KisiServiceImpl.kisiOlustur(kisi);//userdetails service implement etmistik bu classda databaseden cektigimiz
        //bilgilerin conpasini yapiyoruz.

    }
}

    /* UserDetailsService is an interface with only one method which is loadByUserName() bunun return type UserDetils.*/

