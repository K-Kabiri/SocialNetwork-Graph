package graph;

import position.LinkedPositionalList;
import position.Position;
import position.PositionalList;

import java.util.HashMap;
import java.util.Map;

public class EdgeList<V, E> implements Graph<V, E> {

    // ------------------- innerVertex class ---------------------
    private class InnerVertex<V> implements Vertex<V> {
        private V element;
        private Position<Vertex<V>> pos;

        // ------------- Constructor --------------------
        public InnerVertex(V elm, boolean graphIsDirected) {
            element = elm;
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

    // -------------- Fields ------------------------
    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices;
    private PositionalList<Edge<E>> edges;

    // -------------- Constructor -----------------------

    public EdgeList(boolean directed) {
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
        int outDegree = 0;
        if (!isDirected) {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && ((edge.endpoints[0].equals(v)) || (edge.endpoints[1].equals(v))))
                    outDegree++;
            }
        } else {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && (edge.endpoints[0].equals(v)))
                    outDegree++;
            }
        }
        return outDegree;
    }

    @Override
    public int inDegree(Vertex<V> v) {
        int inDegree = 0;
        if (!isDirected) {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && ((edge.endpoints[0].equals(v)) || (edge.endpoints[1].equals(v))))
                    inDegree++;
            }
        } else {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && (edge.endpoints[1].equals(v)))
                    inDegree++;
            }
        }
        return inDegree;

    }

    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) {
        PositionalList<Edge<E>> out = new LinkedPositionalList<>();
        if (!isDirected) {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && ((edge.endpoints[0].equals((v)) || (edge.endpoints[1].equals(v)))))
                    out.addLast(e);
            }
        } else {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && (edge.endpoints[0].equals(v)))
                    out.addLast(e);
            }
        }
        return out;
    }

    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) {
        PositionalList<Edge<E>> in = new LinkedPositionalList<>();
        if (!isDirected) {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && ((edge.endpoints[0].equals(v)) || (edge.endpoints[1].equals(v))))
                    in.addLast(e);
            }
        } else {
            for (Edge<E> e : edges) {
                if ((e instanceof InnerEdge<E> edge) && (edge.endpoints[1].equals(v)))
                    in.addLast(e);
            }
        }
        return in;
    }

    @Override
    public Edge<E> getEdge(Vertex<V> v, Vertex<V> u) {
        // search in edges list to find the edge with inputs vertex.
        if (!isDirected) {
            for (Edge<E> e : this.edges) {
                if ((e instanceof InnerEdge<E> edge)) {
                    if ((edge.endpoints[0].equals(v) && edge.endpoints[1].equals(u)) || (edge.endpoints[0].equals(u) && edge.endpoints[1].equals(v))) {
                        return edge;
                    }
                }
            }
        } else {
            for (Edge<E> e : this.edges) {
                if ((e instanceof InnerEdge<E> edge)) {
                    if (edge.endpoints[0].equals(v) && edge.endpoints[1].equals(u)) {
                        return edge;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Vertex<V>[] endVertices(Edge<E> e) {
        // search in edges list to find the endpoints of the input edge.
        for (Edge<E> edge : this.edges) {
            if (edge.equals(e) && (e instanceof InnerEdge<E> ie)) {
                return ie.getEndpoints();
            }
        }
        return null;
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
        // search in edges list to find the opposite vertex.
        for (Edge<E> edge : this.edges) {
            if (edge.equals(e) && (e instanceof InnerEdge<E> ie)) {
                if (ie.endpoints[0].equals(v)) {
                    return ie.endpoints[1];
                } else if (ie.endpoints[1].equals(v)) {
                    return ie.endpoints[0];
                }
            }
        }
        return null;
    }

    @Override
    public Vertex<V> insertVertex(V element) {
        InnerVertex<V> newInnerVertex = new InnerVertex<>(element, isDirected);
        newInnerVertex.setPos(vertices.addLast(newInnerVertex));
        return newInnerVertex;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
        InnerEdge<E> newInnerEdge = new InnerEdge<>(u, v, element);
        newInnerEdge.setPos(edges.addLast(newInnerEdge));
        return newInnerEdge;
    }

    @Override
    public void removeVertex(Vertex<V> v) {
        InnerVertex<V> innerVertex = validate(v);
        for (Edge<E> e : edges) {
            if ((e instanceof InnerEdge<E> edge) && ((edge.endpoints[0].equals(innerVertex)) || (edge.endpoints[1].equals(innerVertex)))) {
                removeEdge(e);
            }
        }
        vertices.remove(innerVertex.getPos());
    }

    @Override
    public void removeEdge(Edge<E> e) {
        InnerEdge<E> innerEdge=validate(e);
        edges.remove(innerEdge.getPos());
    }

    private InnerVertex<V> validate(Vertex<V> v) throws IllegalArgumentException {
        if (!(v instanceof InnerVertex<V> vert)) throw new IllegalArgumentException("invalid vertex");
        //if(!vertices.contains(vert)) throw new IllegalArgumentException("vertex is not in the list");
        return (InnerVertex<V>) v;
    }

    private InnerEdge<E> validate(Edge<E> e) throws IllegalArgumentException {
        if (!(e instanceof InnerEdge<E> edge)) throw new IllegalArgumentException("invalid edge");
        //if(!edges.contains(edge)) throw new IllegalArgumentException("edge is not in the list");
        return (InnerEdge<E>) e;
    }
}
