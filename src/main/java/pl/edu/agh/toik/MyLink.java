package pl.edu.agh.toik;

/**
 * Created by pkala on 5/26/15.
 */
class MyLink {
    double weight;

    public MyLink(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "w" + weight;
    }
}