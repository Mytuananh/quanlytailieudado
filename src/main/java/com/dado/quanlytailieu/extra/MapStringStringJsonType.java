package com.dado.quanlytailieu.extra;


import com.dado.quanlytailieu.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class MapStringStringJsonType extends AbstractJsonType<Map<String, String>>  {

    public MapStringStringJsonType() {
        super(JsonUtil.TYPE_MAP_STRING_STRING, HashMap::new);
    }
}
