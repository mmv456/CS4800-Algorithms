import java.util.List;
import java.util.Scanner;

public class Piazza_Main {
  static int maxNoOfNodes = 50010;
  static int maxDegree = 60;
  static int N, M;
  static int[][] A; // array of connected components
  static int[]   deg;
  static boolean[] visited;
  static int[] count;
  static int counter;

  static List<Integer> comp;

  static void dfs(int u) {
    visited[u] = true;
    for (int i = 0; i < deg[u]; i++) {
      if (!visited[A[u][i]]) {
        dfs(A[u][i]);
      }
    }
    comp.add(u);
  }

  public static void main(String[] args) {
    A = new int[maxNoOfNodes][maxDegree];
    deg = new int[maxNoOfNodes];
    visited = new boolean[maxNoOfNodes];
    count = new int[maxNoOfNodes];

    Scanner scan = new Scanner(System.in);
    N = scan.nextInt();
    M = scan.nextInt();

    for(int j = 1; j <= M; ++j) {
      int u, v;
      u = scan.nextInt();
      v = scan.nextInt();
      A[u][deg[u]] = v;
      deg[u] += 1;
    }


    for(int h = 1; h <= N; ++h) {
      count[h] = 0;
    }


    for(int k = 1; k <= N; ++k) {
      for (int i = 1; i <= N; ++i) {
        visited[i] = false;
      }

      dfs(k);

      for (int V = 1; V <= N; ++V) {
        if (visited[V] == true) {
          count[V] += 1;
        }
      }
    }


    for(int m = 1; m <= N; m++) {
      if(count[m] == N) {
        counter += 1;
      }
      else {
      }
    }
    System.out.println(counter);
  }
}
