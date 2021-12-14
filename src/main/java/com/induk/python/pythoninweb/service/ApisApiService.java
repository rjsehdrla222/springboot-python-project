package com.induk.python.pythoninweb.service;

import com.induk.python.pythoninweb.domain.Board;
import com.induk.python.pythoninweb.repository.ApiRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ApisApiService {

    private final ApiRepository apiRepository;
    static int SKY = 0, POP = 0, TMN = 0, PTY = 0, REH = 0, WSD = 0, TMX = 0;

    public HashMap<String, Double> getData() throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        //String jsonInString = "";
        HashMap<String, Double> value = new HashMap<>();
        try {
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            //RestTemplate restTemplate = new RestTemplate(factory);
            //HttpHeaders headers = new HttpHeaders();
            //HttpEntity<?> entity = new HttpEntity<>(headers);
            Calendar c1 = new GregorianCalendar();
            c1.add(Calendar.DATE, 0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String today = sdf.format(c1.getTime());

            Calendar c2 = new GregorianCalendar();
            c2.add(Calendar.DATE, 1);
            Object tomorrow = sdf.format(c2.getTime());

            String url;
            URL uri;

            url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=Qiig16sePSBl5kxkzEKXXARzJtOeGEI%2FiXQvkfJFvZGnXpcvJ0dYUgtG7u1m9p5pVHbOL2G%2F%2F%2FTyfwlh8J0kWA%3D%3D&numOfRows=217&dataType=JSON&pageNo=2&base_time=0500&nx=60&ny=127";
            uri = new URL(url + "&" + "base_date=" + today);
            System.out.println(uri);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
            String result2 = sb.toString();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(result2);
            JSONObject parse_response = (JSONObject) jsonObj.get("response");
            JSONObject parse_body = (JSONObject) parse_response.get("body");// response 로 부터 body 찾아오기
            JSONObject parse_items = (JSONObject) parse_body.get("items");// body 로 부터 items 받아오기
            // items로 부터 itemlist 를 받아오기 itemlist : 뒤에 [ 로 시작하므로 jsonarray이다.
            JSONArray parse_item = (JSONArray) parse_items.get("item");

            JSONObject obj;
            String category; // 기준 날짜와 기준시간을 VillageWeather 객체에 저장합니다.

            for (int i = 0; i < parse_item.size(); i++) {
                obj = (JSONObject) parse_item.get(i); // 해당 item을 가져옵니다.
                String fcstTime = (String) obj.get("fcstTime");
                int borderTime = Integer.parseInt(fcstTime);
                Object fcstDate = obj.get("fcstDate");
                category = (String) obj.get("category"); // item에서 카테고리를 검색해옵니다.
                if (!category.equals("PCP")) {
                    if (!category.equals("SNO")) {
                        Double categorysValue = Double.parseDouble((String) obj.get("fcstValue"));
                        if (fcstDate.equals(tomorrow) && borderTime <= 2000) {
                            switch (category) {
                                case "TMN":
                                    if (TMN != 1) {
                                        value.put("TMN", categorysValue);
                                        TMN++;
                                    }
                                    break;
                                case "TMX":
                                    if (TMX != 1) {
                                        value.put("TMX", categorysValue);
                                        TMX++;
                                    }
                                    break;
                            }
                            if (borderTime >= 1400 && borderTime < 1600) {
                                switch (category) {
                                    case "POP":
                                        if (POP != 1) {
                                            value.put("POP", categorysValue);
                                            POP++;
                                        }
                                        break;
                                    case "PTY":
                                        if (PTY != 1) {
                                            value.put("PTY", categorysValue);
                                            PTY++;
                                        }
                                        break;
                                    case "REH":
                                        if (REH != 1) {
                                            value.put("REH", categorysValue);
                                            REH++;
                                        }
                                        break;
                                    case "SKY":
                                        if (SKY != 1) {
                                            value.put("SKY", categorysValue);
                                            SKY++;
                                        }
                                        break;
                                    case "WSD":
                                        if (WSD != 1) {
                                            value.put("WSD", categorysValue);
                                            WSD++;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Response: " + value);
        } catch (HttpClientErrorException |
                HttpServerErrorException e) {
            result.put("statusCode", e.getRawStatusCode());
            System.out.println(e);
        } catch (
                Exception e) {
            result.put("statusCode", "999");
            result.put("body", "except 오류");
            System.out.println(e);
        }
        if (!value.isEmpty()) {
            SKY = 0;
            POP = 0;
            TMN = 0;
            PTY = 0;
            REH = 0;
            WSD = 0;
            TMX = 0;
        } else {
            System.out.println("다음에 다시 요청해주세요");
        }
        return value;
    }

    public String apiSelectValue() {
        return apiRepository.apiSelectValue();
    }

    public void apiUpdateValue(String value) {
        apiRepository.apiValueUpdate(value);
    }


}
