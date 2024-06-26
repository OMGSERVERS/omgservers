package com.omgservers.model.dto.tenant;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindVersionMatchmakerRefResponse {

    VersionMatchmakerRefModel versionMatchmakerRef;
}
