package com.funcas.pboot.common.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author funcas
 * @version 1.0
 * @date 2018年11月29日
 */
public class AuthExceptionSerializer extends StdSerializer<AuthException> {


    protected AuthExceptionSerializer() {
        super(AuthException.class);
    }

    @Override
    public void serialize(AuthException e, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        gen.writeStartObject();
        gen.writeStringField("error", String.valueOf(e.getHttpErrorCode()));
        gen.writeStringField("message", e.getMessage());
        gen.writeStringField("path", request.getServletPath());
        gen.writeStringField("timestamp", String.valueOf(System.currentTimeMillis()));
        if (e.getAdditionalInformation() != null) {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
