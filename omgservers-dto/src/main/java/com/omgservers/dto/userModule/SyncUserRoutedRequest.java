package com.omgservers.dto.userModule;

import com.omgservers.dto.RoutedRequest;
import com.omgservers.model.user.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncUserRoutedRequest implements RoutedRequest {

    static public void validate(SyncUserRoutedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    UserModel user;

    @Override
    public String getRequestShardKey() {
        return user.getId().toString();
    }
}
