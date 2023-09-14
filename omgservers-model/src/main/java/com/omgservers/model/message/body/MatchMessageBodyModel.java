package com.omgservers.model.message.body;

import com.omgservers.model.message.MessageBodyModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchMessageBodyModel extends MessageBodyModel {

    Object data;
}
