package implementation.graph;

import java.util.LinkedList;

import implementation.position.LinkedPositionalList;
        import implementation.position.Position;
        import implementation.position.PositionalList;

public class AdjacencyList<V,E> implements Graph<V,E> {
    // ------------------- innerVertex class ---------------------
    class InnerVertex<V> implements Vertex<V> {
        // -------------- Fields ------------------------
        private V element;
        private Position<Vertex<V>> pos;
        LinkedList<Edge<E>> outgoing;
        LinkedList<Edge<E>> incoming;

        // ------------- Constructor --------------------
        public InnerVertex(V elm, boolean graphIsDirected) {
            element = elm;
            outgoing = new LinkedList<>();
            if (graphIsDirected) {
                incoming = new LinkedList<>();
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

        public LinkedList<Edge<E>> getOutgoing() {
            return outgoing;
        }

        public LinkedList<Edge<E>> getIncoming() {
            return incoming;
        }
    }

// ------------------- innerEdge class ---------------------

    class InnerEdge<E> implements Edge<E> {

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
    // -------------- Fields ------------------------

    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices;
    private PositionalList<Edge<E>> edges;

    // -------------- Constructor -----------------------

    public AdjacencyList(boolean directed) {
        isDirected = directed;
        vertices = new LinkedPositionalList<>();
        edges = new LinkedPositionalList<>();
    }
    // ----------------- Functions --------------------

    @Override
    public int numVertices() {
        return vertices.size();
    }

    @Override
    public Iterable<Vertex<V>> vertices() {
        return vertices; //
    }

    @Override
    public int numEdges() {
        return edges.size();
    }
    // ----------------- validation --------------------
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
    //-------------------------------------------------------------------------------------------
    // ----------------------- degree ----------------------
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
        return vert.outgoing;
    }
    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming();
    }
    // -------------------- insertion ---------------------
    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> v = new InnerVertex<V>(element, isDirected);
        v.setPos(vertices.addLast(v));
        return v;
    }
    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        if (getEdge(u, v) != null) {
            throw new IllegalArgumentException("Edge from u to v already exists!");
        }
        InnerEdge<E> edge = new  InnerEdge<>(u, v, element);
        edge.setPos(edges.addLast(edge));
        InnerVertex<V> org = validate(u);
        InnerVertex<V> dst = validate(v);
        org.getOutgoing().add( edge);
        dst.getIncoming().add(edge);
        return edge;
    }
    // -------------------------------------------------------------------------------------
    @Override
    public Edge<E> getEdge(Vertex<V> v, Vertex<V> u) {
        InnerVertex<V> first = validate(v);
        for (Edge<E> pointer : first.getIncoming()) {
           // if (pointer instanceof InnerEdge<E> p){
                if(((InnerEdge<E>) pointer).getEndpoints()[0]==u)
                    return pointer;
            //}
        }
        for (Edge<E> pointer : first.getOutgoing()) {
            //if (pointer instanceof InnerEdge<E>){
                if(((InnerEdge<E>) pointer).getEndpoints()[1]==u)
                    return pointer;
          //  }
        }
        return null;
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
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        InnerVertex<V> org = validate(endpoints[0]);
        InnerVertex<V> dst = validate(endpoints[1]);
        org.getOutgoing().remove(edge);
        dst.getIncoming().remove(endpoints[1]);
        edges.remove(edge.getPos());
    }
    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> vert = validate(v);
        for (Edge<E> e : vert.getOutgoing()) {
            removeEdge(e);
        }
        for (Edge<E> e : vert.getIncoming()) {
            removeEdge(e);
        }
        vertices.remove(vert.getPos());
    }
}