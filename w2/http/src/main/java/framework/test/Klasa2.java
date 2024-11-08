package framework.test;

import framework.annotations.Autowired;
import framework.annotations.Component;

@Component
public class Klasa2 {
    private int y;

    public Klasa2() {
        this.y = 7;
    }

    public int getY() {
        return y;
    }
}
