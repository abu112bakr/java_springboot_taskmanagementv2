package com.example.taskmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // follow my custom custom security fitler chain
public class SecurityConfig {
    @Autowired
    private JwtFilter JwtFilter;

    // @Autowired
    // private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService; // spring will provide the onject needed for implementation of this interface
    
    // @Autowired
    // private ApplicationContext context;

    // -----------------------
    // Password encoder for DB login
    // 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    

    // -----------------------
    // AuthenticationProvider for username/password login
    // 
    //@Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //provider need to connect with database and verify the user
        //not using password encoder
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        //using UserDetailsService to verify username and password
        provider.setUserDetailsService(userDetailsService); // we will create our own UserDetailsService implementation to connect with db

        return provider;
    }
    // I want to use my own AuthenticationManager
    // Whenever I want to use mine I need to create a bean
    // -----------------------
    // AuthenticationManager bean
    // -----------------------    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
        //Flow: we use authenticationManager which talk to AuthenticationProvider
        //AuthenticationManager is an interface so
        //we use AuthenticationConfiguration which gives an object
    }    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //these are   labmda expression
        return http
                .csrf(customizer -> customizer.disable())
                //.authorizeHttpRequests(request -> request.anyRequest().authenticated()) // any request need auth
                .authorizeHttpRequests(auth -> auth
                    //.requestMatchers("register", "loogin", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html") //these two url dont need auth
                    .requestMatchers("register", "loogin", "/oauth2/**", "/login/oauth2/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html") //these two url dont need auth
                    .permitAll() 
                    .anyRequest().authenticated()) // any ohther request need auth
                .formLogin(Customizer.withDefaults()) //optional for browser login
                .httpBasic(Customizer.withDefaults()) //optional for postman login
                
                // -----------------------
                // Stateless session (JWT)
                // 
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //adding fil†er before normal defult UPAF filter (user password auth filter)
                .addFilterBefore(JwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    

     


}
