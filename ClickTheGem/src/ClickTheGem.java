import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class ClickTheGem {
    int frameHeight = 770;
    int frameWidth = 720;

    //creating the window
    JFrame frame = new JFrame("Game: Find the Hidden Gem!");
    JLabel textLabel = new JLabel();
    JLabel textLabel1 = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton [] board = new JButton[9];
    Random random = new Random();
    int score = 0;
    int hScore = 0;

    ImageIcon gemIcon;
    ImageIcon deathIcon;

    Timer setGemTimer;
    Timer setDeathTimer;
    Timer setDeathTimer2;

    JButton currGemTile;
    JButton currDeathTile;
    JButton currDeathTile1;
    
    ClickTheGem(){
        //Frame
        // frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        //setting the layout of the frame to use Borderlayout
        frame.setLayout(new BorderLayout());

        //text1
        textLabel1.setFont(new Font("Gelline", Font.BOLD, 25));
        textLabel1.setOpaque(true);
        textLabel1.setHorizontalAlignment(JLabel.CENTER);
        textLabel1.setBackground(Color.decode("#243e36"));
        textLabel1.setForeground(Color.decode("#f1f7ed"));
        //Text
        textLabel.setFont(new Font("Gelline", Font.BOLD, 25));
        textLabel.setHorizontalAlignment(JLabel.LEFT);
        textLabel.setText("Score = 0");
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.decode("#243e36"));
        textLabel.setForeground(Color.decode("#f1f7ed"));

        
        //setting the textpanel layout format to borderLayout instead of flowLayout
        textPanel.setLayout(new BorderLayout());
        textPanel.setFocusable(false);
        textPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#7ca982"), 1));
        textPanel.setBackground(Color.decode("#243e36"));

        //adding textlabel to textpanel
        textPanel.add(textLabel, BorderLayout.WEST);
        //adding textpanel to the frame
        frame.add(textPanel, BorderLayout.NORTH);
        //setting boardpanel in gridlayout
        boardPanel.setLayout(new GridLayout(3,3));
        //adding the newly created boardPanel to the frame
        frame.add(boardPanel);
        boardPanel.setBackground(Color.black);
        /*We use Image because ImageIcon cannot be modified
         * so we use Image as it is rescalable using getScaledInstance()
         */
        textPanel.add(textLabel1, BorderLayout.EAST);
        Image gemImg = new ImageIcon(getClass().getResource("./gem.png")).getImage();
        gemIcon = new ImageIcon(gemImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image deathImg = new ImageIcon(getClass().getResource("./death.png")).getImage();
        deathIcon = new ImageIcon(deathImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        //logic for storing the hightest score 
        
        //creating buttons to add into the gridLayout of boardPanel
        /*Ek button create hua jako button[i] array me jako betga vaisach 9 bi hota so */
        for(int i = 0; i<9; i++){
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setBorder(BorderFactory.createLineBorder(Color.decode("#7ca982"), 1));
            // tile.setIcon(gem);
            // tile.setIcon(death);
            tile.setFocusable(false);
            tile.setBackground(Color.decode("#243e36"));
            tile.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e){
                    JButton tile = (JButton) e.getSource();//retrieves the object of the clicked button
                    if(tile == currGemTile){
                        score+=10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if (tile == currDeathTile || tile == currDeathTile1){
                        textLabel.setForeground(Color.decode("#7ca982"));
                        textLabel.setText("Score: " + Integer.toString(score) + "(Game Over)");
                        setGemTimer.stop();
                        setDeathTimer.stop();
                        setDeathTimer2.stop();
                        for(int i = 0; i<9; i++){
                            board[i].setEnabled(false);
                        }
                    }
               } 
            });
        }
        setGemTimer = new Timer(750, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //removing the gem tile
                if(currGemTile!=null){
                    currGemTile.setIcon(null);
                    currGemTile = null;
                }

                //set tile to gem
                int num = random.nextInt(9);//randomly selects a number bw 0-8
                JButton tile = board[num];//stores the indexed button in a JButton called tile
                //return if a tile is already occupied
                if(currDeathTile == tile){
                    return;
                }

                currGemTile = tile;//then the Index of JButton is stored in currGemTile
                currGemTile.setIcon(gemIcon);//then set the indexed JButton to gem
            }
        });
        setDeathTimer = new Timer(850, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //deleting the existing tile in the boardpanel
                if(currDeathTile != null){
                    currDeathTile.setIcon(null);
                    currDeathTile = null;
                }

                //set the death tile
                int num = random.nextInt(9);
                JButton tile = board[num];
                if(currGemTile == tile){
                    return;
                }
                currDeathTile = tile;
                currDeathTile.setIcon(deathIcon);
            }
        });
        setDeathTimer2 = new Timer(750, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                //deleting the existing tile in the boardpanel
                if(currDeathTile1 != null){
                    currDeathTile1.setIcon(null);
                    currDeathTile1 = null;
                }

                //set the death tile
                int num1 = random.nextInt(9);
                JButton tile = board[num1];
                if(currGemTile == tile){
                    return;
                }
                currDeathTile1 = tile;
                currDeathTile1.setIcon(deathIcon);
            }
        });
        setDeathTimer2.start();
        setDeathTimer.start();
        setGemTimer.start();
        frame.setVisible(true);
    }
}
