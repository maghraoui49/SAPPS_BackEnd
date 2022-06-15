package net.atos.sapps_backend.controller.listerApps;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.atos.sapps_backend.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/lister_app_by_dom")
public class ListerAppsByDomController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthController authController;

    private static final String DOMAINE_URL = "http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplicationsImports/InstanceSourceApplicationsImports/root/T_DOMAINE/";
    private  String SOUS_DOMAINE_URL = "http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplications/InstanceSourceApplications/root/T_APPLICATION?pageSize=800&filter=s_ETAT!=\"D\" and s_DOMAINE_PRINCIPAL";
    private  String APPS_URL = "http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplications/InstanceSourceApplications/root/T_APPLICATION?pageSize=800&filter=s_ETAT!=\"D\" and s_SOUS_DOMAINE";


    @RequestMapping(value = "/domaines", method = RequestMethod.GET)
    public ResponseEntity<ArrayNode> getDomains() throws JsonProcessingException {
        String response = null;
        try {

            // if the authentication is successful
            if (authController.auth().getStatusCode().equals(HttpStatus.OK)) {

                String token = authController.auth().getBody().getTokenType() + " " + authController.auth().getBody().getAccessToken();
                HttpHeaders headers = getHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                // Use Token to get Response
                ResponseEntity<String> helloResponse = restTemplate.exchange(DOMAINE_URL, HttpMethod.GET, jwtEntity, String.class);
                if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
                    response = helloResponse.getBody();

                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response);


        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();


        for (JsonNode fieldValue : root.at("/rows")) {
            ObjectNode domaines = mapper.createObjectNode();
            domaines.put("code", fieldValue.at("/content/s_CODE/content").asText());
            domaines.put("domaine", fieldValue.at("/label").asText());
            arrayNode.add(domaines);
        }

        return ResponseEntity.status(HttpStatus.OK).body(arrayNode);
    }



    @RequestMapping(value = "/sousdomaines", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ArrayNode> getSousDomainebyDomaine(@RequestParam String domaine) throws JsonProcessingException {
        String response = null;
        try {

            // if the authentication is successful
            if (authController.auth().getStatusCode().equals(HttpStatus.OK)) {

                String token = authController.auth().getBody().getTokenType() + " " + authController.auth().getBody().getAccessToken();
                HttpHeaders headers = getHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                // Use Token to get Response
                ResponseEntity<String> helloResponse = restTemplate.exchange(SOUS_DOMAINE_URL+"=\""+domaine+"\"", HttpMethod.GET, jwtEntity, String.class);
                if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
                    response = helloResponse.getBody();
                    }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode root = objectMapper.readTree(response);




        ObjectMapper mapper = new ObjectMapper();

        ArrayNode arrayNode = mapper.createArrayNode();



        for (JsonNode fieldValue : root.at("/rows")) {

            ObjectNode sousdomaines = mapper.createObjectNode();
            sousdomaines.put("code", fieldValue.at("/content/s_SOUS_DOMAINE/content").asText());
            sousdomaines.put("sousdomaine", fieldValue.at("/content/s_SOUS_DOMAINE/label").asText());
            arrayNode.add(sousdomaines);

//            if (arrayNode.isEmpty() || !arrayNode.findValues("code").contains(fieldValue.at("/content/s_SOUS_DOMAINE/content").asText()))
//
//                arrayNode.add(sousdomaines);
//            arrayNode.forEach(jsonNode -> {
//                if (jsonNode.get("code").asText().equals(fieldValue.at("/content/s_SOUS_DOMAINE/content").asText()) )
//
//
//            });


        }

        System.out.println();



        ArrayNode tempJsonNode = mapper.createArrayNode();


        Set<String> stationCodes=new HashSet<String>();

        arrayNode.forEach(jsonNode -> {
            String stationCode = jsonNode.findValue("code").asText();
            if (stationCodes.contains(stationCode)){
                return;
            }
            else {
                stationCodes.add(stationCode);
                tempJsonNode.add(jsonNode);
            }
        });

        return ResponseEntity.status(HttpStatus.OK).body(tempJsonNode);
    }




    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ArrayNode> getAppsbySousDomaine(@RequestParam String sousdomaine) throws JsonProcessingException {
        String response = null;
        try {


            if (authController.auth().getStatusCode().equals(HttpStatus.OK)) {

                String token = authController.auth().getBody().getTokenType() + " " + authController.auth().getBody().getAccessToken();
                HttpHeaders headers = getHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);

                ResponseEntity<String> helloResponse = restTemplate.exchange(APPS_URL+"=\""+sousdomaine+"\"", HttpMethod.GET, jwtEntity, String.class);
                if (helloResponse.getStatusCode().equals(HttpStatus.OK)) {
                    response = helloResponse.getBody();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }


        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        JsonNode root = mapper.readTree(response);



        for (JsonNode fieldValue : root.at("/rows")) {

            ObjectNode appli = mapper.createObjectNode();
            appli.put("code", fieldValue.at("/content/s_CODE/content").asText());
            appli.put("application", fieldValue.at("/content/s_LIBELLE_NORME/content").asText());
            arrayNode.add(appli);



        }




      //  List<Application> applicationDTOS = objectMapper.readValue(recs.toString(), new TypeReference<List<Application>>() {
      //  });

        return ResponseEntity.status(HttpStatus.OK).body(arrayNode);
    }




    @RequestMapping(value = "/treelist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ArrayNode> getTreeList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNodeTreeList = mapper.createArrayNode();

        ArrayNode dTemp = mapper.createArrayNode();
        ArrayNode arrayNodeDomaine = getDomains().getBody();
        arrayNodeDomaine.forEach(jsonNodeD -> {
            ObjectNode objectNodeDomaine = mapper.createObjectNode();
            objectNodeDomaine.put("name", jsonNodeD.at("/domaine").asText());
            //dTemp.add(objectNodeDomaine);

            try {
                ArrayNode sdTemp = mapper.createArrayNode();

                ArrayNode arrayNodeSousDomaine = getSousDomainebyDomaine(jsonNodeD.at("/code").asText()).getBody();
                arrayNodeSousDomaine.forEach(jsonNodeSD ->{
                    ObjectNode objectNodeSousDomaine = mapper.createObjectNode();
                    objectNodeSousDomaine.put("name",jsonNodeSD.at("/sousdomaine").asText());
                    sdTemp.add(objectNodeSousDomaine);
                    try {
                        ArrayNode appsTemp = mapper.createArrayNode();
                        ArrayNode arrayNodeApps = getAppsbySousDomaine(jsonNodeSD.at("/code").asText()).getBody();
                        //System.out.println(arrayNodeApps);
                         arrayNodeApps.forEach(jsonNodeApp -> {
                             ObjectNode objectNodeApp = mapper.createObjectNode();
                             objectNodeApp.put("name",jsonNodeApp.at("/application").asText());
                             //System.out.println(objectNodeApp);
                             appsTemp.add(objectNodeApp);
                         });

                        //System.out.println(appsTemp);



//                        ObjectNode objectNodeChild2 = mapper .createObjectNode();
//                        objectNodeChild2.put("children",appsTemp.asText());
//                        dTemp.add(objectNodeChild2);
                        objectNodeSousDomaine.put("children",appsTemp);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }



                });



//                ObjectNode objectNodeChild = mapper .createObjectNode();
//                objectNodeChild.put("children", sdTemp);
//                dTemp.add(objectNodeChild);
                objectNodeDomaine.put("children",sdTemp);

                arrayNodeTreeList.add(objectNodeDomaine);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }




        });





        String resp =null;
        return ResponseEntity.status(HttpStatus.OK).body(arrayNodeTreeList);
    }





    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }








}
