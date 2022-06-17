package net.atos.sapps_backend.controller.Dashboard;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.atos.sapps_backend.controller.ApplicationController;
import net.atos.sapps_backend.controller.AuthController;
import net.atos.sapps_backend.model.Application;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    ApplicationController applicationController;

    @Autowired
    private AuthController authController;

    ObjectMapper objectMapper = new ObjectMapper();

    Map<String,String> urlFilters = new HashMap<>();


    @GetMapping(value = "/dashboard-data")
    public ResponseEntity<?> getDashboardData() throws JsonProcessingException, ParseException, java.text.ParseException {

        final Map<String, List<Object>> dashboardMap=new HashMap<>();



        urlFilters.put("etat","http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplications/InstanceSourceApplications/root/T_ETAT_APPLICATION") ;
        urlFilters.put("appartenance","http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplications/InstanceSourceApplications/root/T_APPLICATION/8/s_APPARTENANCE?selector=true");
        urlFilters.put("gspatrimoine","http://localhost:8080/ebx-dataservices/rest/data/v1/BBrancheSourceApplications/InstanceSourceApplications/root/T_APPLICATION/46/s_GESTION_PATRIMOINE?selector=true");



        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 30)).build();
        WebClient client = WebClient.builder().exchangeStrategies(exchangeStrategies).build();


        JsonNode rootNode=objectMapper.createObjectNode();
        ObjectNode dashNode=((ObjectNode) rootNode);





        ResponseEntity<?> responseEntity=applicationController.getApplications();
        List<Application>  applicationDTOS= (List<Application>) responseEntity.getBody();

        ObjectNode node1=new ObjectNode(objectMapper.getNodeFactory());



        dashNode.put("totalApplication",applicationDTOS.size());
        dashNode.put("titreTotalApplication","Totale application");

        dashNode.put("titreApplicationTMA","Totale application en TMA");
        dashNode.put("totalApplicationTMA",applicationDTOS.stream().filter(o -> o.getEnTMA()!=null && o.getEnTMA().equals("Oui")).count());
        for (Map.Entry<String,String> set: urlFilters.entrySet())
        {
            System.out.println(set.getKey()+" "+set.getValue());


            String response;

            String token = authController.auth().getBody().getTokenType()+ " " +authController.auth().getBody().getAccessToken();


            try {
                response = client.get()
                        .uri(new URI(set.getValue()))
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                JSONObject jsonObject= new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("rows");







                List <Object> rs= objectMapper.
                        readValue(jsonArray.toString(), new TypeReference< List<Map<String, Object>>>()
                        {}).stream().map(o -> o.get("label")).collect(Collectors.toList());


                dashboardMap.put(set.getKey(),rs);

                ObjectNode node=new ObjectNode(objectMapper.getNodeFactory());

                ArrayNode labels= node.putArray("label");

                ArrayNode data= node.putArray("data");




                rs.stream().forEach(o -> labels.add(o.toString()));
                System.out.println(dashboardMap.size());

                for (int i = 0; i < labels.size() ; i++) {
                    data.add(0);
                }


                dashNode.set(set.getKey(),node);

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }


        ObjectNode node=new ObjectNode(objectMapper.getNodeFactory());

        ArrayNode labels= node.putArray("label");

        ArrayNode dataDecom= node.putArray("dataDecom");
        ArrayNode dataMiseEnService= node.putArray("dataMiseEnService");


        Date dateEnd = new Date();

        DateFormat sourceFormat = new SimpleDateFormat("yyyy-mm-dd");

        Date dateStart = sourceFormat.parse("1997-01-01");
        Date date=dateStart;
        while (date.getYear()<=dateEnd.getYear())
        {
            labels.add(date.getYear()+1900);
            date.setYear( date.getYear()+1);

        }
        dashNode.set("lineChart",node);


        for (int i = 0; i <labels.size() ; i++) {
            dataDecom.add(0);
            dataMiseEnService.add(0);
        }

        for (int i = 0; i <applicationDTOS.size(); i++) {

            JsonNode appartenance= dashNode.get("appartenance");

            for (int j = 0; j < appartenance.get("label").size(); j++) {

                if (applicationDTOS.get(i).getAppartenance().equals(appartenance.get("label").get(j).asText()))
                {
                    int val=((ArrayNode )dashNode.get("appartenance").get("data")).get(j).asInt()+1;
                    ((ArrayNode )dashNode.get("appartenance").get("data")).set(j,val);
                    break;
                }

            }

            JsonNode gspatrimoine= dashNode.get("gspatrimoine");
            for (int j = 0; j < appartenance.get("label").size(); j++) {

                if ( applicationDTOS.get(i).getGestionPatrimoine()!=null && applicationDTOS.get(i).getGestionPatrimoine().equals(gspatrimoine.get("label").get(j).asText()))

                {
                    int  val=((ArrayNode )dashNode.get("gspatrimoine").get("data")).get(j).asInt()+1;
                    ((ArrayNode )dashNode.get("gspatrimoine").get("data")).set(j,val);
                    break;
                }

            }


            JsonNode etat= dashNode.get("etat");

            for (int j = 0; j < etat.get("label").size(); j++) {

                if (  applicationDTOS.get(i).getEtat().equals(etat.get("label").get(j).asText()))
                {
                    int val=((ArrayNode )dashNode.get("etat").get("data")).get(j).asInt()+1;
                    ((ArrayNode )dashNode.get("etat").get("data")).set(j,val);
                    break;
                }

            }

            JsonNode lineChart= dashNode.get("lineChart");

            for (int j = 0; j < lineChart.get("label").size(); j++) {

                if (  applicationDTOS.get(i).getDateMiseEnService()!=null && applicationDTOS.get(i).getDateMiseEnService().getYear()+1900==(lineChart.get("label").get(j).asInt()))
                {
                    Integer val= (Integer)((ArrayNode )lineChart.get("dataMiseEnService")).get(j).asInt()+1;
                    ((ArrayNode )lineChart.get("dataMiseEnService")).set(j,val);
                    break;
                }
                if (  applicationDTOS.get(i).getDateDecommission()!=null && applicationDTOS.get(i).getDateDecommission().getYear()+1900==(lineChart.get("label").get(j).asInt()))
                {
                        Integer  val=(Integer) ((ArrayNode )lineChart.get("dataDecom")).get(j).asInt()+1;
                    ((ArrayNode )lineChart.get("dataDecom")).set(j,val);
                    break;
                }

            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(dashNode);

    }

}
