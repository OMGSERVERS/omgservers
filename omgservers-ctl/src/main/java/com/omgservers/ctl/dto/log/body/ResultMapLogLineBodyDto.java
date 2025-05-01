package com.omgservers.ctl.dto.log.body;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.omgservers.ctl.dto.key.KeyEnum;
import com.omgservers.ctl.dto.log.LogLineBodyDto;
import com.omgservers.ctl.dto.log.LogLineQualifierEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResultMapLogLineBodyDto extends LogLineBodyDto {

    @JsonValue
    Map<KeyEnum, String> map;

    @Override
    public LogLineQualifierEnum getQualifier() {
        return LogLineQualifierEnum.RESULT_MAP;
    }

    @JsonCreator
    public ResultMapLogLineBodyDto(final Map<KeyEnum, String> map) {
        this.map = map;
    }
}
