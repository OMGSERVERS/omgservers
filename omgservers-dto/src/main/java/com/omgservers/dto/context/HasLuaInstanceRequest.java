package com.omgservers.dto.context;

import com.omgservers.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HasLuaInstanceRequest {

    public static void validate(HasLuaInstanceRequest request) {
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }
        // TODO: validate fields
    }

    Long id;
}