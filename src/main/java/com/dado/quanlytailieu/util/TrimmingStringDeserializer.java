package com.dado.quanlytailieu.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import java.io.IOException;

public class TrimmingStringDeserializer extends StringDeserializer {

    @Override
    public String deserialize(final JsonParser p, final DeserializationContext ctxt)
            throws IOException {
        final String str = super.deserialize(p, ctxt);
        return str == null ? null : str.trim();
    }
}
