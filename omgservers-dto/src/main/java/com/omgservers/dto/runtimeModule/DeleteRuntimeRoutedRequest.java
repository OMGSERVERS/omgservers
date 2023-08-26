package com.omgservers.dto.runtimeModule;

import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRuntimeRoutedRequest implements RoutedRequest {

    static public void validate(DeleteRuntimeRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long id;

    @Override
    public String getRequestShardKey() {
        return id.toString();
    }
}
