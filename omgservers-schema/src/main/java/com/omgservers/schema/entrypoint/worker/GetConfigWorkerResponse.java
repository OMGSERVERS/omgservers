package com.omgservers.schema.entrypoint.worker;

import com.omgservers.schema.model.version.VersionConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConfigWorkerResponse {

    VersionConfigDto versionConfig;
}
