package com.SalsforceMiddlewareServer.adapters.in.web;

import com.SalsforceMiddlewareServer.core.ports.in.ChatGPTUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/gpt")
public class ChatGPTController {

    private final ChatGPTUseCase chatGPTUseCase;

    @GetMapping("/chat")
    public String chat(@RequestParam(name = "prompt") String prompt) {
        String response = chatGPTUseCase.chat(prompt);
        log.debug("ChatGPTResponse: {}", response);
        return response;
    }
}
