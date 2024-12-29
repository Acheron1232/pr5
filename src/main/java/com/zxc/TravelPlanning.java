package com.zxc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TravelPlanning {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Route> train = CompletableFuture.supplyAsync(() -> fetchRoute("Train", 120, 5));
        CompletableFuture<Route> bus = CompletableFuture.supplyAsync(() -> fetchRoute("Bus", 80, 8));
        CompletableFuture<Route> flight = CompletableFuture.supplyAsync(() -> fetchRoute("Flight", 200, 2));

        CompletableFuture<Route> bestRoute = CompletableFuture.allOf(train, bus, flight).thenApply(v -> {
            Route trainRoute = train.join();
            Route busRoute = bus.join();
            Route flightRoute = flight.join();
            return findBestRoute(trainRoute, busRoute, flightRoute);
        });

        System.out.println("Best route: " + bestRoute.get());
    }

    private static Route fetchRoute(String mode, int cost, int duration) {
        try {
            Thread.sleep((long) (Math.random() * 2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new Route(mode, cost, duration);
    }

    private static Route findBestRoute(Route... routes) {
        Route best = routes[0];
        for (Route route : routes) {
            if (route.getScore() > best.getScore()) {
                best = route;
            }
        }
        return best;
    }

    static class Route {
        private final String mode;
        private final int cost;
        private final int duration;

        public Route(String mode, int cost, int duration) {
            this.mode = mode;
            this.cost = cost;
            this.duration = duration;
        }

        public int getScore() {
            return 1000 / duration - cost;
        }

        @Override
        public String toString() {
            return "Mode: " + mode + ", Cost: " + cost + ", Duration: " + duration + " hours";
        }
    }
}
