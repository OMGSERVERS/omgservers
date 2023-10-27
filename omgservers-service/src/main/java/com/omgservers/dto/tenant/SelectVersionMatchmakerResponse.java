package com.omgservers.dto.tenant;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectVersionMatchmakerResponse {

    VersionMatchmakerModel versionMatchmaker;
}
