package com.omgservers.dto.internalModule;

import com.omgservers.dto.RoutedRequest;
import com.omgservers.model.event.EventModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireEventRoutedRequest implements RoutedRequest {

    static public void validate(FireEventRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    EventModel event;

    @Override
    public String getRequestShardKey() {
        return event.getGroupId().toString();
    }
}
