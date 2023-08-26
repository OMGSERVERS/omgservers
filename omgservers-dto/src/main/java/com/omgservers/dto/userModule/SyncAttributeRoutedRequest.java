package com.omgservers.dto.userModule;

import com.omgservers.model.attribute.AttributeModel;
import com.omgservers.dto.RoutedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncAttributeRoutedRequest implements RoutedRequest {

    static public void validate(SyncAttributeRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    Long userId;
    AttributeModel attribute;

    @Override
    public String getRequestShardKey() {
        return userId.toString();
    }
}
