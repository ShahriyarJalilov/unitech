package az.unibank.cn.unitech.util;

import az.unibank.cn.unitech.exception.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class MapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String mapToString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Long mapToLong(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Long mapToLong(Object number) {
        try {
            return Long.parseLong((String) number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String mapToErrorMessage(InputStream inputStream) {
        try {
            return objectMapper.readValue(inputStream, ErrorResponse.class).getMessage();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
