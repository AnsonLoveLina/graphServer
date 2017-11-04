package com.hisign.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hisign.common.resource.ASpringContextInit;
import com.hisign.common.resource.GraphRelation;
import com.hisign.controller.decorate.impl.JSONDecorators;
import com.hisign.graph.core.travel.GraphTravel;
import com.hisign.graph.search.IGraphSearcher;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class GraphController {

    @Resource(name = "graph.graphSearcher")
    IGraphSearcher iGraphSearcher;

    @RequestMapping(value = "/getGraph", method = RequestMethod.GET)
    public String getGraph(String startNodeValue, String startNodeType,@RequestParam(defaultValue = "true") boolean detail) {
        ApplicationContext context = ASpringContextInit.getContext();
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
                iGraphSearcher.search(graph, detail, relation, conditionSql);
            }
        }

        String jsonStr = getJsonStr(graph);
        return jsonStr;
    }

    public String getJsonStr(Graph graph){
        GraphTravel graphTravel = new GraphTravel(graph);
        JSONDecorators jsonDecorators = new JSONDecorators();
        graphTravel.travelVertex(jsonDecorators.getIVertexTravel());
        JSONObject jsonObject = jsonDecorators.getJSONObject();
        String jsonStr = JSON.toJSONString(jsonObject);
        return jsonStr;
    }
}
