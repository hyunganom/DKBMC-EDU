package com.SalsforceMiddlewareServer.core.ports.out;

import com.SalsforceMiddlewareServer.core.domain.chatgpt.ChatGPTRequest;
import com.SalsforceMiddlewareServer.core.domain.chatgpt.ChatGPTResponse;

public interface ChatGPTAPIClient {
    ChatGPTResponse chat(ChatGPTRequest request);
}
