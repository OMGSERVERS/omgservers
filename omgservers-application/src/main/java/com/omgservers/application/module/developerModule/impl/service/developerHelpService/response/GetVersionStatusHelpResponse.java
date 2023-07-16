package com.omgservers.application.module.developerModule.impl.service.developerHelpService.response;

import com.omgservers.application.module.versionModule.model.VersionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionStatusHelpResponse {

    VersionStatusEnum status;
}
