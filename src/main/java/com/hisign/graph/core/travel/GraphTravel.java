package com.hisign.graph.core.travel;

import com.google.common.collect.Maps;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.Iterator;
import java.util.Map;

public class GraphTravel {
    private Graph graph;

    public GraphTravel(Graph graph) {
        this.graph = graph;
    }

    private class Node{
        private Vertex vertex;

        public Node(Vertex vertex) {
            this.vertex = vertex;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public Object getId(){
            return vertex.id();
        }

        @Override
        public int hashCode() {
            return getId().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) {
                return false;
            }
            if (obj == null) {
                return false;
            }

            Node target = (Node) obj;
            return getId().equals(target.getId());
        }
    }

    public void travelVertex(IVertexTravel vertexTravel){
        Iterator<Vertex> iterator = graph.vertices();
        Map<Node,Integer> nodeIndex = Maps.newHashMap();
        for (int index=0; iterator.hasNext();index++ ) {
            Vertex vertex = iterator.next();
            vertexTravel.nodeCallBack(vertex,index);
            nodeIndex.put(new Node(vertex),index);
        }
        for (Map.Entry<Node,Integer> entry:nodeIndex.entrySet()){
            Vertex outVertex = entry.getKey().getVertex();
            Integer outIndex = entry.getValue();
            Iterator<Edge> edgeIterator = outVertex.edges(Direction.OUT);
            while (edgeIterator.hasNext()){
                Edge edge = edgeIterator.next();
                Vertex inVertex = edge.inVertex();
                Integer inIndex = nodeIndex.get(new Node(inVertex));
                vertexTravel.outEdgeCallBack(outVertex,outIndex,inVertex,inIndex,edge);
            }
        }
        nodeIndex = null;
    }
}
