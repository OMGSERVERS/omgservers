package com.omgservers.model.dto.tenant;

import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionMatchmakerRequestResponse {

    VersionMatchmakerRequestModel versionMatchmakerRequest;
}
