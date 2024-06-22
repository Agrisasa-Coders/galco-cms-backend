package com.gapco.backend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HashMapConverter implements AttributeConverter<Map<String, Integer>, String> {

    @Override
    public String convertToDatabaseColumn(Map<String, Integer> stringObjectMap) {
        String stringObjectMapJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            stringObjectMapJson = objectMapper.writeValueAsString(stringObjectMap);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return stringObjectMapJson;
    }

    @Override
    public Map<String, Integer> convertToEntityAttribute(String stringObjectMapJson) {

        Map<String, Integer> stringObjectMap = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            stringObjectMap = objectMapper.readValue(stringObjectMapJson,
                    new TypeReference<HashMap<String, Integer>>() {});
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return stringObjectMap;
    }
}
