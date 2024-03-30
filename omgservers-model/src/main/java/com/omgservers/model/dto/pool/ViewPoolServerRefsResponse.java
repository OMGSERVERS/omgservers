package com.omgservers.model.dto.pool;

import com.omgservers.model.poolServerRef.PoolServerRefModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolServerRefsResponse {

    List<PoolServerRefModel> poolServerRefs;
}
