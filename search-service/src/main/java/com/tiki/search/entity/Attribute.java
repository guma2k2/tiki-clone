package com.tiki.search.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record Attribute(String name, String value) {

    public static List<Attribute> fromAttributeMap(Map<String, String> attributes) {
        return attributes.entrySet().stream()
                .map(entry -> new Attribute(entry.getKey(), entry.getValue()))
                .toList();
    }
}
