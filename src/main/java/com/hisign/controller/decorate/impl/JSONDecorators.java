package com.hisign.controller.decorate.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hisign.common.util.EasyJSONUtil;
import com.hisign.common.util.VertexUtil;
import com.hisign.controller.decorate.IJSONDecorators;
import com.hisign.graph.core.travel.GraphTravel;
import com.hisign.graph.core.travel.IVertexTravel;
import jodd.util.StringUtil;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JSONDecorators implements IJSONDecorators {

    private JSONArray nodesJson = new JSONArray();

    private JSONArray linksJson = new JSONArray();

    @Override
    public IVertexTravel getIVertexTravel() {
        return new IVertexTravel() {

            @Override
            public void edgeCallBack(Vertex outVertex, Integer outIndex, Vertex inVertex, Integer inIndex, Edge edge) {
                JSONObject edgeValue = new JSONObject();
                edgeValue.put("source", outIndex);
                edgeValue.put("target", inIndex);
                Set<String> properties = edge.keys();
                for (String key : properties) {
                    if (StringUtil.isEmpty(key)) {
                        continue;
                    }
                    edgeValue.put(key, edge.value(key));
                }
                linksJson.add(edgeValue);
            }

            @Override
            public void nodeCallBack(Vertex vertex, int index) {
                JSONObject jsonValue = new JSONObject();
                jsonValue.put("id", vertex.id());
                jsonValue.put("type", vertex.label());
                //需要初始化图片
                jsonValue.put("image", vertex.label() + ".jpg");
                Set<String> properties = vertex.keys();
                for (String key : properties) {
                    if (StringUtil.isEmpty(key)) {
                        continue;
                    }
                    Object value = vertex.value(key);
                    if (value instanceof Collection){
                        Object[] values = ((Collection)value).toArray();
                        JSONArray jsonArray = new JSONArray();
                        for (Object object:values){
                            jsonArray.add(object);
                        }
                        value = jsonArray;
                    }
                    jsonValue.put(key, value);
                }
                nodesJson.add(jsonValue);
            }
        };
    }

    @Override
    public JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodes", nodesJson);
        jsonObject.put("edges", linksJson);
        return jsonObject;
    }
}
