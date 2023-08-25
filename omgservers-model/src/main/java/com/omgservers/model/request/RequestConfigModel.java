package com.omgservers.model.request;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestConfigModel {

    static public RequestConfigModel create(final String mode) {
        if (mode == null) {
            throw new ServerSideBadRequestException("mode is null");
        }

        final var config = new RequestConfigModel();
        config.setMode(mode);
        config.setAttributes(new HashMap<>());
        return config;
    }

    static public void validate(RequestConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    String mode;
    Map<String, String> attributes;
}
