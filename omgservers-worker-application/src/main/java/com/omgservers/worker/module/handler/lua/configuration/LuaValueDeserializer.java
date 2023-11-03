package com.omgservers.worker.module.handler.lua.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class LuaValueDeserializer extends StdDeserializer<LuaValue> {

    LuaValueDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public LuaValue deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        JsonNode root = jsonParser.getCodec().readTree(jsonParser);
        return decode(root, new LuaTable());
    }

    LuaValue decode(JsonNode node, LuaValue parent) {
        if (node.isArray()) {
            Iterator<JsonNode> iterator = node.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                JsonNode next = iterator.next();
                if (next.isContainerNode()) {
                    parent.set(++index, decode(next, new LuaTable()));
                } else {
                    parent.set(++index, decode(next));
                }
            }
            return parent;
        } else if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> field = iterator.next();
                String key = field.getKey();
                JsonNode value = field.getValue();
                if (value.isContainerNode()) {
                    parent.set(key, decode(value, new LuaTable()));
                } else {
                    parent.set(key, decode(value));
                }
            }
            return parent;
        } else {
            return new LuaTable();
        }
    }

    LuaValue decode(JsonNode node) {
        if (node.isIntegralNumber()) {
            return LuaValue.valueOf(node.longValue());
        } else if (node.isFloatingPointNumber()) {
            return LuaValue.valueOf(node.doubleValue());
        } else if (node.isTextual()) {
            return LuaValue.valueOf(node.textValue());
        } else if (node.isBoolean()) {
            return LuaValue.valueOf(node.booleanValue());
        } else {
            return LuaValue.NIL;
        }
    }
}
