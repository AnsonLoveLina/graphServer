package com.hisign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.hisign.common.init.SpringContextInit;
import com.hisign.common.resource.GraphRelation;
import com.hisign.common.util.EasyJSONUtil;
import com.hisign.controller.decorate.impl.JSONDecorators;
import com.hisign.graph.core.travel.GraphTravel;
import com.hisign.graph.core.travel.IVertexTravel;
import com.hisign.graph.search.IGraphSearcher;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
public class GraphController {

    @Resource(name = "graph.graphSearcher")
    IGraphSearcher iGraphSearcher;

    @RequestMapping(value = "/getGraph", method = RequestMethod.GET)
    public String getGraph(String startNodeValue, String startNodeType) {

        ApplicationContext context = SpringContextInit.getContext();
        Map<String, GraphRelation> relationMap = context.getBeansOfType(GraphRelation.class);
        Graph graph = TinkerGraph.open();
        String conditionSql = "";
        if ("pgroupid".equals(startNodeType)) {
            conditionSql = String.format(" and (%s = '%s' or groupid='%s')", startNodeType, startNodeValue, startNodeValue);
        } else if ("groupid".equals(startNodeType)) {
            conditionSql = String.format(" and %s = '%s'", startNodeType, startNodeValue);
        }
        for (GraphRelation relation : relationMap.values()) {
            if (!startNodeType.toString().equals(relation.getTypeDelete())) {
                iGraphSearcher.search(graph, false, relation, conditionSql);
            }
        }

        GraphTravel graphTravel = new GraphTravel(graph);
        JSONDecorators jsonDecorators = new JSONDecorators();
        graphTravel.travelVertex(jsonDecorators.getIVertexTravel());
        JSONObject jsonObject = jsonDecorators.getJSONObject();
        String jsonStr = JSON.toJSONString(jsonObject);
        return jsonStr;
    }
}
