package com.lanaj.clustering;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@NoArgsConstructor
public class KMeans {

    private int numOfClusters;
    private int[] elements;
    private final int numOfElements = 100;
    private List<List<Integer>> clusters;
    private List<Double> centroids;

    /** Constructor - initialize class with necessary parameter {@link #numOfClusters} */
    public KMeans(int clusterCount) {
        this.elements = new int[this.numOfElements];
        for(int i = 0; i < this.numOfElements; i++)
            this.elements[i] = new Random().nextInt(1000);  // init random elements

        this.numOfClusters = clusterCount;
        this.clusters = new ArrayList<>();
        this.centroids = new ArrayList<>(this.numOfClusters);
    }

    public void start() {
        List<Double> oldCentroids = new ArrayList<>();
        while(!oldCentroids.equals(this.centroids)) {   // until centroids stop changing

            // init empty clusters on every step
            this.clusters.clear();
            for (int i = 0; i < this.numOfClusters; i++) {
                this.clusters.add(new ArrayList<>());
            }

            for (int el : elements) {
                int closestClusterIdx = getClosestCluster(el);
                clusters.get(closestClusterIdx).add(el);
            }
            oldCentroids = this.centroids;
            updateCentroids();
        }
    }

    /** Randomly choose centroids from {@link #elements}*/
    public void findCentroids() {
        for (int i = 0; i < this.numOfClusters; i++) {
            int temp = this.elements[new Random().nextInt(this.elements.length-1)];
            while (!centroids.contains((double) temp))
                centroids.add((double) temp);
        }
    }

    /** Calculate Manathan distance */
    public int getClosestCluster(int element) {

        double closestClusterVal = -1;
        double distance = Integer.MAX_VALUE;

        //find smallest difference between values: current element and centroid
        for(double center : centroids) {
            double temp = Math.abs(center - element);
            if(temp < distance) {
                distance = temp;
                closestClusterVal = center;
            }
        }
        return centroids.indexOf(closestClusterVal);    // get suitable cluster by centroid value
    }

    /** Update centroids with average value in corresponding clusters */
    private void updateCentroids() {
        this.centroids.clear();

        for(List<Integer> cluster : clusters) {
            double newCentroid = cluster.stream().mapToDouble(a->a).average().getAsDouble();
            this.centroids.add(newCentroid);
        }
    }

    public String getResult() {
        return clusters.toString();
    }
}
