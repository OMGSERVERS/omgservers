package com.omgservers.schema.module.tenant.versionJenkinsRequest;

import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionJenkinsRequestResponse {

    VersionJenkinsRequestModel versionJenkinsRequest;
}
