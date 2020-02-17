import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
class MainPane extends JPanel implements KeyListener {
    Board board;
    JLabel gameOverLabel;
    MainPane() {
        super();
        board = new Board(MainFrame.frameDimensions);
        gameOverLabel = new JLabel("Game Over");
        setFocusable(true);
        addKeyListener(this);
    }
    void update() {
        board.update();
        removeAll();
        if (!board.isAlive()) {
            gameOverLabel.setBounds(
                125,
                125,
                gameOverLabel.preferredSize().width,
                gameOverLabel.preferredSize().height
            );
            add(gameOverLabel);
        }
        revalidate();
        repaint();
    }
    boolean isAlive() {
        return board.isAlive();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        int correctedX = 0;
        int correctedY = 0;
        
        Block[][] gameboard = board.getBoardArray();
        Block block = null;
        for (int i = 0; i < gameboard.length; i++) {
            for (int j = 0; j < gameboard[i].length; j++) {
                block = gameboard[i][j];
                Shape blockShape = block.getShape(correctedX,correctedY);
                g2d.setColor(block.getColor());
                g2d.draw(blockShape);
                g2d.fill(blockShape);
                correctedX += Block.SIZE;
            }
            correctedY += Block.SIZE;
            correctedX = 0;
        }
        
        g2d.setColor(java.awt.Color.BLACK);
        g2d.drawString(board.getScoreString(),225,10);
        
    }
    @Override
    public void keyPressed(KeyEvent ke) {
        //board.snake.get(0).pv = board.snake.get(0).v;
        Block head = board.snake.get(0);
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if(head.v != Velocity.DOWN) {
                    board.changeHeadVelocity(Velocity.UP);
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (head.v != Velocity.UP) {
                    board.changeHeadVelocity(Velocity.DOWN);
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (head.v != Velocity.LEFT) {
                    board.changeHeadVelocity(Velocity.RIGHT);
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (head.v != Velocity.RIGHT) {
                    board.changeHeadVelocity(Velocity.LEFT);
                }
                break;
        }
        board.snake.get(0).pv = board.snake.get(0).v;
    }
    /* DEPRECATED METHODS -- DO NOT CALL */
    @Deprecated
    @Override
    public void keyReleased(KeyEvent ke) {}
    @Deprecated
    @Override
    public void keyTyped(KeyEvent ke) {}
}