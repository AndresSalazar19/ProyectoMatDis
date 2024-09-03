/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modulo;

/**
 *
 * @author asala
 */

public class FloydWarshall {
    final static int INF = Integer.MAX_VALUE;
    
    public int[][] floydWarshall(int[][] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        int[][] next = new int[n][n];
        
        // Inicializar la matriz de distancias y la matriz de rutas
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
                if (graph[i][j] != INF && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }
        
        // Algoritmo de Floyd-Warshall
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
        
        return next;
    }

    
    public void printPath(int i, int j, int[][] next) {
        if (next[i][j] == -1) {
            System.out.println("No existe un camino desde " + (i + 1) + " a " + (j + 1));
            return;
        }
        
        System.out.print((i + 1));
        while (i != j) {
            i = next[i][j];
            System.out.print(" -> " + (i + 1));
        }
        System.out.println();
    }


    public void printSolution(int[][] dist) {
        int n = dist.length;
        System.out.println("Matriz de distancias más cortas entre cada par de nodos:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dist[i][j] == INF)
                    System.out.print("INF ");
                else
                    System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }
    }
}
