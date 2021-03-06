/*
 * Tetris part B 
 * TCSS 305: Programming Practicum, Winter 2016
 */
package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.Border;

import model.Board;

/**
 * This class create the view of a Tetris game.
 * @author Trinh Pham
 * @version 122216
 */
public class TetrisGUI implements Observer, ActionListener, PropertyChangeListener {
    
    /**
     * Minimum size for the frame.
     */
    private static final Dimension MIN_SIZE = new Dimension(600, 400);
    
    /**
     * Thousand milliseconds per minute.
     */
    private static final int MY_DELAY = 1000;
    
    /**
     * Font size for the labels.
     */
    private static final int FONT_SIZE = 18;
    
    /**
     * String of "rotate".
     */
    private static final String R = "rotate";
    
    /**
     * String of "left".
     */
    private static final String L = "left";
    
    /**
     * String of "right".
     */
    private static final String RGT = "right"; 
    
    /**
     * String of "down".
     */
    private static final String DN = "down";
    
    /**
     * String of "drop".
     */
    private static final String DR = "drop";
    
    /**
     * String of "down".
     */
    private static final String P = "pause";
    
    /**
     * The main container for the game.
     */
    private JFrame myFrame;
    
    /**
     * The panel displays the game.
     */
    private TetrisPanel myGamePanel;
    
    /**
     * The panel displays the next piece.
     */
    private NextPiecePanel myNextPiecePanel;
    
    /**
     * The panel displays the score.
     */
    private ScorePanel myScorePanel;
    
    /**
     * The text of Score panel.
     */
    private JLabel myScoreLabel;
    
    /**
     * The text of Next piece panel.
     */
    private JLabel myNextLabel;
    
    /**
     * The JMenuBar of the GUI.
     */
    private TetrisMenu myMenu;
    
    /**
     * The delay of my timer.
     */
    private int myDelay;

    /**
     * The borders style of my GUI. This will shared between all the components.
     */
    private Border myBorder;

    /**
     * The boolean which value is true if users pause the game.
     */
    private Boolean myPauseGame;
    
    /**
     * The boolean value tells whether users want to draw grid lines.
     */
    private Boolean myDrawGrid;
    /**
     * The board that contains the data and control the game.
     */
    private Board myBoard;
    
    
    /**
     * The timer of the game.
     */
    private Timer myTimer;
    
    /**
     * The boolean value tells running state of the game.
     */
    private boolean myGameRunning;
    
    /**
     * The list to hold all the movement actions for key events (except Pause).
     */
    private ArrayList<MyKeyAction> myActionList;
    
    /**
     * Initial board width.
     */
    private int myBoardWidth = 10;
    
    /**
     * Initial board height.
     */
    private int myBoardHeight = 20;
    


    /**
     * Constructor to create a game display.
     */
    public TetrisGUI() {

        myBoard = new Board(myBoardWidth, myBoardHeight);
        myPauseGame = false;
        myDrawGrid = false;

        
        myTimer = new Timer(MY_DELAY, this);
        
        myBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, java.awt.Color.black);

        setupPanel();
        myGameRunning = false;
        myPauseGame = false;
        
        setupGUI();

        start();

    }
    
    /**
     * Helper method to create all the needed panels.
     */
    private void setupPanel() {
        myFrame = new JFrame("TCSS 305 Tetris");

        myFrame.setMinimumSize(MIN_SIZE);

        myMenu = new TetrisMenu(myBoard, this);

        enableActionList(true);

        myFrame.setJMenuBar(myMenu);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myGamePanel = new TetrisPanel(myBoard, this);
        myNextPiecePanel = new NextPiecePanel(myBoard, this);
        myScorePanel = new ScorePanel(myBoard, this);
        myScoreLabel = new JLabel("Score");
        myNextLabel = new JLabel("Next Piece");
        myNextLabel.setFont(new Font(null, Font.BOLD, FONT_SIZE));
        myScoreLabel.setFont(new Font(null, Font.BOLD, FONT_SIZE));
        
        myFrame.pack();

    }
    
    
    /**
     * Method to start the game.
     */
    public void start() {
        myScorePanel.addPropertyChangeListener(this);
        myBoard.newGame();

        myFrame.setResizable(false);
        myFrame.setVisible(true);
        myFrame.pack();    
        setupKeyBinding();

    }
        
    
    /**
     * Method to start a new Game.
     */
    public void startGame() {
        start();
        myGameRunning = true;
        
        enableActionList(true);

        myTimer.start();
    }
    
    /**
     * Method to end the current game.
     */
    public void endGame()   {
        myTimer.stop();
        myGameRunning = false;
        enableActionList(false);  
    }
    
    /**
     * Method to reset the delay to 1000s for timer.
     */
    public void setTimerDelay() {
        myDelay = MY_DELAY;
        myTimer.setDelay(myDelay);
    }
    
    /**
     * 
     * @return true if player want to draw grid
     */
    public boolean getDrawGrid() {
        return myDrawGrid;
    }
    
    /**
     * Set draw grid to be true or not.
     * @param theBoolean true if want to draw grid
     */
    public void setDrawGrid(final boolean theBoolean) {
        myDrawGrid = theBoolean;
    }
    
    /**
     * This method setup the layout of the GUI.
     */
    private void setupGUI() {

        myFrame.setLayout(new GridBagLayout());

        final GBC c1 = new GBC(0, 0, 1, 4);
        c1.setInsets(20);
        
        final GBC c2 = new GBC(1, 0, 1, 1);
        c2.setInsets(40, 0, 10, 20);
        c2.anchor = GridBagConstraints.CENTER;
  
        final GBC c3 = new GBC(1, 1, 1, 1);

        c3.setInsets(0, 0, 30, 20);
        
        final GBC c4 = new GBC(1, 2, 1, 1);
        final int pad = 55;
        c4.setInsets(pad, 0, 0, 20);
 
        final GBC c5 = new GBC(1, 3, 1, 1);

        
        myFrame.add(myGamePanel, c1);
        myFrame.add(myNextLabel, c2);
        myFrame.add(myNextPiecePanel, c3);
        myFrame.add(myScoreLabel, c4);
        myFrame.add(myScorePanel, c5);
        
    }
    
    /**
     * Reset the grid size of the game board.
     * @param theW the new width
     * @param theH the new height
     */
    public void setGrid(final int theW, final int theH) {
        endGame();

        myFrame.remove(myGamePanel);
        myFrame.remove(myNextPiecePanel);
        myFrame.remove(myScorePanel);
        myFrame.remove(myScoreLabel);
        myFrame.remove(myNextLabel);
        myBoard = new Board(theW, theH);
        myGamePanel = new TetrisPanel(myBoard, this);
        myNextPiecePanel = new NextPiecePanel(myBoard, this);
        myScorePanel = new ScorePanel(myBoard, this);
        setupKeyBinding();
        enableActionList(true);

        setupGUI();
        start();
        myGamePanel.repaint();
        myScorePanel.repaint();

    }
    

    
    @Override
    public void update(final Observable arg0, final Object theObject) {
        
        if (theObject instanceof Boolean) {
            endGame();
        }       
        
        
    }
    
    // To handle change in level from Score Panel
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        // Get level update from Score Panel
        if (theEvent.getPropertyName().equals("level")) {
            final int minus = 200;
            if (myDelay > minus) {
                myDelay -= minus;
            }
            myTimer.setDelay(myDelay);
        }
        if (theEvent.getPropertyName().equals("new game")) {
            myScorePanel.resetScore();
        }
    }
    
    /**
     * Get the piece down for 1 row every delay interval of the timer.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        if (theEvent.getSource().equals(myTimer)) {
            myBoard.down();      
         
        }
        
    }
    
    /**
     * Helper method to setup keybinding for the GUI.
     */
    private void setupKeyBinding() {
        
        // Create an array list holds all the moving actions
        myActionList = new ArrayList<MyKeyAction>();
        myActionList.add(new MyKeyAction(R, myBoard));
        myActionList.add(new MyKeyAction(L, myBoard));
        myActionList.add(new MyKeyAction(RGT, myBoard));
        myActionList.add(new MyKeyAction(DN, myBoard));
        myActionList.add(new MyKeyAction(DR, myBoard));

        
        myGamePanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), R);
        myGamePanel.getActionMap().put(R, myActionList.get(0));
            
        myGamePanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), L);
        myGamePanel.getActionMap().put(L, myActionList.get(1));
            
        myGamePanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), RGT);
        myGamePanel.getActionMap().put(RGT, myActionList.get(2));
            
        myGamePanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), DN);
        myGamePanel.getActionMap().put(DN, myActionList.get(3));
            
        myGamePanel.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), DR);
        myGamePanel.getActionMap().put(DR, myActionList.get(4));
        
        
        myGamePanel.getInputMap().put(KeyStroke.getKeyStroke("P"), P);
        myGamePanel.getActionMap().put(P, new MyKeyAction(P, myBoard));
        
    }

    /**
     * Return the running state of the game.
     * @return the running state of the game
     */
    public boolean isGameRunning() {
        return myGameRunning;
    }

    
    
    /**
     * To check if timer is running.
     * @return running status of the timer
     */
    public boolean isTimerRunning() {
        final boolean result;
        if (myTimer != null) {
            result = myTimer.isRunning();
        } else {
            result = false;
        }
        return result;
    }
    
    // Method to be called when pause/unpause the game
    
    /**
     * To stop/start the timer.
     */
    public void setMyTimer() {
        if (myTimer.isRunning()) {
            myTimer.stop();
            myPauseGame = true;
            this.enableActionList(false);
        } else {
            myTimer.start();
            myPauseGame = false;
            this.enableActionList(true);

        }
    }
    
    /**
     * This method to be called when need to stop/start the timer.
     * @param theRunning the state of the timer
     */
    public void setMyTimer(final boolean theRunning) {
        if (theRunning) {
            myTimer.start();
            myPauseGame = false;
            this.enableActionList(true);

        }
        
        if (!theRunning) {
            myTimer.stop();
            myPauseGame = true;
            this.enableActionList(false);

        }
    }
    
    /** 
     * @return my game panel
     */
    public TetrisPanel getMyTetrisPanel() {
        return myGamePanel;
    }
    
    /**
     * 
     * @return my next piece panel
     */
    public NextPiecePanel getMyNextPiecePanel() {
        return myNextPiecePanel;
    }
    
    /**
     * @return the style of borders.
     */
    public Border getBorder() {
        return myBorder;
    }


    
    /**
     * Used to disable/enable the keyboard when needed.
     * @param theBoolean true if enable and vice versa.
     */
    public final void enableActionList(final boolean theBoolean) {
        if (myActionList != null) {
            for (int i = 0; i < 5; i++) { // 5 keys that move the pieces
                myActionList.get(i).setEnabled(theBoolean);
            }    
        }
    }
    
    /**
     * Inner customized class for key binding.
     * @author Trinh
     *
     */
    
    private final class MyKeyAction extends AbstractAction {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * The text to determine what kind of movement for the key.
         */
        private String myText;
        
        /**
         * The board data.
         */
        private Board myBoard;
        
        /**
         * Constructor.
         * @param theText text of movement
         * @param theBoard  board game
         */
        MyKeyAction(final String theText, final Board theBoard) {
            super();
            myText = theText;
            myBoard = theBoard;
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            if (R.equals(myText)) {
                myBoard.rotate();
            } else if (L.equals(myText)) {
                myBoard.left();
            } else if (RGT.equals(myText)) {
                myBoard.right();
            } else if (DN.equals(myText)) {
                myBoard.down();
            } else if (DR.equals(myText)) {
                myBoard.drop();
            } else if (P.equals(myText)) {
                // This will start/stop the timer and setup
                // the status for myPauseGame
                TetrisGUI.this.setMyTimer();
                
                // Enable or unable the actions base on the value of myPauseGame 
                enableActionList(!myPauseGame);
   
                    
            }
        }
        
    }
    

    
    /**
     * Private class to create constraints for gridbag layout using in the class.
     * @author Trinh
     *
     */
    private final class GBC extends GridBagConstraints  {
        
        /**
         * UID#.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor to create a gridbad constraint.
         * @param theX the X
         * @param theY the Y
         * @param theWidth the width
         * @param theHeight the height
         */
        GBC(final int theX, final int theY, final int theWidth, final int theHeight) {
            super();
            this.gridx = theX;
            this.gridy = theY;
            this.gridwidth = theWidth;
            this.gridheight = theHeight;
        }
        
        /**
         * Method to set the insets for the constraints.
         * @param thePad the dimension of the pad, 4 sides are the same
         * @return the constraints with insets setup
         */
        public GBC setInsets(final int thePad) {
            this.insets = new Insets(thePad, thePad, thePad, thePad);
            return this;
        }
        
        /**
         * Method to set the insets for the constraints.
         * @param theTop the top dimension of the pad
         * @param theLeft the left pad
         * @param theRight the right pad
         * @param theBottom the bottom pad
         * @return the constraints with insets setup
         */
        public GBC setInsets(final int theTop, final int theLeft,
                             final int theBottom, final int theRight) {
            this.insets = new Insets(theTop, theLeft, theBottom, theRight);
            return this;
        }
    } // end Class GBC



}
