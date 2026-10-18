package com.lifesamadhan.api.dto;

import lombok.Data;

@Data
public class StatusUpdateDTO {
    public StatusUpdateDTO() {
    }

    public StatusUpdateDTO(String status) {
        this.status = status;
    }

    private String status;
}
