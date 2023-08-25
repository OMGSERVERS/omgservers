package com.omgservers.dto.versionModule;

import com.omgservers.model.version.VersionBytecodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBytecodeInternalResponse {

    VersionBytecodeModel bytecode;
}
