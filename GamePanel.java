import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;


 GamePanel(){
 
        StartGame();
    }


    //-----------------------------------------------------------------------------------------
    public void StartGame(){
        newFrog();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }


    //-----------------------------------------------------------------------------------------
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

 GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT ));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();
    }

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEIGHT*SCREEN_WIDTH)/UNIT_SIZE;
    static final int DELAY = 70;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int frogsEaten = 0;
    int frogX;
    int frogY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    //-----------------------------------------------------------------------------------------
    public void draw(Graphics g){

        if(running){

        // The below loop helps to draw lines on the J Board.
        for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }

        g.setColor(Color.red);                           // Colour of frog
        g.fillOval(frogX, frogY, UNIT_SIZE, UNIT_SIZE);


        for(int i = 0; i<bodyParts; i++){
            if (i == 0){
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            else{
                g.setColor(new Color(45, 100, 10));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
      } // ended if(running) loop

        // Below lines of code are for displaying the score.
        g.setColor(Color.red);
		g.setFont( new Font("System",Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
        // The below line aligns the text in exactly the middle of the screen.
		g.drawString("Score " + frogsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score " + frogsEaten))/2, g.getFont().getSize());


      else {
        GameOver(g);
      }

    } // ended draw(Graphics g)


    //-----------------------------------------------------------------------------------------
    public void newFrog(){
        frogX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        frogY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }


    //-----------------------------------------------------------------------------------------
    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }


    //-----------------------------------------------------------------------------------------
    public void checkFrog(){
        if((x[0] == frogX) && (y[0] == frogY)) {
			bodyParts++;
			applesEaten++;
			newApple();
		}
    }


    //-----------------------------------------------------------------------------------------
    public void checkCollisions(){
        // The below loop checks if the head collides with the body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;                        // Game Over
			}
		}

        // To check if head touches left border
		if(x[0] < 0) {
			running = false;
		}

		// To check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}

		// To check if head touches top border
		if(y[0] < 0) {
			running = false;
		}

		// To check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}

        // If not running, then timer stops, game over.
		if(!running) {
			timer.stop();
		}
    }


    //-----------------------------------------------------------------------------------------
    public void GameOver(Graphics g){

        // Game Score text
        g.setColor(Color.red);
		g.setFont( new Font("System",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
        // The below line aligns the text in exactly the middle of the screen.
		g.drawString("Score " + frogsEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score " + frogsEaten))/2, g.getFont().getSize());

        // 'GAME OVER' text
        g.setColor(Color.red);
		g.setFont( new Font("System",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
        // The below line aligns the text in exactly the middle of the screen.
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }


    //-----------------------------------------------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkFrog();
            checkCollisions();
        }
        repaint();
    }


    //-----------------------------------------------------------------------------------------
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){

                // The player shouldn't be able to make 180 degree turns.
                // 24 September 2023, 1206 hrs.

                case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;


            }
        }
    }
}
