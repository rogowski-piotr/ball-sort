import tube.Ball;
import tube.Tube;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JPanel implements ActionListener, MouseListener {

    final static Color BACKGROUND_COLOR = Color.black;
    final static Color TUBE_COLOR = Color.white;
    final static Color [] BALL_COLORS = {Color.green, Color.yellow, Color.blue, Color.gray, Color.cyan, Color.magenta, Color.orange, Color.pink, Color.red};
    final static int TUBE_AMOUNT = 10;
    final static int EMPTY_TUBE_AMOUNT = 2;

    static List<Tube> tubes;
    static int choosenTube = -1;

    Board() {
        super();
        addMouseListener(this);
        initializeGame();
    }
    
    private void initializeGame() {
        tubes = new ArrayList<>();
        int [] ballsInTubes = new int [TUBE_AMOUNT - EMPTY_TUBE_AMOUNT];
        Random rand = new Random();

        for (int t = 0; t < TUBE_AMOUNT - EMPTY_TUBE_AMOUNT; t++) {
            Tube tube = new Tube();

            while (tube.size() != 4) {
                int x = rand.nextInt(TUBE_AMOUNT - EMPTY_TUBE_AMOUNT);
                Color c = BALL_COLORS[x];
                if (ballsInTubes[x] < 4) {
                    ballsInTubes[x]++;
                    tube.add(new Ball(c));
                }
            }
            tubes.add(tube);
        }

        for (int t=0; t<EMPTY_TUBE_AMOUNT; t++) {
            tubes.add(new Tube());
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, d.width, d.height);

        paintTube(g, d);

        paintBalls(g, d);
    }

    private void paintTube(Graphics g, Dimension d) {
        int tubeWidth = -1;
        int tubeHeight = -1;
        g.setColor(TUBE_COLOR);

        for (int level=0; level<2; level++) {
            int tubeInLvl = TUBE_AMOUNT % 2 == 1 && level == 0 ? TUBE_AMOUNT / 2 + 1 : TUBE_AMOUNT / 2;

            if (tubeWidth == -1) {
                tubeWidth = d.width / (tubeInLvl * 2 + 1);
                tubeHeight = tubeWidth * 4;
            }

            for (int i = 0; i < tubeInLvl * 2 + 1; i++) {
                if (level == 0) {
                    if (i % 2 == 1) {
                        g.drawLine(i * tubeWidth, (5 * d.height) / 12, (i + 1) * tubeWidth, (5 * d.height) / 12);
                        g.drawLine(i * tubeWidth, (5 * d.height) / 12, i * tubeWidth, (5 * d.height) / 12 - tubeHeight);
                        g.drawLine((i + 1) * tubeWidth, (5 * d.height) / 12, (i + 1) * tubeWidth, (5 * d.height) / 12 - tubeHeight);
                    }
                } else {
                    if (i % 2 == 1) {
                        g.drawLine(i * tubeWidth, (11 * d.height) / 12, (i + 1) * tubeWidth, (11 * d.height) / 12);
                        g.drawLine(i * tubeWidth, (11 * d.height) / 12, i * tubeWidth, (11 * d.height) / 12 - tubeHeight);
                        g.drawLine((i + 1) * tubeWidth, (11 * d.height) / 12, (i + 1) * tubeWidth, (11 * d.height) / 12 - tubeHeight);
                    }
                }
            }
        }
    }

    private void paintBalls(Graphics g, Dimension d) {
        int tubeWidth = -1, tubeHeight = -1;
        int actualTube = 0;

        for (int level=0; level<2; level++) {
            int tubeInLvl = TUBE_AMOUNT % 2 == 1 && level == 0 ? TUBE_AMOUNT / 2 + 1 : TUBE_AMOUNT / 2;

            if (tubeWidth == -1) {
                tubeWidth = d.width / (tubeInLvl * 2 + 1);
                tubeHeight = tubeWidth * 4;
            }

            for (int i = 0; i < tubeInLvl * 2 + 1; i++) {
                if (level == 0) {
                    if (i % 2 == 1) {
                        Tube tmp = tubes.get(actualTube);
                        int actualBall = 0;

                        tmp.leftUpX = i * tubeWidth;
                        tmp.leftUpY = (5 * d.height) / 12 - tubeHeight;
                        tmp.rightDownX = (i + 1) * tubeWidth;
                        tmp.rightDownY = (5 * d.height) / 12;

                        for (Ball ball : tmp) {
                            g.setColor(ball.color);
                            if (choosenTube == actualTube && tmp.getFirst() == ball)
                                g.fillOval(i * tubeWidth, (5 * d.height) / 12 - 5 * tubeWidth, tubeWidth, tubeWidth);
                            else g.fillOval(i * tubeWidth, (5 * d.height) / 12 - (1 + actualBall) * tubeWidth, tubeWidth, tubeWidth);
                            actualBall++;
                        }

                        if (actualTube+1 < tubes.size())
                            actualTube++;
                    }
                } else {
                    if (i % 2 == 1) {
                        Tube tmp = tubes.get(actualTube);
                        int actualBall = 0;

                        tmp.leftUpX = i * tubeWidth;
                        tmp.leftUpY = (11 * d.height) / 12 - tubeHeight;
                        tmp.rightDownX = (i + 1) * tubeWidth;
                        tmp.rightDownY = (11 * d.height) / 12;

                        for (Ball ball : tmp) {
                            g.setColor(ball.color);
                            if (choosenTube == actualTube && tmp.getFirst() == ball)
                                g.fillOval(i * tubeWidth, (11 * d.height) / 12 - (5) * tubeWidth, tubeWidth, tubeWidth);
                            else g.fillOval(i * tubeWidth, (11 * d.height) / 12 - (1 + actualBall) * tubeWidth, tubeWidth, tubeWidth);
                            actualBall++;
                        }

                        if (actualTube+1 < tubes.size())
                            actualTube++;
                    }
                }
            }
        }
    }

    public boolean checkVictory() {
        for (Tube tube : tubes)
            if (! tube.emptyOrOneColor())
                return false;
        return true;
    }

    public void mousePressed(MouseEvent mouseEvent) {
        int tmpX = mouseEvent.getX();
        int tmpY = mouseEvent.getY();
        int counter = 0, selectedTube = -1;

        for (Tube t : tubes) {
            if (tmpX >= t.leftUpX && tmpX <= t.rightDownX && tmpY >= t.leftUpY && tmpY <= t.rightDownY)
                selectedTube = counter;
            counter++;
        }

        if (selectedTube == -1) {
            choosenTube = -1;
        } else if (selectedTube >= 0 && choosenTube == -1) {
            choosenTube = selectedTube;
        } else if (selectedTube >= 0 && choosenTube >= 0) {
            if (tubes.get(choosenTube).moveTo(tubes.get(selectedTube)) && checkVictory())
                initializeGame();

            choosenTube = -1;
        }

        repaint();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }

}
