package com.omgservers.application.module.matchmakerModule.model.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {

    static public RequestModel create(UUID matchmaker, RequestConfigModel config) {
        return create(matchmaker, UUID.randomUUID(), config);
    }

    static public RequestModel create(UUID matchmaker,
                                      UUID uuid,
                                      RequestConfigModel config) {
        Instant now = Instant.now();

        final var request = new RequestModel();
        request.setMatchmaker(matchmaker);
        request.setCreated(now);
        request.setUuid(uuid);
        request.setConfig(config);
        return request;
    }

    static public void validate(RequestModel matchRequest) {
        if (matchRequest == null) {
            throw new ServerSideBadRequestException("matchRequest is null");
        }
    }

    UUID matchmaker;
    @ToString.Exclude
    Instant created;
    UUID uuid;
    RequestConfigModel config;
}
