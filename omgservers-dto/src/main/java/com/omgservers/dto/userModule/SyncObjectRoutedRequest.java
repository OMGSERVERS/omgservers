package com.omgservers.dto.userModule;

import com.omgservers.dto.RoutedRequest;
import com.omgservers.model.object.ObjectModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncObjectRoutedRequest implements RoutedRequest {

    static public void validate(SyncObjectRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    ObjectModel object;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
