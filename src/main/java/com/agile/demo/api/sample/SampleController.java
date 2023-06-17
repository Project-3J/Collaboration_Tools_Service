package com.agile.demo.api.sample;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/me")
@AllArgsConstructor
public class SampleController {

    @Autowired
    private SampleRepository sampleRepository;
    @GetMapping
    public ResponseEntity<?> gets(HttpHeaders httpHeaders){
        /* valid token*/
        //return ResponseEntity.ok().build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/01")
    public void post(@RequestBody Map<String, Object> requestData) {
        System.out.println(requestData); // 내용을 받기만 하고 토큰을 보내지 않음

    }
}
