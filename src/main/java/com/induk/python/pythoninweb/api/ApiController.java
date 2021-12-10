package com.induk.python.pythoninweb.api;

import com.induk.python.pythoninweb.service.ApisApiService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final ApisApiService apisApiService;

    @GetMapping("/api")
    public String customerInput(/*@Valid @RequestBody Member member*/) throws Exception {

        URI uri = UriComponentsBuilder
                .fromUriString("http://3.36.83.102:8000")
                .path("/ddareungi/predict")
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        //.queryParam("name", name) // get 방식에서 쓰임.

        /** 여기 고쳐야 **/
        /* set 방식에서 쓰임 */
        Map<String, Object> abcd = apisApiService.getData();
        Map<String, String> req = new HashMap<>();
        Iterator<String> iter = abcd.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = (String) abcd.get(key);
            req.put(key, value);
        }

        RestTemplate restTemplate = new RestTemplate();

        /* get 방식이면 getForEntity로 바꾸고 req를 쓰지 않고 위에 queryParam을 작성하고 이곳의 req를 삭제 */
        ResponseEntity<CustomerInput> result = restTemplate.postForEntity(uri, req, CustomerInput.class);

        System.out.println(result.getStatusCode());
        System.out.println(result.getBody());
        return "redirect:/index";
    }

    @Data
    static class CustomerInput {
        private String name;
        public CustomerInput() {
        }
        public CustomerInput(String name) {
            this.name = name;
        }
    }

}
