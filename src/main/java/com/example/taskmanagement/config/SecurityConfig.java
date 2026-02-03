package com.example.taskmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // follow my custom custom security fitler chain
public class SecurityConfig {
    // IMPLEMENT DEFAULT FORM LOGIN AND BASIC AUTHENTICATION BUT USING CUSTOM PASSWORD
    // http.csrf(customizer -> customizer.disable()); disable csrf token
    //http.authorizeHttpRequests(request -> request.anyRequest().authenticated()) every request must be authenticated
    //http.formLogin(Customizer.withDefaults()); // default login form
    //http.httpBasic(Customizer.withDefaults()); // accept login from postman
    //.formLogin(Customizer.withDefaults())
    @Bean
    public SecurityFilterChain securityFilterChanin(HttpSecurity http) throws Exception {
        //these are   labmda expression
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
// Part 2
// working with db to verify user
//Authentication object -> Authentication provider -> Authentication object
//un-authticated                                        authenticated
//we need to modify the authentication provider
    // we will use our own UserDetailsService by using a class(MyUserDetailsService.java)
    @Autowired
    private UserDetailsService userDetailsService; // spring will provide the onject needed for implementation of this interface
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //provider need to connect with database and verify the user
        //not using password encoder
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        //using UserDetailsService to verify username and password
        provider.setUserDetailsService(userDetailsService); // we will create our own UserDetailsService implementation to connect with db


        return provider;
        
    }




// Part 1
// using UserDetailsService & inMemoryUserDetailsManager to verify custom username and password
// we verify user details from database using UserDetailsService interface, we will customize it
// we will use InMemoryUserDetailsManager which is a class that implements UserDetailsService interface
// InMemoryUserDetailsManager need object of UserDetails, UserDetails is an interface so we need a class for it. 
// we use inbuild User from spring framwork & this User is a class which implements UserDetails interface
// we will use builder of User class
    // @Bean
    // public UserDetailsService userDetailsService() {
    //     UserDetails user1 = User
    //             .withDefaultPasswordEncoder() // for demo purpose only, not for production\
    //             .username("kiran")
    //             .password("k@123")
    //             .roles("USER")
    //             .build();

    //     UserDetails user2 = User
    //             .withDefaultPasswordEncoder() // for demo purpose only, not for production\
    //             .username("harsh")
    //             .password("h@123")
    //             .roles("ADMIN")
    //             .build();
    //     return new InMemoryUserDetailsManager(user1, user2);
    // }    
}
