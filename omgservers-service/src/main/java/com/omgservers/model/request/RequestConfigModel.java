package com.omgservers.model.request;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.player.PlayerAttributesModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestConfigModel {

    static public RequestConfigModel create(PlayerAttributesModel attributes) {
        final var config = new RequestConfigModel();
        config.setAttributes(attributes);
        return config;
    }

    public static void validate(RequestConfigModel config) {
        if (config == null) {
            throw new ServerSideBadRequestException("config is null");
        }
    }

    @NotNull
    PlayerAttributesModel attributes;
}
