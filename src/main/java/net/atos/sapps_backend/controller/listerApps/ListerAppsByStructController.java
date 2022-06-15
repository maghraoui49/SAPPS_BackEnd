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



@CrossOrigin("*")
@RestController
@RequestMapping("/lister_app_by_struct")
public class ListerAppsByStructController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AuthController authController;

    private  String STRUCT_URL = "http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplicationsImports/InstanceSourceApplicationsImports/root/T_DEPARTEMENT";
    private  String APPS_URL = "http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplications/InstanceSourceApplications/root/T_APPLICATION?pageSize=800&filter=s_ETAT!=\"D\" and s_DEPARTEMENT_BUILD_RUN";





    @RequestMapping(value = "/structure", method = RequestMethod.GET)
    public ResponseEntity<ArrayNode> getStructures() throws JsonProcessingException {
        String response = null;
        try {

            // if the authentication is successful
            if (authController.auth().getStatusCode().equals(HttpStatus.OK)) {

                String token = authController.auth().getBody().getTokenType() + " " + authController.auth().getBody().getAccessToken();
                HttpHeaders headers = getHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                // Use Token to get Response
                ResponseEntity<String> helloResponse = restTemplate.exchange(STRUCT_URL, HttpMethod.GET, jwtEntity, String.class);
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
            ObjectNode categories = mapper.createObjectNode();
            categories.put("code", fieldValue.at("/content/s_CODE/content").asText());
            categories.put("structure", fieldValue.at("/label").asText());
            arrayNode.add(categories);
        }

        return ResponseEntity.status(HttpStatus.OK).body(arrayNode);
    }


    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ArrayNode> getAppsbyStructure(@RequestParam String structure) throws JsonProcessingException {
        String response = null;
        try {

            // if the authentication is successful
            if (authController.auth().getStatusCode().equals(HttpStatus.OK)) {

                String token = authController.auth().getBody().getTokenType() + " " + authController.auth().getBody().getAccessToken();
                HttpHeaders headers = getHeaders();
                headers.set("Authorization", token);

                HttpEntity<String> jwtEntity = new HttpEntity<String>(headers);
                // Use Token to get Response
                ResponseEntity<String> helloResponse = restTemplate.exchange(APPS_URL + "=\"" + structure + "\"", HttpMethod.GET, jwtEntity, String.class);
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

        return ResponseEntity.status(HttpStatus.OK).body(arrayNode);

    }






    @RequestMapping(value = "/treelist", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ArrayNode> getTreeList() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNodeTreeList = mapper.createArrayNode();


        ArrayNode arrayNodeCategorie = getStructures().getBody();
        arrayNodeCategorie.forEach(jsonNodeCat -> {
            ObjectNode objectNodeDomaine = mapper.createObjectNode();
            objectNodeDomaine.put("name", jsonNodeCat.at("/structure").asText());

            try {
                ArrayNode appsTemp = mapper.createArrayNode();

                ArrayNode arrayNodeApps = getAppsbyStructure(jsonNodeCat.at("/code").asText()).getBody();
                arrayNodeApps.forEach(jsonNodeSD ->{
                    ObjectNode objectNodeSousDomaine = mapper.createObjectNode();
                    objectNodeSousDomaine.put("name",jsonNodeSD.at("/application").asText());
                    appsTemp.add(objectNodeSousDomaine);


                });



//                ObjectNode objectNodeChild = mapper .createObjectNode();
//                objectNodeChild.put("children", sdTemp);
//                dTemp.add(objectNodeChild);
                objectNodeDomaine.put("children",appsTemp);

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
