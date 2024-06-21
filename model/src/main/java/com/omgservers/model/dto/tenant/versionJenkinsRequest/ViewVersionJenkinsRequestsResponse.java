package com.omgservers.model.dto.tenant.versionJenkinsRequest;

import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
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
