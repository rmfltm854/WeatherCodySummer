package com.example.weathercodysummer.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class WeatherApiService { //openWeather Api 로직
    public String main() throws JsonProcessingException {

        HashMap<String, Object> result = new HashMap<String,Object>();

        String jsonInString = "";
        String jsonInString1 = "";

        try {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String url = "https://api.openweathermap.org/data/2.5/weather";

            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url + "?" + "q=Seoul,kr&appid=9f09039e3f7251d24b085929292a0317").build();

            //이 한줄의 코드로 API를 호출해 MAP타입으로 전달 받는다.
            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            LinkedHashMap lm = (LinkedHashMap)resultMap.getBody();

            ArrayList<Map> tw = (ArrayList<Map>)lm.get("weather");
            LinkedHashMap mnList = new LinkedHashMap<>();
            for (Map obj : tw) {
                mnList.putAll(obj);
            }
            Object please = mnList.get("main");


            System.out.println(resultMap.toString());


            ObjectMapper mapper = new ObjectMapper();
            jsonInString = mapper.writeValueAsString(please);


        }catch (HttpClientErrorException | HttpServerErrorException e){
            result.put("statusCode", e.getRawStatusCode());
            result.put("body"  , e.getStatusText());
            System.out.println("데이터");
            System.out.println(e.toString());
        }catch (Exception e) {
            result.put("statusCode", "999");
            result.put("body"  , "excpetion오류");
            System.out.println(e.toString());
        }

        return jsonInString;

    }

}
