package com.chatop.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String message;

    @NotNull
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull
    @JsonProperty("rental_id")
    private Integer rentalId;
}