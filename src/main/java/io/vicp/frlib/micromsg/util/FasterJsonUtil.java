package io.vicp.frlib.micromsg.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * Created by zhoudr on 2016/12/27.
 */
public class FasterJsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        return objectMapper;
    }

    /**
     * JSON序列化
     */
    public static String writeValueAsString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch(IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
