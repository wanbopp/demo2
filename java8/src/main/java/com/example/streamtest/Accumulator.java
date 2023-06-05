package com.example.streamtest;

public class Accumulator {

    public long total = 0;

    public long getTotal() {
        return total;
    }

    public void add(long n){
        total += n;
    }
}
