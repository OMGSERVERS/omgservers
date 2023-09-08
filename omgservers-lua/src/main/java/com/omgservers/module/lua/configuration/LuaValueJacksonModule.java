package com.omgservers.module.lua.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;

@Slf4j
@ApplicationScoped
public class LuaValueJacksonModule implements ObjectMapperCustomizer {

    public void customize(ObjectMapper mapper) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LuaValue.class, new LuaValueSerializer(LuaValue.class));
        simpleModule.addDeserializer(LuaValue.class, new LuaValueDeserializer(LuaValue.class));
        log.info("LuaValue serializer/deserializer was installed");
        mapper.registerModule(simpleModule);
    }
}