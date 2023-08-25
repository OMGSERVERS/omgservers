package com.omgservers.dto.userModule;

import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenInternalRequest implements InternalRequest {

    static public void validate(CreateTokenInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    @ToString.Exclude
    String password;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
