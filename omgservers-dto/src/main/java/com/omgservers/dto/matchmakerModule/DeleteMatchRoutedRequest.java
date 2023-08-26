package com.omgservers.dto.matchmakerModule;

import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMatchRoutedRequest implements RoutedRequest {

    static public void validate(DeleteMatchRoutedRequest request) {
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
