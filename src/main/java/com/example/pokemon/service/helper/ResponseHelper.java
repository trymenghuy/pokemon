package com.example.pokemon.service.helper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ResponseHelper {

    public Map<String, Object> success(String messageKey) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", messageKey);
        result.put("errorCode", 0);
        return result;
    }

    public Map<String, Object> success(String messageKey, Object data) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("success", true);
        result.put("message", messageKey);
        result.put("errorCode", 0);
        return result;
    }

    public Map<String, Object> fail(String messageKey) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", messageKey);
        result.put("errorCode", -1);
        return result;
    }

    public Map<String, Object> fail(String messageKey, int errorCode) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", messageKey);
        result.put("errorCode", errorCode);
        return result;
    }
}
