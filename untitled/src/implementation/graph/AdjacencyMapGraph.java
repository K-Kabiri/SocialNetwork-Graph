package implementation.graph;

import implementation.position.LinkedPositionalList;
import implementation.position.Position;
import implementation.position.PositionalList;
import linkedIn.model.Connection;
import linkedIn.model.User;

import java.util.*;

public class AdjacencyMapGraph<V, E> implements Graph<V, E> {
    // ------------------- innerVertex class ---------------------
    private class InnerVertex<V> implements Vertex<V> {
        // -------------- Fields ------------------------
        private V element;
        private Position<Vertex<V>> pos;
        private Map<Vertex<V>, Edge<E>> outgoing, incoming;

        // ------------- Constructor --------------------
        public InnerVertex(V elm, boolean graphIsDirected) {
            element = elm;
            outgoing = new HashMap<>();
            if (graphIsDirected) {
                incoming = new HashMap<>();
            } else {
                incoming = outgoing;
            }
        }

        // -------------- Setter & Getter ----------------
        @Override
        public V getElement() {
            return element;
        }

        @Override
        public void setElement(V elem) {
            element = elem;
        }

        public void setPos(Position<Vertex<V>> pos) {
            this.pos = pos;
        }

        public Position<Vertex<V>> getPos() {
            return pos;
        }

        public Map<Vertex<V>, Edge<E>> getOutgoing() {
            return outgoing;
        }

        public Map<Vertex<V>, Edge<E>> getIncoming() {
            return incoming;
        }
    }

    // ------------------- innerEdge class ---------------------

    private class InnerEdge<E> implements Edge<E> {

        // -------------- Fields----------------
        private E element;
        private Position<Edge<E>> pos;
        private Vertex<V>[] endpoints;

        // -------------- Constructor ----------------

        public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
            element = elem;
            endpoints = (Vertex<V>[]) new Vertex[]{u, v};
        }

        // -------------- Setter & Getter ----------------
        @Override
        public E getElement() {
            return element;
        }

        @Override
        public void setElement(E elem) {
            element = elem;
        }

        public Vertex<V>[] getEndpoints() {
            return endpoints;
        }

        public Position<Edge<E>> getPos() {
            return pos;
        }

        public void setPos(Position<Edge<E>> pos) {
            this.pos = pos;
        }
    }
    //===============================================
    // -------------- Fields ------------------------
    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices;
    private PositionalList<Edge<E>> edges;

    // -------------- Constructor -----------------------

    public AdjacencyMapGraph(boolean directed) {
        isDirected = directed;
        vertices = new LinkedPositionalList<>();
        edges = new LinkedPositionalList<>();
    }

    // ----------------- Methods -------------------------

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices;
    }

    @Override
    public int numEdges() {
        return edges.size();
    }

    @Override
    public PositionalList<Edge<E>> edges() {
        return edges;
    }

    @Override
    public int outDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }

    @Override
    public int inDegree(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values();
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().values();
    }

    @Override
    public Edge<E> getEdge(Vertex<V> v, Vertex<V> u) {
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v);
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if (endpoints[0] == v) {
            return endpoints[1];
        } else if (endpoints[1] == v) {
            return endpoints[0];
        } else {
            throw new IllegalArgumentException("v is not incident to this edge!");
        }
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<>(element, isDirected);
        v.setPos(vertices.addLast(v));
        return v;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) != null) {
            throw new IllegalArgumentException("Edge from u to v already exists!");
        }
        InnerEdge<E> edge = new InnerEdge<>(u, v, element);
        edge.setPos(edges.addLast(edge));
        InnerVertex<V> org = validate(u);
        InnerVertex<V> dst = validate(v);
        org.getOutgoing().put(v, edge);
        dst.getIncoming().put(u, edge);
        return edge;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        for (Edge<E> e : vert.getOutgoing().values()) {
            removeEdge(e);
        }
        for (Edge<E> e : vert.getIncoming().values()) {
            removeEdge(e);
        }
        vertices.remove(vert.getPos());
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        InnerVertex<V> org = validate(endpoints[0]);
        InnerVertex<V> dst = validate(endpoints[1]);
        org.getOutgoing().remove(endpoints[0], edge);
        org.getIncoming().remove(endpoints[1], edge);
        edges.remove(edge.getPos());
    }

    private InnerVertex<V> validate(Vertex<V> v) throws IllegalArgumentException {
        //if (!(v instanceof InnerVertex<V> vert)) throw new IllegalArgumentException("invalid vertex");
        //if(!vertices.contains(vert)) throw new IllegalArgumentException("vertex is not in the list");
        return (InnerVertex<V>) v;
    }

    private InnerEdge<E> validate(Edge<E> e) throws IllegalArgumentException {
        //if (!(e instanceof InnerEdge<E> edge)) throw new IllegalArgumentException("invalid edge");
        //if(!edges.contains(edge)) throw new IllegalArgumentException("edge is not in the list");
        return (InnerEdge<E>) e;
    }

    /*
      performs DFS for the entire graph and returns DFS forest as a map
   */
    public Map<Vertex<V>, Edge<E>> DFSComplete() {
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>, Edge<E>> forest = new HashMap<>();
        for (Vertex<V> vertex : this.vertices()) {
            if (!known.contains(vertex)) {
                // restart the DFS process at vertex
                DFS( vertex, known, forest);
            }
        }
        return forest;
    }

     /*
       performs depth-first search of graph starting at vertex
     */
    public void DFS(Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest) {
        // u has been discovered
        known.add(u);
        // for every outgoing edge from u
        for (Edge<E> edge : this.outgoingEdges(u)) {
            Vertex<V> vertex = this.opposite(u, edge);
            if (!known.contains(vertex)) {
                // edge is the tree edge that discovered vertex
                forest.put(vertex, edge);
                // recursively explore from vertex
                DFS(vertex, known, forest);
            }
        }
    }

    /*
        performs breath-first search of graph starting at vertex
     */

    public void BFS(Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Integer> connections) {
        PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
        known.add(u);
        int index = 1 ;
        // first level includes only u
        level.addLast(u);
        while (!level.isEmpty() && index<=5) {
            PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
            for (Vertex<V> vertex : level) {
                for (Edge<E> edge : this.outgoingEdges(vertex)) {
                    Vertex<V>v = this.opposite(vertex, edge);
                    if (!known.contains(v)) {
                        known.add(v);
                        // edge is the tree edge that discovered v
                        connections.put(v, index);
                        // v will be further considered in next pass
                        nextLevel.addLast(v);
                    }
                }
                level = nextLevel;
            }
            index++;
        }
    }
    public PositionalList<Edge<E>> constructPath(Vertex<V> u, Vertex<V> v, Map<Vertex<V>, Edge<E>> forest) {
        PositionalList<Edge<E>> path = new LinkedPositionalList<>();
        // v was discovered during the search
        if (forest.get(v) != null) {
            // we construct the path from back to front
            Vertex<V> walk = v;
            while (walk != null) {
                Edge<E> edge = forest.get(walk);
                // add edge to the front of path
                path.addFirst(edge);
                // repeat with opposite endpoint
                walk = this.opposite(walk, edge);
            }
        }
        return path;
    }
}