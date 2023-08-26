package com.omgservers.dto.userModule;

import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsShardRequest implements ShardRequest {

    static public void validate(ValidateCredentialsShardRequest request) {
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
