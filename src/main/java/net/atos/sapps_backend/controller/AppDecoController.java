package net.atos.sapps_backend.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import net.atos.sapps_backend.model.Application;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/application_deco")
public class AppDecoController {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthController authController;

    private static final String HELLO_URL = "http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplications/InstanceSourceApplications/root/T_APPLICATION?filter=s_ETAT=\"D\"";

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Application>> getApplications() throws JsonProcessingException {
        String response = null;
        try {

            // if the authentication is successful
            if (authController.auth().getStatusCode().equals(HttpStatus.OK)) {

                String token = authController.auth().getBody().getTokenType()+ " " +authController.auth().getBody().getAccessToken();
                HttpHeaders headers = getHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                // Use Token to get Response
                ResponseEntity<String> helloResponse = restTemplate.exchange(HELLO_URL, HttpMethod.GET, jwtEntity, String.class);
                if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
                    response = helloResponse.getBody();

                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        JSONObject jsonObject = new JSONObject(response);
        JSONArray recs = jsonObject.getJSONArray("rows");

        List<Application> applicationDTOS = objectMapper.readValue(recs.toString(), new TypeReference<List<Application>>() {
        });
        return ResponseEntity.status(HttpStatus.OK).body(applicationDTOS);
    }


    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
