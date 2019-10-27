package com.training.springtips.config;

public class Blabler2 implements TalkActive {
    @Override
    public void say() {
        System.out.println("I say just bla");
    }

    @Override
    public void postProxy() {
        System.out.println("Blabler2 postProxy");
    }
}
