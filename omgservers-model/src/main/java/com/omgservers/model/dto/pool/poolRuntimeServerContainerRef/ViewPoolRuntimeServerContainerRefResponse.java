package com.omgservers.model.dto.pool.poolRuntimeServerContainerRef;

import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolRuntimeServerContainerRefResponse {

    List<PoolRuntimeServerContainerRefModel> poolRuntimeServerContainerRefs;
}
