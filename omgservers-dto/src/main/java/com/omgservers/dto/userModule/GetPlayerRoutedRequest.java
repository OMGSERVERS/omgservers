package com.omgservers.dto.userModule;

import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlayerRoutedRequest implements RoutedRequest {

    static public void validate(GetPlayerRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    Long stageId;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
