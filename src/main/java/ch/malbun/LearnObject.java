package ch.malbun;

import java.io.Serial;
import java.io.Serializable;

public class LearnObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String a;
    private String b;

    public LearnObject(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public boolean compare(String a, String b) {
        return (this.a.equals(a) && this.b.equals(b)) || (this.a.equals(b) && this.b.equals(a));
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
