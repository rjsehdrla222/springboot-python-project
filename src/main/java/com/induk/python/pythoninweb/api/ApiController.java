package com.induk.python.pythoninweb.api;

import com.induk.python.pythoninweb.service.ApisApiService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ApisApiService apisApiService;

    @Scheduled(cron = "0 01 14 * * *")
    public void customerInput(/*@Valid @RequestBody Member member*/) throws Exception {

        URI uri = UriComponentsBuilder
                .fromUriString("http://3.36.83.102:8000")
                .path("/ddareungi/predict")
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        //.queryParam("name", name) // get 방식에서 쓰임.

        /* set 방식에서 쓰임 */
        Map<String, Double> abcd = apisApiService.getData();
        Map<String, Double> req = new HashMap<>();
        Iterator<String> iter = abcd.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            Double value = abcd.get(key);
            req.put(key, value);
        }

        RestTemplate restTemplate = new RestTemplate();

        /* get 방식이면 getForEntity로 바꾸고 req를 쓰지 않고 위에 queryParam을 작성하고 이곳의 req를 삭제 */
        ResponseEntity<String> result = restTemplate.postForEntity(uri, req, String.class);
        int idx = result.getBody().indexOf(".");
        long lastResult = Long.parseLong(result.getBody().substring(1, idx));

        DecimalFormat decFormat = new DecimalFormat("###,###");
        String finalResult = decFormat.format(lastResult);
        apisApiService.apiUpdateValue(finalResult);

    }

    @Data
    static class BorrowInput {
        private Double borrowValue;
        public BorrowInput() {
        }
        public BorrowInput(Double borrowValue) {
            this.borrowValue = borrowValue;
        }
    }

}
