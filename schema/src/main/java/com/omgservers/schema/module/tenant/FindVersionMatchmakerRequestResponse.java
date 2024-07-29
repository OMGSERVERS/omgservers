package com.omgservers.schema.module.tenant;

import com.omgservers.schema.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionMatchmakerRequestResponse {

    VersionMatchmakerRequestModel versionMatchmakerRequest;
}
