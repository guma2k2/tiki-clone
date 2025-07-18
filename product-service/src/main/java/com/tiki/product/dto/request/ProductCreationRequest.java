package com.tiki.product.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public record ProductCreationRequest(
    @NotBlank
    String name,
    String description,

    @NotBlank
    Integer brandId,

    @NotBlank
    Integer categoryId,

    Map<Integer, String> attributes

) {
}
