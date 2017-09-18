/*
 * Tetris part B 
 * TCSS 305: Programming Practicum, Winter 2016
 */
package view;

import com.sun.glass.events.KeyEvent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.Board;
import sound.MusicPlayer;


/**
 * This class create a menu bar for the game.
 * @author Trinh
 * @version 1222016
 */
public class TetrisMenu extends JMenuBar implements Observer {
    
    /**
     * UID#.
     */
    private static final long serialVersionUID = 1L;
    
    /** A ToolKit. */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();
    
    /** The Dimension of the screen. */
    private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
    
    /**
     * Size of the blocks.
     */
    private static final int BLOCK_SIZE = 25;
    
    /**
     * Major tick of grid sliders.
     */
    private static final int MAJOR_TICK = 5;
    
    /**
     * Minimum tick of grid sliders.
     */
    private static final int MIN_TICK = 1;
    /**
     * The control menu.
     */
    private JMenu myGameControl;
    
    /**
     * The help menu.
     */
    private JMenu myHelp;
    
    /**
     * Option menu.
     */
    private JMenu myOption;
    
    /**
     * Look menu.
     */
    private JMenu myLook;
    
    /**
     * Music menu.
     */
    private JMenu myMusic;
    
    /**
     * The GUI.
     */
    private TetrisGUI myGUI;
    
    /**
     * The music player to play background music.
     */
    private MusicPlayer myPlayer;

    /**
     * The new game button.
     */
    private JMenuItem myNewGame;
    
    /**
     * The end game button.
     */
    private JMenuItem myEndGame;
    
    /**
     * The width of the board. Initial at 10.
     */
    private int myBoardWidth = 10;
    
    /**
     * The height of the board. Initial at 20.
     */
    private int myBoardHeight = 20;
    
    /**
     * Max width based on users screen.
     */
    private int myMaxBoardWidth;
    
    /**
     * Max height.
     */
    private int myMaxBoardHeight;
    
    /**
     * Outline color of the blocks.
     */
    private Color myFillColor;
    
    /**
     * Inside color of the blocks.
     */
    private Color myDrawColor;
    
    /**
     * Constructor.
     * @param theBoard the board data of the games
     * @param theGUI the gui of the game
     */
    public TetrisMenu(final Board theBoard,
                      final TetrisGUI theGUI) {
        super();
//       myBoard = theBoard;
        myGUI = theGUI;

        myNewGame = new JMenuItem("New Game");
        myEndGame = new JMenuItem("End Game");
        
        
        setupGameMenu();
        setupOptionMenu();
        setupLookMenu();
        setupMusic();
        setupHelpMenu();

        theBoard.addObserver(this);
    }

    /**
     * Helper method to setup Music menu.
     */
    private void setupMusic() {
        myPlayer = new MusicPlayer();

        myMusic = new JMenu("Music");
        final File music = new File("sounds/Tetris.mp3");

        final File[] list = {music};
        
        myPlayer.newList(list, false);
        
        final JRadioButtonMenuItem musicBut = new JRadioButtonMenuItem("Background music?");
        myMusic.add(musicBut);
        musicBut.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myPlayer.togglePause();
                
            }
            
        });
        
        this.add(myMusic);

    }

    /**
     * Helper method to setup the Look menu.
     */
    
    private void setupLookMenu() {
        myLook = new JMenu("Visual");

        final JRadioButtonMenuItem grid = new JRadioButtonMenuItem("Visual Grid");
        grid.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
             //   System.out.println(grid.isSelected());
                myGUI.setDrawGrid(grid.isSelected());
            }
            
        });
        
        myLook.add(grid);
        myLook.addSeparator();
        
        final String name = new String("A Color Chooser");

        final JMenuItem draw = new JMenuItem("Draw Color...", KeyEvent.VK_D);
        draw.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myDrawColor = JColorChooser.showDialog(null, name, null);
                if (myDrawColor != null) {
                    myGUI.getMyTetrisPanel().setDrawColor(myDrawColor);
                    myGUI.getMyNextPiecePanel().setDrawColor(myDrawColor);
                }
            }
            
        });
        final JMenuItem fill = new JMenuItem("Fill Color...", KeyEvent.VK_F); 
        fill.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myFillColor = JColorChooser.showDialog(null, name, null);
                if (myFillColor != null) {
                    myGUI.getMyTetrisPanel().setFillColor(myFillColor);
                    myGUI.getMyNextPiecePanel().setFillColor(myFillColor);

                }
            }
            
        });
        
        myLook.add(draw);
        myLook.add(fill);
        this.add(myLook);

        
    }
    /**
     * Setup method.
     */
    public final void setupHelpMenu() {
        
        myHelp = new JMenu("Help");

        final String string = "Key Controls";
        final JMenuItem keyControl = new JMenuItem(string);
        keyControl.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, "MOVE LEFT: Left Arrow\n" 
                                     + "MOVE RIGHT: Right Arrow\n" + "ROTATE: Up Arrow\n"
                                     + "SOFT DROP: Down Arrow\n" 
                                     + "HARD DROP: Space bar",
                                     string, JOptionPane.INFORMATION_MESSAGE);
                
            }
            
        });
  //      keyControl.addFocusListener(this);
        
        final String string2 = "Scoring Algorithm";
        final JMenuItem score = new JMenuItem(string2);
        score.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                
                JOptionPane.showMessageDialog(null, "1 line cleared : 100 point\n"
                                               + "2 lines cleared : 200 point\n"  
                                               + "3 lines cleared : 400 point\n" 
                                               + "4 lines cleared : 800 point\n",
                                               string2, JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        
        myHelp.add(keyControl);
        myHelp.addSeparator();
        myHelp.add(score);
        this.add(myHelp);

    } // end setupHelpMenu
    
    
    /**
     * Helper method to setup Game menu.
     */
    private void setupGameMenu() {
        
        myGameControl = new JMenu("Game");

        myEndGame.setEnabled(false);
        myNewGame.setAccelerator(KeyStroke.getKeyStroke('N',
                                                    java.awt.event.KeyEvent.CTRL_MASK));
        myNewGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myEndGame.setEnabled(true);
                myGUI.endGame();
                JOptionPane.showMessageDialog(null, "Start a new game");
                myGUI.setTimerDelay();
               // myGUI.setBoard();
                myGUI.startGame();
                myNewGame.setEnabled(false);

            }
            
        });
        
        myEndGame.setAccelerator(KeyStroke.getKeyStroke('E',
                                      java.awt.event.KeyEvent.CTRL_MASK));

        myEndGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGUI.endGame();
                myNewGame.setEnabled(true);
                JOptionPane.showMessageDialog(null, "GAME OVER");
                myEndGame.setEnabled(false);
            }
            
        });
        
        myGameControl.add(myNewGame);
        myGameControl.add(myEndGame);
        this.add(myGameControl);

    }
    
    /**
     * To see how big the grid should be based on users' screen size.
     */
    private void getMaxTick() {
      
        myMaxBoardWidth = (int) SCREEN_SIZE.getWidth() / 2 / BLOCK_SIZE;
        myMaxBoardHeight = (int) SCREEN_SIZE.getHeight() / BLOCK_SIZE - 10; 
    }
    
    
    /**
     * Helper method to setup the Option menu.
     */
    private void setupOptionMenu() {
        myOption = new JMenu("Option");

        getMaxTick();

        final String string = "Please click New Game to create a new board!";
        final JMenu boardWidth = new JMenu("Width grid");
        final JSlider widthSlider = new JSlider(SwingConstants.HORIZONTAL, 5,
                                                myMaxBoardWidth, 10);
        widthSlider.setMajorTickSpacing(MAJOR_TICK);
        widthSlider.setMinorTickSpacing(MIN_TICK);
        widthSlider.setPaintLabels(true);
        widthSlider.setPaintTicks(true);
        boardWidth.add(widthSlider);
        widthSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                final JSlider source = (JSlider) theEvent.getSource();
                if (!source.getValueIsAdjusting()) {
                    myBoardWidth = widthSlider.getValue();
    
                    myGUI.setGrid(myBoardWidth, myBoardHeight);
                    JOptionPane.showMessageDialog(null, string);
                    myEndGame.setEnabled(false);
                    myNewGame.setEnabled(true);
                }
            }
            
        });
        myOption.add(boardWidth);
        
        final JMenu boardHeight = new JMenu("Height grid");
        final JSlider heightSlider = new JSlider(SwingConstants.HORIZONTAL,
                                                 10, myMaxBoardHeight, 20);
        heightSlider.setMajorTickSpacing(MAJOR_TICK);
        heightSlider.setMinorTickSpacing(MIN_TICK);
        heightSlider.setPaintLabels(true);
        heightSlider.setPaintTicks(true);
        boardHeight.add(heightSlider);
        heightSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent theEvent) {
                final JSlider source = (JSlider) theEvent.getSource();
                if (!source.getValueIsAdjusting()) {
                    myBoardHeight = heightSlider.getValue();
                    
                    myGUI.setGrid(myBoardWidth, myBoardHeight);
                    JOptionPane.showMessageDialog(null, string);
    
                    myEndGame.setEnabled(false);
                    myNewGame.setEnabled(true);
                }
            }
            
        });
        myOption.add(boardHeight);    
        this.add(myOption);

    }


    @Override
    public void update(final Observable theObject, final Object theData) {

        if (theData instanceof Boolean) {
            myNewGame.setEnabled(true);
            myEndGame.setEnabled(false);
        }
    }


}
