package framework.test;

import framework.annotations.Autowired;
import framework.annotations.Bean;
import framework.annotations.Qualifier;

@Bean
public class Klasa {
    private int x;
    @Autowired(verbose = true)
    private Klasa2 klasa2;



    public Klasa() {
        this.x = 6;
    }

    public int getX() {
        return x;
    }

    public Klasa2 getKlasa2() {
        return klasa2;
    }


}
