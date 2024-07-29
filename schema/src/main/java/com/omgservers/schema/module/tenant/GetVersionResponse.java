package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.version.VersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionResponse {

    VersionModel version;
}
