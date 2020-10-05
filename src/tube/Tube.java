package tube;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tube implements Iterable<Ball> {

    public int leftUpX, leftUpY, rightDownX, rightDownY;
    List<Ball> balls;

    public Tube() {
        balls = new ArrayList<Ball>();
    }

    @Override
    public Iterator<Ball> iterator() {
        return balls.iterator();
    }

    public int size() {
        return balls.size();
    }

    public boolean add(Ball ball) {
        return balls.size() < 4 ? balls.add(ball) : false;
    }

    private Ball removeLast() {
        int lastIndex = balls.size() - 1;
        return balls.remove(lastIndex);
    }

    public Ball getFirst() {
        return balls.size() != 0 ? balls.get(balls.size() - 1) : null;
    }

    public boolean moveTo(Tube tube) {
        if (tube.size() == 4 || this.size() == 0)
            return false;

        if (tube.size() > 0 && (! this.getFirst().color.equals(tube.getFirst().color)))
            return false;

        Ball ball = this.removeLast();
        return tube.add(ball);
    }

    public boolean emptyOrOneColor() {
        if (balls.size() == 0)
            return true;
        if (balls.size() < 4 && balls.size() > 0)
            return false;

        for (int i=0; i<balls.size()-1; i++)
            if (! balls.get(i).color.equals(balls.get(i+1).color))
                return false;

        return true;
    }

}
