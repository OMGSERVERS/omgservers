package com.omgservers.dto.user;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenRequest implements ShardedRequest {

    public static void validate(CreateTokenRequest request) {
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
