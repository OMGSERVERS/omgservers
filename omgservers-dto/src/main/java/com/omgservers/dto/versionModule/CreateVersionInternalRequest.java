package com.omgservers.dto.versionModule;

import com.omgservers.model.version.VersionModel;
import com.omgservers.dto.InternalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateVersionInternalRequest implements InternalRequest {

    static public void validate(CreateVersionInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    VersionModel version;

    @Override
    public String getRequestShardKey() {
        return version.getId().toString();
    }
}
