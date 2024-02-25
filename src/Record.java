import java.io.Serializable;

public class Record implements Serializable,Comparable<Record> {
    private String name;
    private int score;

    public Record(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + "\t" + score;
    }

    @Override
    public int compareTo(Record o) {
        return o.score - score;
    }
}
