package com.springjwt.security;

import com.springjwt.service.JWT.JwtAuthFiliter;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


//    @Bean      Murat hoca buraya ekledi
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder(10);
//    }





    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthFiliter jwtAuthFiliter(){
        return new JwtAuthFiliter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {



        //Asagidaki islemlerle sifresiz bi sekilde girilan alanlara izin veriyoruz Mesela testcontroller class da /api/test/all
        http.cors().and()//Corss Origin Resource Sharing ayarlar buna izin vermemiz lazim UI ile baglanti yapar
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//oturumsuz demek( stateless durumsuz demek Restapi stateless dir
                .and()
                .authorizeRequests()
                .antMatchers("/api/test/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/auth/login").permitAll()
                .anyRequest()
                .authenticated().and().httpBasic();

        http.addFilterBefore(jwtAuthFiliter(), UsernamePasswordAuthenticationFilter.class);
    }
}
