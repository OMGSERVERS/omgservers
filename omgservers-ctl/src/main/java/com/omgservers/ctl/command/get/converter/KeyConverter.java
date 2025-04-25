package com.omgservers.ctl.command.get.converter;

import com.omgservers.ctl.dto.key.KeyEnum;
import picocli.CommandLine;

public class KeyConverter implements CommandLine.ITypeConverter<KeyEnum> {
    @Override
    public KeyEnum convert(final String value) throws Exception {
        return KeyEnum.fromString(value);
    }
}
