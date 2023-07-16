package com.omgservers.application.module.versionModule.impl.service.versionInternalService.response;

import com.omgservers.application.module.versionModule.model.VersionBytecodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBytecodeInternalResponse {

    VersionBytecodeModel bytecode;
}
