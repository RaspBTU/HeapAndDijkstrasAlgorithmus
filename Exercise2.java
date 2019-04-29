package exercise;

import util.ADGraph;

import java.util.*;

public class Exercise2 {
    public static class MinHeap extends PartialMinHeap {
        /*
         * Setzen Sie IS_IMPLEMENTED auf true, wenn ihr MinHeap bei der
         * Ausfuerhung von shortestPaths verwendet werden soll!
         */
        private static boolean IS_IMPLEMENTED = true;

        @Override
        /*
         * Implementieren Sie die heapifyUp Methode wie in der Vorlesung besprochen.
         * Der Integer i ist dabei die Position des Heap Elements. Mit keyAt(i) haben sie Zugriff
         * auf den Schluessel.
         */
        protected void heapifyUp(int i) {

            if(i > 0){
                int j = getParent(i);
                if(keyAt(i) < keyAt(j)){
                    swap(i, j);

                    heapifyUp(j);
                }
            }
        }

        @Override
        /*
         * Implementieren Sie die heapifyDown Methode wie in der Vorlesung besprochen.
         * Der Integer i ist dabei die Position des Heap Elements. Mit keyAt(i) haben sie Zugriff
         * auf den Schluessel.
         */
        protected void heapifyDown(int i) {

            int n = size()-1;
            int j;
            if(getLeft(i) > n) return;
            else if(getLeft(i) < n){
                int left = getLeft(i);
                int right = getRight(i);

                j = (keyAt(left) < keyAt(right)) ? left : right;
            }
            else j = n;

           if(keyAt(j) < keyAt(i)){
                swap(i, j);
                heapifyDown(j);
            }
        }

        @Override
        /*
         * Fuer ein Element an Position i gibt getParent(i) dessen Elternelement, wie
         * in der Vorlesung besprochen, zurueck. Beachten Sie, dass wir in dieser Implementierung
         * ab 0 zaehlen, nicht ab 1 wie in der Vorlesung!
         */
        protected int getParent(int i) {
            return (i-1)/2;
        }

        @Override
        /*
         * Fuer ein Element an Position i gibt getLeft(i) dessen linken Nachfolger, wie
         * in der Vorlesung besprochen, zurueck. Beachten Sie, dass wir in dieser Implementierung
         * ab 0 zaehlen, nicht ab 1 wie in der Vorlesung!
         */
        protected int getLeft(int i) {
            return (2*i)+1;
        }

        @Override
        /*
         * Fuer ein Element an Position i gibt getRight(i) dessen rechten Nachfolger, wie
         * in der Vorlesung besprochen, zurueck. Beachten Sie, dass wir in dieser Implementierung
         * ab 0 zaehlen, nicht ab 1 wie in der Vorlesung!
         */
        protected int getRight(int i) {
            return (2*i)+2;
        }

        //TO IGNORE
        @Override
        public boolean isImplemented() {
            return IS_IMPLEMENTED;
        }
    }

    /*
     * Implementieren Sie hier ihre Version von Dijkstras Algorithmus. G ist der Graph, s und t die IDs
     * der Knoten, zwischen denen Sie den kuerzesten s->t Pfad finden sollen. Q ist eine bereits korrekt
     * initialisierte, leere Priority Queue. Sie muessen NICHT den MinHeap von oben an diese Methode
     * weitergeben, dass erledigt das Framework fuer sie!
     *
     * Als Rueckgabe erwartet das Framework das Gewicht eines kuerzesten Pfades von s nach t. In path
     * speichern Sie bitte die IDs der besuchten Knoten von s nach t. Dass heisst am Ende sollte path
     * an Position Null s enthalten, gefolgt von den Knoten auf dem Weg von s nach t und auf dem letzten
     * Arrayplatz den Knoten t.
     */
    public static double shortestPaths(ADGraph G, Integer s, Integer t, ArrayList<Integer> path, MyPriorityQueue Q) {

        double[] dist = new double[G.numVertices()];
        int[] previous = new int[G.numVertices()];

        boolean[] disc = new boolean[G.numVertices()];

        for (int i = 0; i < dist.length; i++) {
            dist[i] = Double.MAX_VALUE;
            previous[i] = -1;
        }

        dist[s] = 0;

        for (int i = 0; i < dist.length; i++) Q.add(i, dist[i]);

        while (!Q.isEmpty()) {

            int u = Q.removeMin();

            for (int v : (ArrayList<Integer>) G.outNeighbors(u)) {

                if (!disc[v]) {

                    double distance = dist[u] + G.weight(u, v);

                    if (distance < dist[v]) {
                        dist[v] = distance;
                        previous[v] = u;

                        Q.decreaseKey(v, distance);
                    }
                }
            }

            disc[u] = true;
        }

        int u = t;
        path.add(0,t);
        while(u != s){
            u = previous[u];
            path.add(0, u);
        }

        // 5) Gib die Distanz zu 't' aus
        return dist[t];
    }
}
