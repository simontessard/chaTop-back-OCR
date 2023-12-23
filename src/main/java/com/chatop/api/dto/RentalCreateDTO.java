package com.chatop.api.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalCreateDTO {
    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Double surface;

    @NotNull
    private Double price;

    @NotNull
    private MultipartFile picture;
}
