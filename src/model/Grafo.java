package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Grafo<T> {

	public int[][] matrixF;
	public int[][] matrixC;
	public HashMap<T, ArrayList<T>> vertexAdj;
	public ArrayList<T> cost = new ArrayList<>();

	public Grafo(int nv) {
		this.matrixC = new int[nv][nv];
		matrixF = new int[nv][nv];
		matrixCweight();
		this.vertexAdj = new HashMap<>();
	}
	
	public int keyMin(int key[], Boolean mstSet[], int nV) {
		
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < nV; v++)
			if (mstSet[v] == false && key[v] < min) {
				min = key[v];
				min_index = v;
			}

		return min_index;
	}

	public void MST(int parent[], int matrixC[][]) {
		int nV = matrixC.length;
		System.out.println("Edge \tWeight");
		for (int i = 1; i < nV; i++)
			System.out.println(parent[i] + " - " + i + "\t" + matrixC[i][parent[i]]);
	}

	public void matrixCweight() {
		for (int i = 0; i < matrixC.length; i++) {
			for (int j = 0; j < matrixC[0].length; j++) {
				if (i == j) {
					matrixC[i][j] = 0;
				} else {
					matrixC[i][j] = Integer.MAX_VALUE;
				}
			}
		}
	}

	public void addEdge(T vertex1, T vertex2) {
		Vertice v1 = new Vertice(vertex1);
		Vertice v2 = new Vertice(vertex2);
		vertexAdj.get(vertex1).add(vertex2);
	}
	
	public void añadirVertice(T data) {
		vertexAdj.put(data, new ArrayList<>());
		cost.add(data);
	}

	public void removerVertice(T data) {
		Vertice v = new Vertice(data);
		vertexAdj.remove(new Vertice(data));
	}

	public void searchadj(T data) {
		System.out.println(vertexAdj.get(data));
	}

	public Set<T> BFS(Grafo graph, T root) {
		Set<T> visited = new LinkedHashSet<T>();
		Queue<T> queue = new LinkedList<T>();
		queue.add(root);
		visited.add(root);
		while (!queue.isEmpty()) {
			T vertex = queue.poll();
			for (T v : vertexAdj.get(vertex)) {
				if (!visited.contains(v)) {
					visited.add((T) v);
					queue.add((T) v);
				}
			}
		}
		return visited;
	}

	public boolean conexo(Grafo graph) {

		for (T v : vertexAdj.keySet()) {
			Set<T> size = new LinkedHashSet<T>();
			size = BFS(graph, v);

			if (size.size() != graph.vertexAdj.keySet().size()) {
				return false;
			}
		}
		return true;
	}

	public void addEdge(T v1, T v2, int cost) {
		int i = 0;
		for (T v : cost) {
			if (v.equals(v1)) {
				break;
			}
			i++;
		}
		int j = 0;
		for (T v : cost) {
			if (v.equals(v2)) {
				break;
			}
			j++;
		}
		matrixC[i][j] = cost;
		matrixC[j][i] = cost;
	}

	public void cost() {
		for (int x = 0; x < matrixC.length; x++) {
			System.out.print("______");
			for (int y = 0; y < matrixC[x].length; y++) {
				System.out.print(matrixC[x][y]);
				if (y != matrixC[x].length - 1)
					System.out.print("\t");
			}
			System.out.println("______");
		}
	}

	public void dijkstra(int graph[][], int src, int V) {
		int dist[] = new int[V];
		Boolean sptSet[] = new Boolean[V];

		for (int i = 0; i < V; i++) {
			dist[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}

		dist[src] = 0;

		for (int count = 0; count < V - 1; count++) {

			int u = distanciaMinima(dist, sptSet);

			sptSet[u] = true;

			for (int v = 0; v < V; v++)

				if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
					dist[v] = dist[u] + graph[u][v];
		}

		solucion(dist);
	}

	public int distanciaMinima(int dist[], Boolean sptSet[]) {

		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < dist.length; v++)
			if (sptSet[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}

		return min_index;
	}

	public void floydWarshall(int[][] graph) {
		int nV = graph.length;

		int i = 0;
		int j = 0;
		int k = 0;

		for (i = 0; i < matrixF.length; i++) {
			for (j = 0; j < matrixF[0].length; j++) {
				matrixF[i][j] = graph[i][j];

			}
		}

		for (k = 0; k < nV; k++) {
			for (i = 0; i < nV; i++) {
				for (j = 0; j < nV; j++) {

					if (matrixF[i][j] > (matrixF[i][k] + matrixF[k][j])
							&& (matrixF[k][j] != Integer.MAX_VALUE && matrixF[i][k] != Integer.MAX_VALUE))
						matrixF[i][j] = matrixF[i][k] + matrixF[k][j];
				}
			}
		}
	}

	public void shortroads() {
		for (int x = 0; x < matrixF.length; x++) {
			System.out.print("|");
			for (int y = 0; y < matrixF[x].length; y++) {
				System.out.print(matrixF[x][y]);
				if (y != matrixF[x].length - 1)
					System.out.print("\t");
			}
			System.out.println("|");
		}

	}

	public void prim(int matrixC[][]) {

		int nV = matrixC.length;
		int parent[] = new int[nV];
		int key[] = new int[nV];

		Boolean mstSet[] = new Boolean[nV];


		for (int i = 0; i < nV; i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}

		key[0] = 0;

		parent[0] = -1;

		for (int count = 0; count < nV - 1; count++) {

			int u = keyMin(key, mstSet, nV);

			mstSet[u] = true;

			for (int v = 0; v < nV; v++)

				if (matrixC[u][v] != 0 && mstSet[v] == false && matrixC[u][v] < key[v]) {
					parent[v] = u;
					key[v] = matrixC[u][v];
				}
		}


		MST(parent, matrixC);
	}
	
	public Set<T> dfs(Grafo graph, T root) {
		Set<T> visited = new LinkedHashSet<T>();
		Stack<T> stack = new Stack<T>();
		stack.push(root);
		while (!stack.isEmpty()) {
			T vertex = stack.pop();
			if (!visited.contains(vertex)) {
				visited.add(vertex);
				for (T v : vertexAdj.get(vertex)) {
					stack.push(v);
				}
			}
		}
		return visited;
	}
	
	public void solucion(int dist[]) {
		System.out.println("Vertice \t\t distancia desde la fuente");
		for (int i = 0; i < dist.length; i++)
			System.out.println(cost.get(i) + " \t\t " + dist[i]);
	}

	
}