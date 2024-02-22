package com.omgservers.model.dto.tenant;

import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
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
