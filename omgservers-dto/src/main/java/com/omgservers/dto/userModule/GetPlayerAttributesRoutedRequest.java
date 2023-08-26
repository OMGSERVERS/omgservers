package com.omgservers.dto.userModule;

import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerAttributesRoutedRequest implements RoutedRequest {

    static public void validate(GetPlayerAttributesRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long playerId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
