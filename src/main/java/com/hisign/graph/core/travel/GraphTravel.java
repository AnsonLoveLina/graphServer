package com.hisign.graph.core.travel;

import com.google.common.collect.Sets;
import com.hisign.common.util.VertexUtil;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import java.util.*;

public class GraphTravel {
    private Graph graph;

    public final static String indexKey = "INDEX";

    public final static String inEdgeIndexKey = "inEdges";

    public final static String outEdgeIndexKey = "outEdges";

    public GraphTravel(Graph graph) {
        this.graph = graph;
    }

    private class Node {
        private Vertex vertex;

        public Node(Vertex vertex) {
            this.vertex = vertex;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public Object getId() {
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

    public class IndexObject {
        private Integer index;

        public IndexObject(Integer index) {
            this.index = index;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IndexObject that = (IndexObject) o;

            return index != null ? index.equals(that.index) : that.index == null;
        }

        @Override
        public int hashCode() {
            return index != null ? index.hashCode() : 0;
        }
    }

    public class EdgeObject implements Comparable {
        private Edge edge;

        public EdgeObject(Edge edge) {
            this.edge = edge;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EdgeObject that = (EdgeObject) o;

            return edge != null ? edge.equals(that.edge) : that.edge == null;
        }

        @Override
        public int hashCode() {
            return edge != null ? edge.hashCode() : 0;
        }

        public Edge getEdge() {
            return edge;
        }

        @Override
        public int compareTo(Object o) {
            if (this == o || this.equals(o)) return 0;
            if (o == null || getClass() != o.getClass()) return 0;

            EdgeObject that = (EdgeObject) o;
            if (that.getEdge().<Integer>value(GraphTravel.indexKey) < getEdge().<Integer>value(GraphTravel.indexKey)) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * o(2(v+e))
     *
     * @param vertexTravel
     */
    public void travelVertex(IVertexTravel vertexTravel) {
        Iterator<Vertex> iterator = graph.vertices();
//        Map<Node,Integer> nodeIndex = Maps.newHashMap();
        Set<Vertex> nodes = Sets.newLinkedHashSet();
        Set<Edge> edges = Sets.newLinkedHashSet();
        int edgeIndex = 0;
        for (int outIndex = 0; iterator.hasNext(); outIndex++) {
            Vertex outVertex = iterator.next();
            outVertex.property(GraphTravel.indexKey, outIndex);
            nodes.add(outVertex);

            Iterator<Edge> edgeOutIterator = outVertex.edges(Direction.OUT);
            while (edgeOutIterator.hasNext()) {
                Edge edge = edgeOutIterator.next();
                if (!edges.contains(edge)) {
                    edge.property(GraphTravel.indexKey, edgeIndex);
                    edges.add(edge);

                    Vertex inVertex = edge.inVertex();
                    VertexUtil.putValue(outVertex, GraphTravel.outEdgeIndexKey, new IndexObject(edgeIndex));
                    VertexUtil.putValue(inVertex, GraphTravel.inEdgeIndexKey, new IndexObject(edgeIndex));
                    edgeIndex++;
                }
//                Vertex inVertex = edge.inVertex();
//                Integer inIndex = VertexUtil.getValue(inVertex,GraphTravel.indexKey,null);
////                Integer inIndex = nodeIndex.get(new Node(inVertex));
//                vertexTravel.outEdgeCallBack(outVertex,outIndex,inVertex,inIndex,edge);
            }
        }

        for (Edge edge : edges) {
            Vertex outVertex = edge.outVertex();
            Integer outIndex = VertexUtil.getValue(outVertex, GraphTravel.indexKey, null);
            Vertex inVertex = edge.inVertex();
            Integer inIndex = VertexUtil.getValue(inVertex, GraphTravel.indexKey, null);
            vertexTravel.edgeCallBack(outVertex, outIndex, inVertex, inIndex, edge);
        }

        for (Vertex vertex : nodes) {
            Integer index = VertexUtil.getValue(vertex, GraphTravel.indexKey, null);
            vertexTravel.nodeCallBack(vertex, index);
        }

//        for (Map.Entry<Node,Integer> entry:nodeIndex.entrySet()){
//            Vertex outVertex = entry.getKey().getVertex();
//            Integer outIndex = entry.getValue();
//            Iterator<Edge> edgeOutIterator = outVertex.edges(Direction.OUT);
//            while (edgeOutIterator.hasNext()){
//                Edge edge = edgeOutIterator.next();
//                Vertex inVertex = edge.inVertex();
//                Integer inIndex = nodeIndex.get(new Node(inVertex));
//                vertexTravel.outEdgeCallBack(outVertex,outIndex,inVertex,inIndex,edge);
//            }
//        }
//        nodeIndex = null;
    }
}
