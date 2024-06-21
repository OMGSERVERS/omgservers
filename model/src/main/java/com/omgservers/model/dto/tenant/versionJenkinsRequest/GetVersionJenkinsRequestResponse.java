package com.omgservers.model.dto.tenant.versionJenkinsRequest;

import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionJenkinsRequestResponse {

    VersionJenkinsRequestModel versionJenkinsRequest;
}
