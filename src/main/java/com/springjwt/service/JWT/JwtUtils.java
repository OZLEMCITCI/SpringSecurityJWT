package com.springjwt.service.JWT;


import com.springjwt.service.KisiServiceImpl;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${springJWT.app.jwtExpirationMs}")//application propertiesden yolluyoruz
    private int jwtExpirationMs;//expration date


    @Value("${springJWT.app.jwtSecret}")//application propertiesden yolluyoruz
    private String jwtSecret;//expration date


    public String jwtCreate(Authentication authentication){
        KisiServiceImpl kisiBilgiler=(KisiServiceImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject(kisiBilgiler.getUsername())
                .setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime()+jwtExpirationMs))//gecerlilik suresi hard code yerine boyle yazdik
                .signWith(SignatureAlgorithm.HS512,jwtSecret)//gizli sifre bununa gore butun diger sifreleri ayarliyor
                //bunu kullanarak herseye ulasabiliriz.
                .compact();

    }

    public String usernameAl(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean JwtTokenGecerle(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            System.out.println("JWT Hatasi:" + e.getMessage());
        }
        return false;
    }
}
