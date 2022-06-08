package net.atos.sapps_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.atos.sapps_backend.DTO.AuthResponse;
import net.atos.sapps_backend.DTO.security.ResponseToken;
import net.atos.sapps_backend.DTO.security.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@CrossOrigin(origins="http://localhost:4200/")
@RestController
public class AuthController {

    private static final String AUTHENTICATION_URL = "http://localhost:8080/ebx-dataservices/rest/auth/v1/token:create";

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value = "/auth")
    public ResponseEntity<ResponseToken> auth() throws JSONException, JsonMappingException, JsonProcessingException {

        // create user authentication object
        User authenticationUser = getAuthenticationUser();
        // convert the user authentication object to JSON
        String authenticationBody = getBody(authenticationUser);
        // create headers specifying that it is JSON request
        HttpHeaders authenticationHeaders = getHeaders();
        HttpEntity<String> authenticationEntity = new HttpEntity<String>(authenticationBody,
                authenticationHeaders);
        // Authenticate User and get JWT
        ResponseEntity<ResponseToken> authenticationResponse = restTemplate.exchange(AUTHENTICATION_URL,
                HttpMethod.POST, authenticationEntity, ResponseToken.class);
        return authenticationResponse;
    }



    private User getAuthenticationUser() {
        User user = new User();
        user.setLogin("ebx_admin");
        user.setPassword("ebx_admin");
        return user;
    }


    private String getBody(final User user) throws JsonProcessingException {

        return new ObjectMapper().writeValueAsString(user);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
