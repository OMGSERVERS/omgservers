package com.omgservers.dto.userModule;

import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCredentialsRoutedRequest implements RoutedRequest {

    static public void validate(ValidateCredentialsRoutedRequest request) {
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
