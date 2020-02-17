import java.lang.Thread;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.Color;
class Maxwell {
    private static int x = 1138;
    private static int y = 581;
    private static int R = 130;
    private static int P = 487;
    public static void main(String[] args) {
        Robot rob = null;
        try {
            rob = new Robot();
        } catch (java.awt.AWTException awe) {
            awe.printStackTrace();
        }
        rob.setAutoDelay(25);
        releaseAtFourtyFive(rob);
    }
    private static void releaseAtFourtyFive(Robot rob) {
        //Drag pendulum
        rob.mouseMove(x,y);
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
        rob.mousePress(InputEvent.BUTTON1_MASK);
        Thread threader = new Thread() {
            public void run() {
                for (int i = 0; i < R; i++) {
                    x--;
                    y -= i%2;
                    rob.mouseMove(x,y);
                }
            }
        };
        threader.start();
        try {
            while(threader.isAlive()) { Thread.sleep(1000); }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
        System.out.println("Button released");
        
        //Start timer
        rob.mouseMove(924,563);
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
        
        //Watch for period trail
        Color color = new Color(255,255,255);
        do {
            color = rob.getPixelColor(1130,P);
        } while (color.getRed() > 198 && color.getGreen() > 198);
        
        //Stop timer
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
        
        rob.mouseMove(1133,633);
        rob.mousePress(InputEvent.BUTTON1_MASK);
        rob.mouseRelease(InputEvent.BUTTON1_MASK);
    }
}