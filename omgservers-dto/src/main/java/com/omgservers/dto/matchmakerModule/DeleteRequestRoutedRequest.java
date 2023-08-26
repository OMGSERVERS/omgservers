package com.omgservers.dto.matchmakerModule;

import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRequestRoutedRequest implements RoutedRequest {

    static public void validate(DeleteRequestRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long matchmakerId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
