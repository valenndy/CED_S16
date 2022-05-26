package main;

import java.util.Scanner;

import model.Grafo;

public class Main {

	private Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		Main obj = new Main();
		obj.init();

	}

	public void init() {

		System.out.println("Digite el numero de vertices");
		int vertice = sc.nextInt();

		System.out.println("ingrese el valor de los vertices segun la cantidad que elijio previamente: s");

		Grafo<Integer> graph = new Grafo<>(vertice);

		for (int i = 0; i < vertice; i++) {
			int vertex = sc.nextInt();
			graph.añadirVertice(vertex);
		}
		int option = 1;
		while (option == 1) {
			System.out.println("ingrese el vertice 1");
			int vertice1 = sc.nextInt();

			System.out.println("ingrese el vertice 1");
			int vertice2 = sc.nextInt();

			System.out.println("ingrese el peso");
			int weight = sc.nextInt();

			System.out.println("conecto " + vertice1 + " con " + vertice2 + " con peso de: " + weight + "\n");

			graph.addEdge(vertice1, vertice2, weight);

			System.out.println("\n do you want to enter a edge?");
			System.out.println("1. Sisas");
			System.out.println("2. Nonas");
			option = sc.nextInt();
		}
		graph.prim(graph.matrixC);
	}
}