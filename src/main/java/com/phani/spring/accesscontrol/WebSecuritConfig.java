package com.phani.spring.accesscontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecuritConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("READ_ADMIN", "WRITE_ADMIN")
                .antMatchers("/books/**").hasAnyAuthority("READ", "WRITE")
                .antMatchers("/users/**").hasAnyAuthority("READ", "WRITE")
                .antMatchers("/public/**").permitAll()
                .antMatchers("/secured/**").hasAnyAuthority("READ_ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .httpBasic();
    }

    /*protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()// NEVER disable CSRF, it is disabled here to simplify the demo. Disabling CSRF protection should never be in a real application.
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyAuthority("READ_ADMIN", "WRITE_ADMIN")
                .antMatchers("/books/**").hasAnyAuthority("READ", "WRITE")
                .antMatchers("/public/**").permitAll() //Allow access to everyone for /public folder
                .anyRequest().authenticated()
                .and()
                .httpBasic();//NEVER use Basic Authentication, it is used here to simplify demo
    }*/

   /* protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // NEVER disable CSRF, it is disabled here to simplify the demo. Disabling CSRF protection should never be in a real application.
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic(); //NEVER use Basic Authentication, it is used here to simplify demo
    } */

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
