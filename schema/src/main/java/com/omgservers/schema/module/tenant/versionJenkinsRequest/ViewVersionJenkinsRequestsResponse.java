package com.omgservers.schema.module.tenant.versionJenkinsRequest;

import com.omgservers.schema.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionJenkinsRequestsResponse {

    List<VersionJenkinsRequestModel> versionJenkinsRequests;
}
