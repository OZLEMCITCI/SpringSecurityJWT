package com.springjwt.service;

import com.springjwt.model.Eroller;
import com.springjwt.model.Kisi;
import com.springjwt.model.KisiRole;
import com.springjwt.regres.LoginRequest;
import com.springjwt.regres.JwtResponse;
import com.springjwt.regres.MesajResponse;
import com.springjwt.regres.RegisterRequest;
import com.springjwt.repository.KisiRepository;
import com.springjwt.repository.RoleRepository;
import com.springjwt.service.JWT.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    KisiRepository kisiRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    KisiService kisiService;

    @Autowired
    JwtUtils jwtUtils;


    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {

        //Daha onceden kullanildiysa bu username hata veriyor
        if (kisiRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MesajResponse("Hata: username is already exist"));
        }

        //Daha onceden kullanildiysa bu email hata veriyor
        if (kisiRepository.existsByEmail((registerRequest.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MesajResponse("Hata: Email is already exist"));
        }

        //Yeni kullanici kayit et
        Kisi kisi = new Kisi(
                registerRequest.getUsername()
                , passwordEncoder.encode(registerRequest.getPassword())
                , registerRequest.getEmail());

        Set<String> stringRoller = registerRequest.getRole();
        Set<KisiRole> roller = new HashSet<>();

        if (stringRoller == null) {
            KisiRole userRole = roleRepository.findByName(Eroller.ROLE_USER).
                    orElseThrow(() -> new RuntimeException("hata: Veritabaninda Role kayitli de??il"));
            roller.add(userRole);
        } else {
            stringRoller.forEach(role -> {
                switch (role) {
                    case "admin":
                        KisiRole adminRole = roleRepository.findByName(Eroller.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Hata: Role mevcut de??il."));
                        roller.add(adminRole);
                        break;
                    case "mod":
                        KisiRole modRole = roleRepository.findByName(Eroller.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("EHata: Role mevcut de??il."));
                        roller.add(modRole);
                        break;
                    default:
                        KisiRole userRole = roleRepository.findByName(Eroller.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Hata: Role mevcut de??il."));
                        roller.add(userRole);
                }
            });

            kisi.setRoller(roller);
            kisiRepository.save(kisi);

        }
        return ResponseEntity.ok(new MesajResponse("Kullanici basarili kayit edildi"));
    }




        //Security config classinda AuthenticationManagerBean() methodunu override yaptik.
        @Autowired
        AuthenticationManager authenticationManager;

        public ResponseEntity<?> getUserInfo (LoginRequest loginRequest){

            //kimlik denetimi yapiyoruz token olusturuyorz
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            //Kisiye gore JWT olusturulmasi ve Security Contex in guncellenmesi
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt= jwtUtils.jwtCreate(authentication);//jwtolusturuldu

            //Kimlik denetimi yapilan kisinin bilgilerinin service katmanindan alinmasi butun bilgilerini aliyoruz
            KisiServiceImpl loginKisi = (KisiServiceImpl) authentication.getPrincipal();

            //login olan kisinin Rollerinin Elde edeilmesi
            List<String> roller = loginKisi.getAuthorities().stream()
                    .map(item -> item.getAuthority()).collect(Collectors.toList());
            return ResponseEntity
                    .ok(new JwtResponse(jwt, loginKisi.getId(), loginKisi.getUsername(), loginKisi.getEmail(), roller));
        }
    }

