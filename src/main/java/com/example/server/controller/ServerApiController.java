package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {

    @GetMapping("/naver")
    public String naver() {
        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", "중국집")
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode()
                .build()
                .toUri();


        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(uri).header("X-Naver-Client-Id", "v5dJzgkTn_sABo2hCdHo")
                .header("X-Naver-Client-Secret", "zA4J6k0G7o").build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();
    }

    @GetMapping("/hello")
    public User hello(@RequestParam String name, @RequestParam int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public Req<User> post(@RequestBody Req<User> user, @PathVariable int userId, @PathVariable String userName,
                          @RequestHeader("x-authorization") String authorization,
                          @RequestHeader("custom-header") String customHeader) {
      log.info("client request = {}", user);
      log.info("userId = {}, userName = {}", userId, userName);
      log.info("authorization = {}, customHeader = {}", authorization, customHeader);

      Req<User> response = new Req<>();
      response.setHeader(new Req.Header());
      response.setBody(user.getBody());
      return response;
    }
}
