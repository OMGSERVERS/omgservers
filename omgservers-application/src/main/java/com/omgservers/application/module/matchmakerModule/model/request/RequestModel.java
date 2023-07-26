package com.omgservers.application.module.matchmakerModule.model.request;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestModel {

    static public void validate(RequestModel matchRequest) {
        if (matchRequest == null) {
            throw new ServerSideBadRequestException("matchRequest is null");
        }
    }

    Long id;
    Long matchmakerId;
    @ToString.Exclude
    Instant created;
    RequestConfigModel config;
}
