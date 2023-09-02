package com.omgservers.dto.tenant;

import com.omgservers.model.version.VersionBytecodeModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetVersionBytecodeShardedResponse {

    VersionBytecodeModel bytecode;
}
