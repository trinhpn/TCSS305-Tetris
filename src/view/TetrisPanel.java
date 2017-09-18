/*
 * Tetris part B
 * TCSS 305: Programming Practicum, Winter 2016
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Board;

/**
 * This class create a panel that will display the visual of the tetris game.
 * @author Trinh Pham
 * @version 1222016
 */
public class TetrisPanel extends JPanel implements Observer, FocusListener {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Size of the block.
     */
    private static final int BLOCK_SIZE = 25;
    
    /**
     * The starting row.
     */
    private static final int START_ROW = 5;
    
    /**
     * The starting column.
     */
    private static final int START_COLUMN = 1;
    
    /**
     * The roundness for the round rect.
     */
    private static final int CURVE = 10;
    
    /**
     * Split at the end of the toString of the Board.
     */
    private static final String SPLIT = "\n";

    /**
     * The width of the panel.
     */
    private int myWidth;
    
    /**
     * The height of the panel.
     */
    private int myHeight;
    

    
    /**
     * The board data of the game.
     */
    private final Board myBoard;
    
    /**
     * The string represents the blocks in process and frozen.
     */
    private String[] myList;
    
//    /**
//     * Boolean status of the game.
//     */
//    private Boolean myGameOver;

    /**
     * Dimension of the panel.
     */
    private Dimension myDimension;
    
    /**
     * The gui of the game.
     */
    private TetrisGUI myGUI;
    
    /**
     * Outside color of the blocks.
     */
    private Color myDrawColor;
    
    /**
     * Inside color of the blocks.
     */
    private Color myFillColor;
    
    /**
     * Pause status of the game.
     */
    private Boolean myPause;
    /**
     * Constructor of the tetris panel.
     * @param theBoard the board data
     * @param theGUI the gui in which the panel is a component of it
     */
    public TetrisPanel(final Board theBoard, final TetrisGUI theGUI) {
        super();
        this.addFocusListener(this);
        
        //this.addKeyListener(new MyKeyListener(theBoard, theGUI));
        myBoard = theBoard;
        myGUI = theGUI;
        this.setBorder(myGUI.getBorder());
        
        
        // This will split the String representation of the board into a String list
        // The first element will be the first row of the panel and so forth
        // The order of characters of the element will be the column
        myList = myBoard.toString().split(SPLIT);
        myPause = false;
        setup();

        
        theBoard.addObserver(this);
        
    }
    
    /**
     * Helper method setup the panel.
     */
    private void setup() {
        myWidth = myBoard.getWidth() * BLOCK_SIZE;
        myHeight = myBoard.getHeight() * BLOCK_SIZE;
        myDimension = new Dimension(myWidth + 1 , myHeight  + 1);
        this.setPreferredSize(myDimension);
        this.setBackground(Color.white);
        
        myDrawColor = Color.blue.darker();
        myFillColor = Color.ORANGE.brighter();
    }
        
    @Override
    public void update(final Observable theO, final Object theObject) {
        
        
        if (theObject instanceof Boolean) {

            JOptionPane.showMessageDialog(this, "Game Over");
            
        }
        if (theObject instanceof String) {
         //   myGameOver = false;
            myList = myBoard.toString().split(SPLIT);
            repaint();
        }
        
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);   
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        g2d.setColor(Color.gray.brighter());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                             RenderingHints.VALUE_ANTIALIAS_ON);
        myWidth = myBoard.getWidth() * BLOCK_SIZE;
        myHeight = myBoard.getHeight() * BLOCK_SIZE;
        myDimension = new Dimension(myWidth + 1 , myHeight  + 1);
        
        // If users want to draw grid
        if (myGUI.getDrawGrid()) {
            drawGrid(g2d);
        }    
        
        
        if (!myPause) {                  
            drawBlock(g2d);
        } 
    }
    /**
     * Method to draw the blocks of the board, including the frozen pieces and the
     * one in process.
     * @param theGraphics the Object to draw
     */
    private void drawBlock(final Graphics theGraphics) {
        final int stroke = 4;
        if (myList != null) {
            // Outer loop for row
            // We start at 5 because the first 4 is just storing the next piece
            for (int i = START_ROW; i < myList.length - 1; i++) { 
                // Inner loop for column
                // We start at 1 because the first one is just the wall
                for (int j = 1; j < myList[i].length() - 1; j++) {
                    // Draw anything not an empty string as a block
                    if (myList[i].charAt(j) != ' ') { 
                        ((Graphics2D) theGraphics).setStroke(new BasicStroke(stroke));
                        theGraphics.setColor(myDrawColor);
                        
                        
                        theGraphics.drawRoundRect((j - START_COLUMN) * BLOCK_SIZE,
                                              (i - START_ROW) * BLOCK_SIZE,
                                              BLOCK_SIZE, BLOCK_SIZE, CURVE, CURVE);
                        
                        theGraphics.setColor(myFillColor);
    
                        theGraphics.fillRoundRect((j - START_COLUMN) * BLOCK_SIZE,
                                              (i - START_ROW) * BLOCK_SIZE,
                                              BLOCK_SIZE, BLOCK_SIZE, CURVE, CURVE);
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @param theColor the outline color of the blocks
     */
    public void setDrawColor(final Color theColor) {
        myDrawColor = theColor;
    }
    
    /**
     *
     * @param theColor the inside color of the blocks
     */
    public void setFillColor(final Color theColor) {
        myFillColor = theColor;
    }
    
    /**
     * Method to draw the grid of the panel.
     * @param theGraphics the object to draw
     */
    private void drawGrid(final Graphics theGraphics) {
        final int numRow = myBoard.getHeight();
        final int numColumn = myBoard.getWidth();
        
        
     // Draw horizontal grid lines
        for (int i = 0; i <= numRow; i++) {
            theGraphics.drawLine(0, i * BLOCK_SIZE, myWidth, i * BLOCK_SIZE);   
        }
        // Draw vertical grid lines
        for (int i = 0; i <= numColumn; i++) {
            theGraphics.drawLine(i * BLOCK_SIZE, 0, i * BLOCK_SIZE, myHeight);   
        }
        

    }
    

    @Override
    public void focusGained(final FocusEvent theEvent) {
        myPause = false;

        if (myGUI.isGameRunning()) {
            
            myGUI.setMyTimer(true);
            
        }

    }

    @Override
    public void focusLost(final FocusEvent theEvent) {
        myPause = true;
        myGUI.setMyTimer(false);
    }


} 
