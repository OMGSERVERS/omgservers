package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionMatchmakerRefResponse {

    VersionMatchmakerRefModel versionMatchmakerRef;
}
