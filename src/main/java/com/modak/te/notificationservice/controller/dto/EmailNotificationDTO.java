package com.modak.te.notificationservice.controller.dto;

import lombok.Data;

@Data
public class EmailNotificationDTO {
    String name;
    String email;
    String subject;
    String message;
}
