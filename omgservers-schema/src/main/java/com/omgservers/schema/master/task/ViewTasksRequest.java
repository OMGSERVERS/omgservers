package com.omgservers.schema.master.task;

import com.omgservers.schema.master.MasterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTasksRequest implements MasterRequest {

    URI uri;
}
