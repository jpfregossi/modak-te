package com.modak.te.notificationservice.controller;

import com.modak.te.notificationservice.controller.dto.NotificationDTO;
import com.modak.te.notificationservice.exception.CuotaExcededException;
import com.modak.te.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    NotificationService service;
    private static String STATUS_MESSAGE = "status";
    private static String NEWS_MESSAGE = "news";
    private static String MARKETING_MESSAGE = "marketing";

    @PostMapping("/email/status")
    public ResponseEntity<NotificationDTO> sendStatus(@RequestBody NotificationDTO request) {
        try{
            service.send(STATUS_MESSAGE, request);
        } catch (CuotaExcededException e) {
            return ResponseEntity.badRequest().header("X-Ratelimit-Retry-After", e.getMessage()).build();
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping("/email/news")
    public ResponseEntity<NotificationDTO> sendNews(@RequestBody NotificationDTO request) {
        try{
            service.send(NEWS_MESSAGE, request);
        } catch (CuotaExcededException e) {
            return ResponseEntity.badRequest().header("X-Ratelimit-Retry-After", e.getMessage()).build();
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }

    @PostMapping("/email/marketing")
    public ResponseEntity<NotificationDTO> sendMarketing(@RequestBody NotificationDTO request) {
        try{
            service.send(MARKETING_MESSAGE, request);
        } catch (CuotaExcededException e) {
            return ResponseEntity.badRequest().header("X-Ratelimit-Retry-After", e.getMessage()).build();
        }

        return new ResponseEntity<>(request, HttpStatus.OK);
    }
}
