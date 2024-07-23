package com.SalsforceMiddlewareServer.adapters.in.web;

import com.SalsforceMiddlewareServer.core.domain.salesforce.SendToSalesforce;
import com.SalsforceMiddlewareServer.core.ports.in.SalesforceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin //CORS 설정을 허용
@RestController
//기본 응답 미디어 타입을 JSON으로 설정(인터넷에 전달되는 파일 포맷 및 포맷 컨텐츠를 위한 식별자)
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SalesforceController {

    private static final String SUCCESS_MESSAGE = "세일즈포스 보내기 성공!";
    private static final String FAILURE_MESSAGE = "세일즈포스 보내기 실패: ";

    private final SalesforceUseCase salesforceUseCase;

    @PostMapping("/send-chat")
    public ResponseEntity<String> sendChat(@RequestBody SendToSalesforce newChat) {
        try {
            salesforceUseCase.sendToSalesforce(newChat); // Salesforce 유스 케이스를 사용하여 메시지를 Salesforce로 전송
            return ResponseEntity.status(HttpStatus.CREATED).body(SUCCESS_MESSAGE);
        } catch (Exception e) {
            log.error("Failed to send to Salesforce: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILURE_MESSAGE + e.getMessage());
        }
    }

    @PostMapping("/receive-chat")
    public ResponseEntity<String> handleContactWebhook(@RequestBody String receiveChat) { //웹훅 요청을 문자열로 받아 처리
        try {
            String response = salesforceUseCase.handleSalesforceWebhook(receiveChat);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error handling webhook: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILURE_MESSAGE + e.getMessage());
        }
    }
}
//CORS란 도메인이 다른 서버끼리 리소스를 주고 받을 때 보안을 위해 설정된 정책
//API 서버 B에서 CORS 허용 설정이 되어 있지 않으면 웹 브라우저에서 API 접근이 거부될 수 있다.