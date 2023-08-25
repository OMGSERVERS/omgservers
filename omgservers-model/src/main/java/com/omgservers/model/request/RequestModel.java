package com.omgservers.model.request;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    Instant created;
    Instant modified;
    Long userId;
    Long clientId;
    RequestConfigModel config;
}
