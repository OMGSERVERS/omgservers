package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsRequest implements ShardedRequest {

    public static void validate(ValidateCredentialsRequest request) {
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
