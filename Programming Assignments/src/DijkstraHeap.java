import java.util.Scanner;

public class DijkstraHeap {

  public static void main(String[] args) {

    int N, M, S;

    Scanner scan = new Scanner(System.in);
    N = scan.nextInt(); // # cities
    M = scan.nextInt(); // # train routes
    S = scan.nextInt(); // capital city
    // System.out.println(N + " " + M + " " + S);

    // NOW THE ARRAYS
    int[][] A = new int[50010][60]; // the neighbors of each city
    int[][] W = new int[50010][60];
    int[] deg = new int[50010]; // the degree of the city
    int[][] h = new int[N + 10][N + 10]; // the heap
    // The limits are 50,010 and 60 because the problem statement said that there are at most
    // 50,000 cities, and we just added 10 just to be sure. We have 60 because the maximum number of
    // train routes is 50, and we just added 10 to that.

    // with each incoming vertex/city, we will at first initialize the degree to be 0
    for (int i = 1; i <= N; ++i) {
      deg[i] = 0;  // initialize the degree of each vertex to 0
    }

    // this is for each of the train routes
    for (int i = 1; i <= M; ++i) {
      int u, v, w;
      u = scan.nextInt(); // origin
      v = scan.nextInt(); // destination
      w = scan.nextInt(); // # days
      // System.out.println(u + " " + v + " " + w);

      // WITH THE ARRAYS
      A[u][deg[u]] = v; // adding an edge (u,v) to the graph where u is origin and deg[u] is weight
      deg[u]++; // increase degree of vertex u by 1
    }

    //for(int i = 1; i <= N; ++i) {
    //  System.out.println("vertex:" + i + "'s neighbors");
    //  for(int j = 0; j < deg[i]; ++j) {
    //    System.out.println(A[i][j] + " " + W[i][j]);
    //  }
    //}



    // compute distance from U (origin) to S (capital city) by Dijkstra's algorithm
    // Dijkstra's algorithm: find the shortest path distance from each vertex to the capital
    for(int U = 1; U <= N; ++U) {


      // INITIALIZATION
      int[] visited = new int[N + 10]; // create an empty array w/ max # cities space for cities that are visited
      // loop that goes through the arrays and fills in values up to N number of cities
      for (int V = 1; V <= N; ++V) {
        h[U][V] = 100000000; // set the distance of the city to the capital to be the maximum possible number
        visited[V] = 0; // set the cities that are visited to be 0
      }

      // ACTUAL ALGORITHM


      h[U][N + 10] = 0;

      //find an unvisited vertex with minimum distance
      int min = 100000000;
      int minVertex = 1;
      h[U][minVertex] = min;
      for (int i = 1; i <= N; ++i) {
        // if the city has not been visited and the distance from it to the capital is less than the minimum
        if (visited[i] == 0 && h[U][i] < min) {
          min = h[U][i]; // set the new minimum to be this distance
          minVertex = i; // set the minimum vertex to be this number
          h[U][i] = min;
        }
      }

       visited[minVertex] = 1; // set this value to 1 to show that the city has been visited
       // relax the edges that are adjacent to minVertex to update the shortest path distance to
       // neighbors of minVertex
       for(int j = 0; j < deg[minVertex]; ++j) { // this is updating the minimum weight of the city
         // A[minVertex][j] is the j-th neighbor of minVertex
         // W[minVertex][j] is the weight of the corresponding edge
         int newDist = h[U][minVertex] + h[minVertex][j];
         if (newDist < h[U][A[minVertex][j]]) {
           h[U][A[minVertex][j]] = newDist;
         }
       }




      if(h[U][S] == 100000000) { // if the distance of this city is still the maximum, it does not have a connection
        System.out.print("-1 ");
      }
      else { // if it has a distance less than max, it means there is a minimum distance and we will print that
        System.out.print(h[U][S] + " ");
      }

    }
    System.out.println("");
  }
}