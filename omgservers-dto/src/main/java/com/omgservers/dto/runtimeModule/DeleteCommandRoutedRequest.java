package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCommandRoutedRequest implements RoutedRequest {

    static public void validate(DeleteCommandRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long runtimeId;
    Long id;

    @Override
    public String getRequestShardKey() {
        return runtimeId.toString();
    }
}
