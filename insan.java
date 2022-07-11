public class insan implements Cloneable {
    private String name;
    private double borc;
    public insan(String name, double borc) {
        this.borc = borc;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public double getBorc() {
        return borc;
    }
    public void borcEkle(double miktar) {
        borc = borc - miktar;
    }
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
