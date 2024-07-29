package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVersionMatchmakerRequestsResponse {

    List<VersionMatchmakerRequestModel> versionMatchmakerRequests;
}
