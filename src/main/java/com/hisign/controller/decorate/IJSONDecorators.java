package com.hisign.controller.decorate;

import com.alibaba.fastjson.JSONObject;
import com.hisign.graph.core.travel.IVertexTravel;

public interface IJSONDecorators {
    public IVertexTravel getIVertexTravel();
    public JSONObject getJSONObject();
}
