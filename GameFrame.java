import javax.swing.JFrame;

public class GameFrame extends JFrame{

  GameFrame(){
    /*
        GamePanel Panel = new GamePanel();
        this.addPanel;                     (23 September 2023, 2000 hrs right now hehe).

        This work above can be done by just one line,
        and that line is:
        this.add(new GamePanel());
    */

    this.add(new GamePanel());

    this.setTitle("Snake");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
