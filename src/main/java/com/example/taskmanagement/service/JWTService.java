package com.example.taskmanagement.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;
import java.util.function.Function;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.taskmanagement.exception.TaskNotFoundException;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;


import org.springframework.stereotype.Service;

@Service
public class JWTService {

    private String secretKey="";
    // generating key here
    public JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSha256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

            //postman post
        //http://localhost:8080/loogin
        //body
        // {
        //     "username": "cod",
        //     "password": "c@123"
        // }
    public String generateToken(String username){
        // specifying claims
        Map<String, Object> claims = new HashMap<>();
        //System.out.println("Key is " + getKey());
        //generating token using claims & signit using a key
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30 ))
                .and()
                .signWith(getKey())
                .compact();
    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    // private String secretkey;
    // //TIME 37MIN
    // try {
    //     KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
    //     SecretKey sk = keyGen.generateKey();
    //     secretkeyBase64.getEncoder().encodeToString(sk.getEncoded());

    // } catch (TaskNotFoundException e) {
    //     throw new RuntimeException(e);
        
    // }

    // public String generateToken(String username) {
    //     // How to generate token?
    //     Map<String, Object> claims = new HashMap<>();
        
    //     return Jwts.builder()
    //             .claims()
    //             .add(claims)
    //             .subject(username)
    //             .issuedAt(new Date(System.currentTimeMillis()))
    //             .expiration(new Date(System.currentTimeMillis() * 60 * 60 * 30))
    //             .and()
    //             .signWith(getKey())
    //             .compact();

    // }

    // private Key getKey() {
    //     byte[] keyBytes = Decoders.BASE64.decode(secretkey);
    //     return Keys.hmacShaKeyFor(keyBytes);
    // }

}
