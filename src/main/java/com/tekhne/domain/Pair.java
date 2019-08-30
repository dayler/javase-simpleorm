package com.tekhne.domain;

public class Pair<F, S> {
    
    private F first;
    private S second;
    
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
    
    public F first() {
        return first;
    }
    
    public S second() {
        return second;
    }
    
    public static <F, S>Pair<F, S>of(F first, S second) {
        return new Pair<>(first, second);
    }
}
