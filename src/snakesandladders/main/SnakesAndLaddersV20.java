/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders.main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import snakesandladders.main.Board.Difficulty;

/**
 *
 * @author Zac
 */
public class SnakesAndLaddersV20 extends JFrame {

    /**
     * Starts the game
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakesAndLaddersV20::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        SnakesAndLaddersV20 snakesAndLadders = new SnakesAndLaddersV20();
    }

    //Replacement for clone maybe?
    /**
     *
     */
    public int timesGravityHasChanged = 0;

    //Fields
    SnakesAndLaddersV20 snl = this; //Used in action listeners since the class cannot be accessed directly
    //Swing=====================================================================
    private GridBagConstraints gbc = new GridBagConstraints();
    private Border redBorder = BorderFactory.createLineBorder(Color.RED);
    private JMenuBar menuBar;
    private JPanel leftPane, midPane, rightPane, startScreen;
    private JTextArea historyTArea;
    private JScrollPane scrollPane;
    private JToggleButton rollButton;
    //StartScreen===============================================================
    private JFormattedTextField nameField;
    private ButtonGroup toggleButtons, radioButtons;
    private JToggleButton[] pawnButtons;
    private JRadioButton[] diffButtons;
    //My Classes================================================================
    private Assets assets = new Assets();
    private History history;
    private GUIDice die;
    private SnakeTimer timerField;
    private PlayerPanel[] playerPanes;
    private BoardPanel boardPanel;
    //==========================================================================

    //Logic=====================================================================
    private Random random;
    private Board board, boardCopy;
    private JPanel placeholder; //soc code
    private Player[] players;
    private Player currentPlayer;
    private boolean endTurnCondition, endGameCondition;

    //==========================================================================
    //Enums=====================================================================
    private enum Scene {
        START_SCREEN, GAME, NONE
    }

    //Constructors==============================================================
    /**
     * Calls functions to build GUI then shows start screen to start the game
     */
    public SnakesAndLaddersV20() {

        random = new Random();
        placeholder = new JPanel(); //soc code
        LayoutManager overlay = new OverlayLayout(placeholder); //soc code
        placeholder.setLayout(overlay); //soc code

        menuBar = buildMenuBar();
        startScreen = buildStartScreen();
        leftPane = buildLeftPane();
        midPane = buildMidPane();
        rightPane = buildRightPane();

        //Hiding everything, what is shown will be handled by method(s)
        startScreen.setVisible(false);
        leftPane.setVisible(false);
        midPane.setVisible(false);
        rightPane.setVisible(false);

        setTitle("Snakes And Ladders");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setJMenuBar(menuBar);
        add(leftPane, BorderLayout.WEST);
        add(midPane, BorderLayout.CENTER);
        add(rightPane, BorderLayout.EAST);

        //DEBUGGING
        switchScene(Scene.START_SCREEN);

        setVisible(true);

    }

    //Methods===================================================================
    //General Use Methods=======================================================
    //Handles which panels are visible
    private void switchScene(Scene scene) {

        switch (scene) {
            case START_SCREEN:
                switchScene(Scene.NONE);
                midPane.setVisible(true);
                startScreen.setVisible(true);
                midPane.add(startScreen);
                break;
            case GAME:
                switchScene(Scene.NONE);
                leftPane.setVisible(true);
                midPane.setVisible(true);
                rightPane.setVisible(true);
                midPane.add(placeholder);
                placeholder.add(boardPanel); //soc code
                placeholder.add(board); //soc code
                break;
            case NONE:
                midPane.removeAll();
                placeholder.removeAll();
                leftPane.setVisible(false);
                startScreen.setVisible(false);
                midPane.setVisible(false);
                rightPane.setVisible(false);
                break;
        }

    }

    private void gbcReset() {
        gbc = new GridBagConstraints();
    }

    //Used to skip writing try-catch everytime sleep is used
    private void mySleep(int time) {
        try {
            sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(SnakesAndLaddersV20.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeFrame() {
        dispose();
    }

    private Player getOtherPlayer() {
        for (Player player : players) {
            if (player != currentPlayer) {
                return player;
            }
        }

        return null;
    }

    private void updateGUI() {
        board.updatePlayers(players[0].getSquare().getNumber(), players[1].getSquare().getNumber()); //soc code

        for (int i = 0; i < 2; i++) {
            playerPanes[i].updatePosition(players[i].getSquare().getNumber());
        }
        historyTArea.setText(history.getHistory());
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()); //Should move scrollbar on text area all the way down but doesn't <BUG>
    }

    //Lazy and unreliable method should be replaced/removed
    //It doesn't work no idea why <BUG>
    private void highlightPlayer(Player player) {
        if (player == players[0]) {
            playerPanes[0].boldenName();
            playerPanes[1].unBoldenName();
        } else {
            playerPanes[1].boldenName();
            playerPanes[0].unBoldenName();
        }
    }

    //Specific Methods <BETTER NAME>============================================
    private String getCOMColor(String pColor) {

        String COMColor;
        String[] colors = new String[4];
        colors[0] = "pawn_red";
        colors[1] = "pawn_blue";
        colors[2] = "pawn_magenta";
        colors[3] = "pawn_green";

        COMColor = colors[random.nextInt(4)];
        while (COMColor.equals(pColor)) {
            COMColor = colors[random.nextInt(4)];
        }

        return COMColor;
    }

    /**
     * Moves the player and applies effect calling the appropriate methods
     *
     * @param result
     */
    public void movementAndEffects(int result) {

        currentPlayer.advance(result, board);
        history.append(currentPlayer.getName() + " moves to square " + currentPlayer.getSquare().getNumber());
        updateGUI();

        currentPlayer.getSquare().applyEffect(currentPlayer, getOtherPlayer(), board, snl);

        updateGUI();
    }

    private void endTurn() {
        currentPlayer = getOtherPlayer();
        rollButton.setSelected(false);
        rollButton.setEnabled(true);
        highlightPlayer(currentPlayer);
        if (currentPlayer.getName() == "Computer") {
            mySleep(300);
            rollButton.doClick();
        }
    }

    //Main Panel Building Methods===============================================
    private JMenuBar buildMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenu optionsMenu = new JMenu("Options");
        JMenu helpMenu = new JMenu("Help");
        JMenu historySubMenu = new JMenu("History");
        JMenuItem menuItem;
        ActionListener actionListener;

        //Logic
        menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(new NewGameListener());
        gameMenu.add(menuItem);
        menuItem = new JMenuItem("Restart");
        menuItem.addActionListener(new ResetListener());
        gameMenu.add(menuItem);
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ExitListener());
        gameMenu.add(menuItem);

        optionsMenu.add(historySubMenu);
        menuItem = new JMenuItem("Show History");
        menuItem.addActionListener(new ShowHistoryListener());
        historySubMenu.add(menuItem);
        menuItem = new JMenuItem("Hide History");
        menuItem.addActionListener(new HideHistoryListener());
        historySubMenu.add(menuItem);

        menuItem = new JMenuItem("About");
        helpMenu.add(menuItem);

        menuBar.add(gameMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);

        return menuBar;

    }

    private JPanel buildLeftPane() {

        JPanel leftPane = new JPanel();
        JLabel historyLabel = new JLabel("History");
        historyTArea = new JTextArea();
        scrollPane = new JScrollPane(historyTArea);

        //Logic
        leftPane.setLayout(new BoxLayout(leftPane, BoxLayout.PAGE_AXIS));
        leftPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        leftPane.setPreferredSize(new Dimension(320, 700));

        historyLabel.setAlignmentX(0);

        scrollPane.setAlignmentX(0);

        historyTArea.setEditable(false);
        historyTArea.setAlignmentX(0);

        leftPane.add(historyLabel);
        leftPane.add(scrollPane);

        return leftPane;

    }

    private JPanel buildMidPane() {

        JPanel midPane = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.RED);
        gbcReset();

        //Logic
        midPane.setLayout(new BorderLayout());
        //midPane.setBorder(border);

        midPane.add(startScreen);

        return midPane;

    }

    private JPanel buildRightPane() {

        JPanel rightPane = new JPanel();
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        JPanel rightUpPane = new JPanel(new GridBagLayout());
        JPanel rightMidPane = new JPanel(new GridBagLayout());
        JPanel rightDownPane = new JPanel(new GridBagLayout());
        rollButton = new JToggleButton("Roll");
        JLabel timerLabel = new JLabel("TIMER");
        die = new GUIDice();
        timerField = new SnakeTimer();
        playerPanes = new PlayerPanel[2];
        playerPanes[0] = new PlayerPanel("Player", "pawn_blue");
        playerPanes[1] = new PlayerPanel("Computer", "pawn_red");
        gbcReset();

        //Logic
        rollButton.addActionListener(new RollButtonListener());
        //rightPane.setBorder(blackBorder);
        rightPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.PAGE_AXIS));
        rightPane.setPreferredSize(new Dimension(320, 700));

        timerLabel.setFont(new Font("Serif", Font.BOLD, 22));

        //rightUpPane.setBorder(blackBorder);
        rightUpPane.setPreferredSize(new Dimension(320, 230));
        rightUpPane.add(timerLabel);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        rightUpPane.add(timerField, gbc);
        gbcReset();

        //rightMidPane.setBorder(blackBorder);
        //rightMidPane.setBackground(Color.decode("#e0e0e0"));
        rightMidPane.setPreferredSize(new Dimension(320, 230));
        rightMidPane.add(die);
        gbc.insets = new Insets(10, 0, 0, 0);
        gbc.fill = gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 1;
        gbc.ipady = 15;
        rightMidPane.add(rollButton, gbc);
        gbcReset();

        //rightDownPane.setBorder(blackBorder);
        //rightDownPane.setBackground(Color.decode("#e0e0e0"));
        rightDownPane.setPreferredSize(new Dimension(320, 240));
        rightDownPane.add(playerPanes[0], gbc);
        gbc.gridy = 1;
        rightDownPane.add(playerPanes[1], gbc);
        gbcReset();

        rightPane.add(rightUpPane);
        rightPane.add(rightMidPane);
        rightPane.add(rightDownPane);

        return rightPane;

    }

    private JPanel buildStartScreen() {

        JPanel startScreen = new JPanel();
        JLabel nameLabel = new JLabel("Player Name");
        nameField = new JFormattedTextField();
        ImageIcon[] pawnImg = new ImageIcon[4];
        pawnButtons = new JToggleButton[4];
        JLabel diffLabel = new JLabel("Difficulty");
        diffButtons = new JRadioButton[3];
        toggleButtons = new ButtonGroup();
        radioButtons = new ButtonGroup();
        JButton startButton = new JButton("Start Game");
        JPanel upPane = new JPanel(new GridBagLayout());
        JPanel downPane = new JPanel(new GridBagLayout());
        JPanel namePane = new JPanel(new GridBagLayout());
        gbcReset();

        //Logic
        startScreen.setLayout(new GridBagLayout());
        //startScreen.setPreferredSize(new Dimension(600, 600));

        nameField.setColumns(10);
        nameField.setDocument(new JTextFieldLimit(12));

        pawnImg[0] = assets.getResizedIcon("pawn_blue", 100, 100);
        pawnImg[1] = assets.getResizedIcon("pawn_green", 100, 100);
        pawnImg[2] = assets.getResizedIcon("pawn_magenta", 100, 100);
        pawnImg[3] = assets.getResizedIcon("pawn_red", 100, 100);

        for (int i = 0; i < 4; i++) {

            pawnButtons[i] = new JToggleButton();
            //pawnButton[i].setBackground(Color.BLACK);
            pawnButtons[i].setIcon(pawnImg[i]);
            pawnButtons[i].setPreferredSize(new Dimension(100, 100));

        }
        //Action commands are used to determine what action will be taken by the startScreen
        pawnButtons[0].setActionCommand("pawn_blue");
        pawnButtons[1].setActionCommand("pawn_green");
        pawnButtons[2].setActionCommand("pawn_magenta");
        pawnButtons[3].setActionCommand("pawn_red");
        pawnButtons[0].setSelected(true);

        diffLabel.setFont(new Font("Dialog", Font.BOLD, 15));

        diffButtons[0] = new JRadioButton("Easy");
        diffButtons[1] = new JRadioButton("Normal");
        diffButtons[2] = new JRadioButton("Hard");

        diffButtons[0].setActionCommand("easy");
        diffButtons[1].setActionCommand("normal");
        diffButtons[2].setActionCommand("hard");

        diffButtons[1].setSelected(true);

        for (JToggleButton i : pawnButtons) {
            toggleButtons.add(i);
        }

        for (JRadioButton i : diffButtons) {
            radioButtons.add(i);
        }

        startButton.addActionListener(new StartGameButtonListener());

        namePane.add(nameLabel, gbc);
        gbc.insets = new Insets(0, 8, 0, 0);
        namePane.add(nameField, gbc);
        gbcReset();

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.insets = new Insets(25, 0, 25, 0);
        upPane.add(namePane, gbc);
        gbcReset();
        gbc.gridy = 1;
        gbc.insets = new Insets(25, 15, 50, 15);
        for (JToggleButton i : pawnButtons) {
            upPane.add(i, gbc);
        }
        gbcReset();

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        downPane.add(diffLabel, gbc);
        gbcReset();
        gbc.gridy = 1;
        for (JRadioButton i : diffButtons) {
            downPane.add(i, gbc);
        }
        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.insets = new Insets(50, 0, 0, 0);
        gbc.ipadx = 20;
        gbc.ipady = 10;
        downPane.add(startButton, gbc);
        gbcReset();

        startScreen.add(upPane, gbc);
        gbc.gridy = 1;
        startScreen.add(downPane, gbc);

        return startScreen;

    }

    //Listeners=================================================================
    private class RollButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Thread thread = new Thread() {
                int result;

                public void run() {
                    //Play Animation and wait for it to finish
                    rollButton.setSelected(true);
                    rollButton.setEnabled(false);
                    result = die.roll();
                    history.append(currentPlayer.getName() + " rolls a " + result);
                    //Move player and apply neccessary effects
                    movementAndEffects(result);
                    //Switch player and enable roll button
                    if (endTurnCondition) {
                        endTurn();
                    }
                    if (!endGameCondition) {
                        endTurnCondition = true;
                    }
                    if (endGameCondition) {
                        rollButton.setSelected(true);
                        rollButton.setEnabled(false);
                    }
                }
            };
            thread.start();
        }

    }

    private class StartGameButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            rollButton.setSelected(false);
            rollButton.setEnabled(true);

            String playerName, playerColor, COMColor;
            players = new Player[2];
            history = new History();

            endTurnCondition = true;
            endGameCondition = false;

            playerName = nameField.getText();
            playerColor = toggleButtons.getSelection().getActionCommand();
            COMColor = getCOMColor(playerColor);

            playerPanes[0].updateName(playerName);
            playerPanes[0].updateColor(playerColor);
            playerPanes[1].updateName("Computer");
            playerPanes[1].updateColor(COMColor);

            switch (radioButtons.getSelection().getActionCommand()) {
                case "easy":
                    board = new Board(Difficulty.EASY);
                    break;
                case "normal":
                    board = new Board(Difficulty.NORMAL);
                    break;
                case "hard":
                    board = new Board(Difficulty.HARD);
                    break;
            }

            players[0] = new Player(board.getBoardSquare(1), playerName, playerColor);
            players[0].setColor(playerColor);//soc code
            players[1] = new Player(board.getBoardSquare(1), "Computer", COMColor);
            players[1].setColor(COMColor);// soc code
            board.setPlayers(players[0], players[1]);
            currentPlayer = players[random.nextInt(2)]; //Select a random player to go first
            highlightPlayer(currentPlayer);
            if (currentPlayer.getName() == "Computer") {
                rollButton.doClick();
            }

            boardPanel = new BoardPanel(board); //soc code

            //=========
            history.append("Game is Starting");
            history.append(currentPlayer.getName() + " goes first");

            timerField.reset();
            switchScene(Scene.GAME);
            updateGUI();

            nameField.setText("");
            pawnButtons[0].setSelected(true);
            diffButtons[1].setSelected(true);

        }

    }

    private class NewGameListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            history.reset();
            switchScene(Scene.START_SCREEN);

        }

    }

    private class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            endGameCondition = false;

            rollButton.setSelected(false);
            rollButton.setEnabled(true);
            history.reset();

            board.reset(snl);
//            board = boardCopy.clone();
            for (Player player : players) {
                player.reset(board);
            }
            //Resetting Gui
            updateGUI();
            die.reset();
            timerField.reset(); //Does not display 00:00:00 when reset <BUG>
            //reset timer, history etc

        }

    }

    private class ExitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            closeFrame();
        }
    }

    private class ShowHistoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            history.setHidden(false);
            historyTArea.setText(history.getHistory());
        }

    }

    private class HideHistoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            history.setHidden(true);
            historyTArea.setText(history.getHistory());
        }

    }

    //Other=====================================================================
    //Prevents user from inserting more than a certain number of character in a JTextField (used when getting name from startScreen)
    class JTextFieldLimit extends PlainDocument {

        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        JTextFieldLimit(int limit, boolean upper) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    //GetterSetters
    /**
     *
     * @return
     */
    public SnakesAndLaddersV20 getSnl() {
        return snl;
    }

    /**
     *
     * @param snl
     */
    public void setSnl(SnakesAndLaddersV20 snl) {
        this.snl = snl;
    }

    /**
     *
     * @return
     */
    public GridBagConstraints getGbc() {
        return gbc;
    }

    /**
     *
     * @param gbc
     */
    public void setGbc(GridBagConstraints gbc) {
        this.gbc = gbc;
    }

    /**
     *
     * @return
     */
    public Border getRedBorder() {
        return redBorder;
    }

    /**
     *
     * @param redBorder
     */
    public void setRedBorder(Border redBorder) {
        this.redBorder = redBorder;
    }

    /**
     *
     * @param menuBar
     */
    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    /**
     *
     * @return
     */
    public JPanel getLeftPane() {
        return leftPane;
    }

    /**
     *
     * @param leftPane
     */
    public void setLeftPane(JPanel leftPane) {
        this.leftPane = leftPane;
    }

    /**
     *
     * @return
     */
    public JPanel getMidPane() {
        return midPane;
    }

    /**
     *
     * @param midPane
     */
    public void setMidPane(JPanel midPane) {
        this.midPane = midPane;
    }

    /**
     *
     * @return
     */
    public JPanel getRightPane() {
        return rightPane;
    }

    /**
     *
     * @param rightPane
     */
    public void setRightPane(JPanel rightPane) {
        this.rightPane = rightPane;
    }

    /**
     *
     * @return
     */
    public JPanel getStartScreen() {
        return startScreen;
    }

    /**
     *
     * @param startScreen
     */
    public void setStartScreen(JPanel startScreen) {
        this.startScreen = startScreen;
    }

    /**
     *
     * @return
     */
    public JTextArea getHistoryTArea() {
        return historyTArea;
    }

    /**
     *
     * @param historyTArea
     */
    public void setHistoryTArea(JTextArea historyTArea) {
        this.historyTArea = historyTArea;
    }

    /**
     *
     * @return
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     *
     * @param scrollPane
     */
    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    /**
     *
     * @return
     */
    public JToggleButton getRollButton() {
        return rollButton;
    }

    /**
     *
     * @param rollButton
     */
    public void setRollButton(JToggleButton rollButton) {
        this.rollButton = rollButton;
    }

    /**
     *
     * @return
     */
    public JFormattedTextField getNameField() {
        return nameField;
    }

    /**
     *
     * @param nameField
     */
    public void setNameField(JFormattedTextField nameField) {
        this.nameField = nameField;
    }

    /**
     *
     * @return
     */
    public ButtonGroup getToggleButtons() {
        return toggleButtons;
    }

    /**
     *
     * @param toggleButtons
     */
    public void setToggleButtons(ButtonGroup toggleButtons) {
        this.toggleButtons = toggleButtons;
    }

    /**
     *
     * @return
     */
    public ButtonGroup getRadioButtons() {
        return radioButtons;
    }

    /**
     *
     * @param radioButtons
     */
    public void setRadioButtons(ButtonGroup radioButtons) {
        this.radioButtons = radioButtons;
    }

    /**
     *
     * @return
     */
    public Assets getAssets() {
        return assets;
    }

    /**
     *
     * @param assets
     */
    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    /**
     *
     * @return
     */
    public GUIDice getDie() {
        return die;
    }

    /**
     *
     * @param die
     */
    public void setDie(GUIDice die) {
        this.die = die;
    }

    /**
     *
     * @return
     */
    public SnakeTimer getTimerField() {
        return timerField;
    }

    /**
     *
     * @param timerField
     */
    public void setTimerField(SnakeTimer timerField) {
        this.timerField = timerField;
    }

    /**
     *
     * @return
     */
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     *
     * @param boardPanel
     */
    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    /**
     *
     * @return
     */
    public Random getRandom() {
        return random;
    }

    /**
     *
     * @param random
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     *
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     *
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     *
     * @return
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @param currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     *
     * @return
     */
    public boolean isEndTurnCondition() {
        return endTurnCondition;
    }

    /**
     *
     * @param endTurnCondition
     */
    public void setEndTurnCondition(boolean endTurnCondition) {
        this.endTurnCondition = endTurnCondition;
    }

    /**
     *
     * @return
     */
    public boolean isEndGameCondition() {
        return endGameCondition;
    }

    /**
     *
     * @param endGameCondition
     */
    public void setEndGameCondition(boolean endGameCondition) {
        this.endGameCondition = endGameCondition;
    }

    /**
     *
     * @return
     */
    public JToggleButton[] getPawnButtons() {
        return pawnButtons;
    }

    /**
     *
     * @param pawnButtons
     */
    public void setPawnButtons(JToggleButton[] pawnButtons) {
        this.pawnButtons = pawnButtons;
    }

    /**
     *
     * @return
     */
    public JRadioButton[] getDiffButtons() {
        return diffButtons;
    }

    /**
     *
     * @param diffButtons
     */
    public void setDiffButtons(JRadioButton[] diffButtons) {
        this.diffButtons = diffButtons;
    }

    /**
     *
     * @return
     */
    public History getHistory() {
        return history;
    }

    /**
     *
     * @param history
     */
    public void setHistory(History history) {
        this.history = history;
    }

    /**
     *
     * @return
     */
    public Board getBoardCopy() {
        return boardCopy;
    }

    /**
     *
     * @param boardCopy
     */
    public void setBoardCopy(Board boardCopy) {
        this.boardCopy = boardCopy;
    }

}
