package com.qwerty.tokenbucket.api;

import com.qwerty.tokenbucket.model.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class Controller {

    @GetMapping("/ping")
    public Response ping() {
        return Response.builder()
                .message("pong")
                .build();
    }
}
