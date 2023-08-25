package com.omgservers.dto.userModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsInternalRequest implements InternalRequest {

    static public void validate(ValidateCredentialsInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long tenantId;
    Long userId;
    String password;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
