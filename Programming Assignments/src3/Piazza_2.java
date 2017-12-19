import java.util.*;

/** class Kosaraju **/
public class Piazza_2 {

  /**
   * DFS function
   **/
  public void DFS(List<Integer>[] graph, int v, boolean[] visited, List<Integer> comp) {
    visited[v] = true;
    for (int i = 0; i < graph[v].size(); i++)
      if (!visited[graph[v].get(i)])
        DFS(graph, graph[v].get(i), visited, comp);
    comp.add(v);
  }

  /**
   * function fill order
   **/
  public List<Integer> fillOrder(List<Integer>[] graph, boolean[] visited) {
    int V = graph.length;
    List<Integer> order = new ArrayList<Integer>();

    for (int i = 0; i < V; i++)
      if (!visited[i])
        DFS(graph, i, visited, order);
    return order;
  }

  /**
   * function to get transpose of graph
   **/
  public List<Integer>[] getTranspose(List<Integer>[] graph) {
    int V = graph.length;
    List<Integer>[] g = new List[V];
    for (int i = 0; i < V; i++)
      g[i] = new ArrayList<Integer>();
    for (int v = 0; v < V; v++)
      for (int i = 0; i < graph[v].size(); i++)
        g[graph[v].get(i)].add(v);
    return g;
  }

  /**
   * function to get all SCC
   **/
  public List<List<Integer>> getSCComponents(List<Integer>[] graph) {
    int V = graph.length;
    boolean[] visited = new boolean[V];
    List<Integer> order = fillOrder(graph, visited);
    /** get transpose of the graph **/
    List<Integer>[] reverseGraph = getTranspose(graph);
    /** clear visited array **/
    visited = new boolean[V];
    /** reverse order or alternatively use a stack for order **/
    Collections.reverse(order);

    /** get all scc **/
    List<List<Integer>> SCComp = new ArrayList<>();
    for (int i = 0; i < order.size(); i++) {
      /** if stack is used for order pop out elements **/
      int v = order.get(i);
      if (!visited[v]) {
        List<Integer> comp = new ArrayList<>();
        DFS(reverseGraph, v, visited, comp);
        SCComp.add(comp);
      }
    }
    return SCComp;
  }

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    Piazza_2 k = new Piazza_2();

    int V = scan.nextInt();
    List<Integer>[] g = new List[V];
    for (int i = 0; i < V; i++) {
      g[i] = new ArrayList<Integer>();
    }

    int E = scan.nextInt();
    for(int i = 0; i < E; i++) {
      int x = scan.nextInt();
      int y = scan.nextInt();
      g[x].add(y);
    }

    List<List<Integer>> scComponents = k.getSCComponents(g);
    System.out.println(scComponents.size());

  }
}