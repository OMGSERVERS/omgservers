package com.omgservers.dto.version;

import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionShardedRequest implements ShardedRequest {

    static public void validate(GetVersionShardedRequest request) {
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
