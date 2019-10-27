package com.training.springtips.config;


import com.training.springtips.annotations.DeprecatedClass;
import com.training.springtips.annotations.PostProxy;
import com.training.springtips.annotations.Profile;
import com.training.springtips.annotations.RandomInt;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Profile
//@DeprecatedClass(newImpl = Blabler2.class)
public class Blabler implements TalkActive {

    @RandomInt(min = 2, max = 3)
    private int repeat;

    public Blabler() {
        System.out.println("Phase1: Constructor");
    }

    @PostConstruct
    public void init(){
        System.out.println("Phase2: PostConstruct");
    }

    @PostProxy
    public void postProxy(){
        System.out.println("Phase3: PostProxy. It should be on proxy with @Profile");
    }

    public void say(){
        IntStream.rangeClosed(1, repeat)
                .forEach(i ->System.out.println("I say bla-bla-bla"));
    }
}
