package com.omgservers.dto.userModule;

import com.omgservers.model.client.ClientModel;
import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncClientRoutedRequest implements RoutedRequest {

    static public void validate(SyncClientRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    ClientModel client;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
