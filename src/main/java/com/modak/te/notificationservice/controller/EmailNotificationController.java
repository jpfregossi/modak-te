package com.modak.te.notificationservice.controller;

import com.modak.te.notificationservice.controller.dto.EmailNotificationDTO;
import com.modak.te.notificationservice.exception.QuotaExcededException;
import com.modak.te.notificationservice.model.Notification;
import com.modak.te.notificationservice.model.NotificationMapper;
import com.modak.te.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/notifications/email")
public class EmailNotificationController {
    @Autowired
    NotificationService service;
    @Autowired
    NotificationMapper mapper;
    private static String STATUS_MESSAGE = "status";
    private static String NEWS_MESSAGE = "news";
    private static String MARKETING_MESSAGE = "marketing";

    @PostMapping("/status")
    public ResponseEntity<EmailNotificationDTO> sendStatus(@RequestBody EmailNotificationDTO request) {
        return handleRequest(STATUS_MESSAGE, request);
    }

    @PostMapping("/news")
    public ResponseEntity<EmailNotificationDTO> sendNews(@RequestBody EmailNotificationDTO request) {
        return handleRequest(NEWS_MESSAGE, request);
    }

    @PostMapping("/marketing")
    public ResponseEntity<EmailNotificationDTO> sendMarketing(@RequestBody EmailNotificationDTO request) {
        return handleRequest(MARKETING_MESSAGE, request);
    }

    // TODO: manage other errors
    private ResponseEntity<EmailNotificationDTO> handleRequest(String type, EmailNotificationDTO request) {
        Notification notification = mapper.from(request);
        try{
            service.process(type, notification);
        } catch (QuotaExcededException e) {
            log.info("Rejecting {} message request for {} [response: {}]",
                    type,
                    request.getEmail(),
                    e.getMessage());
            return ResponseEntity.badRequest().header("X-Ratelimit-Retry-After", e.getMessage()).build();
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
