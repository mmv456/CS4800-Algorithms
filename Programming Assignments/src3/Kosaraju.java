import java.util.Queue;

public class KosarajuSharirSCC {
  private boolean[] marked;     // marked[v] = has vertex v been visited?
  private int[] id;             // id[v] = id of strong component containing v
  private int count;            // number of strongly-connected components

  /**
   * Computes the strong components of the digraph <tt>G</tt>.
   * @param G the digraph
   */
  public KosarajuSharirSCC(Digraph G) {

    // compute reverse postorder of reverse graph
    DepthFirstOrder dfs = new DepthFirstOrder(G.reverse());

    // run DFS on G, using reverse postorder to guide calculation
    marked = new boolean[G.V()];
    id = new int[G.V()];
    for (int v : dfs.reversePost()) {
      if (!marked[v]) {
        dfs(G, v);
        count++;
      }
    }

    // check that id[] gives strong components
    assert check(G);
  }

  // DFS on graph G
  private void dfs(Digraph G, int v) {
    marked[v] = true;
    id[v] = count;
    for (int w : G.adj(v)) {
      if (!marked[w]) dfs(G, w);
    }
  }

  /**
   * Returns the number of strong components.
   * @return the number of strong components
   */
  public int count() {
    return count;
  }

  /**
   * Are vertices <tt>v</tt> and <tt>w</tt> in the same strong component?
   * @param v one vertex
   * @param w the other vertex
   * @return <tt>true</tt> if vertices <tt>v</tt> and <tt>w</tt> are in the same
   *     strong component, and <tt>false</tt> otherwise
   */
  public boolean stronglyConnected(int v, int w) {
    return id[v] == id[w];
  }

  /**
   * Returns the component id of the strong component containing vertex <tt>v</tt>.
   * @param v the vertex
   * @return the component id of the strong component containing vertex <tt>v</tt>
   */
  public int id(int v) {
    return id[v];
  }

  // does the id[] array contain the strongly connected components?
  private boolean check(Digraph G) {
    TransitiveClosure tc = new TransitiveClosure(G);
    for (int v = 0; v < G.V(); v++) {
      for (int w = 0; w < G.V(); w++) {
        if (stronglyConnected(v, w) != (tc.reachable(v, w) && tc.reachable(w, v)))
          return false;
      }
    }
    return true;
  }

  /**
   * Unit tests the <tt>KosarajuSharirSCC</tt> data type.
   */
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    KosarajuSharirSCC scc = new KosarajuSharirSCC(G);

    // number of connected components
    int M = scc.count();
    StdOut.println(M + " components");

    // compute list of vertices in each strong component
    Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
    for (int i = 0; i < M; i++) {
      components[i] = new Queue<Integer>();
    }
    for (int v = 0; v < G.V(); v++) {
      components[scc.id(v)].enqueue(v);
    }

    // print results
    for (int i = 0; i < M; i++) {
      for (int v : components[i]) {
        StdOut.print(v + " ");
      }
      StdOut.println();
    }

  }

}
