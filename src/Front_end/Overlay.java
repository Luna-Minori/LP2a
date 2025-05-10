package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Overlay panel used to display a semi-transparent pause menu or scoreboard over the game.
 */
public class Overlay extends JPanel {
    private Consumer<Integer> onPauseClicked;
    private Consumer<Integer> onNextRoundClicked;
    private TextPanel title;
    private TextPanel setting;
    private TextPanel leave;
    private TextPanel game;
    private TextPanel scoreButton;
    private ScoreBoard scorePanel;

    /**
     * Constructs the overlay with score or pause content.
     * @param showScore whether to show the scoreboard (true) or the pause menu (false)
     * @param parentWidth width of the parent component
     * @param parentHeight height of the parent component
     * @param names player names
     * @param scores corresponding scores
     */
    public Overlay(boolean showScore, int parentWidth, int parentHeight, ArrayList<String> names, ArrayList<Integer> scores) {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 200));
        setBounds(0, 0, parentWidth, parentHeight);

        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseAdapter() {});

        flipMode(showScore);
    }

    public Overlay(boolean score, int parentWidth, int parentHeight) {
        this(score, parentWidth, parentHeight, null, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void showOverlay(BoardPanel rootPane) {
        setVisible(true);
    }

    public void hideOverlay() {
        setVisible(false);
    }

    /**
     * Switches the overlay display mode between pause menu and scoreboard.
     *
     * @param showScore true to display the scoreboard, false to display the pause menu
     */
    protected void flipMode(boolean showScore) {
        removeAll(); // Remove all existing components

        if (showScore) {
            // Create a new scoreboard with empty data or reuse stored data if available
            scorePanel = new ScoreBoard(new ArrayList<>(), new ArrayList<>());
            scorePanel.setBounds(getWidth() / 4, getHeight() / 4, 500, 300);
            setScoreButton();
            adjustScoreButtonPosition();
            add(scorePanel);
        } else {
            // Create the pause menu
            createPauseMenu();
            adjustPauseMenuPosition();
        }

        revalidate(); // Refresh layout
        repaint();    // Repaint panel
    }


    private void setScoreButton() {
        scoreButton = new TextPanel("Next round");
        scoreButton.setFont(new Font("Uncial Antiqua", Font.BOLD, 30));
        scoreButton.setForeground(Color.WHITE);

        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Font baseFont = text.getFont();
                Map<TextAttribute, Object> attrs = new HashMap<>(baseFont.getAttributes());
                attrs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Map<TextAttribute, Object> attrs = new HashMap<>(text.getFont().getAttributes());
                attrs.remove(TextAttribute.UNDERLINE);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                hideOverlay();
                click(4);
            }
        };

        add(scoreButton);
        scoreButton.addMouseListener(hoverEffect);
    }

    private void createPauseMenu() {
        title = new TextPanel("Pause");
        title.setFont(new Font("MedievalSharp", Font.BOLD, 35));
        title.setForeground(Color.WHITE);

        game = new TextPanel("Play");
        setting = new TextPanel("Setting");
        leave = new TextPanel("Quit");

        Font optionFont = new Font("Uncial Antiqua", Font.BOLD, 30);
        game.setFont(optionFont);
        setting.setFont(optionFont);
        leave.setFont(optionFont);

        game.setForeground(Color.WHITE);
        setting.setForeground(Color.WHITE);
        leave.setForeground(Color.WHITE);

        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Font baseFont = text.getFont();
                Map<TextAttribute, Object> attrs = new HashMap<>(baseFont.getAttributes());
                attrs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Map<TextAttribute, Object> attrs = new HashMap<>(text.getFont().getAttributes());
                attrs.remove(TextAttribute.UNDERLINE);
                text.setFont(new Font(attrs));
                text.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == leave) {
                    click(3);
                } else if (e.getSource() == game) {
                    click(1);
                } else if (e.getSource() == setting) {
                    click(2);
                }
            }
        };

        add(title);
        add(game);
        add(setting);
        add(leave);

        game.addMouseListener(hoverEffect);
        setting.addMouseListener(hoverEffect);
        leave.addMouseListener(hoverEffect);
    }

    private void adjustPauseMenuPosition() {
        int y = getHeight() / 4;
        int height = (int) (getHeight() * 0.1);
        int spacing = 20;

        int titleWidth = title.getText().length() * 20;
        int settingWidth = setting.getText().length() * 20;
        int leaveWidth = leave.getText().length() * 20;
        int gameWidth = game.getText().length() * 20;

        int centerX = getWidth() / 2;

        title.setBounds(centerX - titleWidth / 2, y, titleWidth, height);
        game.setBounds(centerX - gameWidth / 2, y + height + spacing, gameWidth, height);
        setting.setBounds(centerX - settingWidth / 2, y + 2 * (height + spacing), settingWidth, height);
        leave.setBounds(centerX - leaveWidth / 2, y + 3 * (height + spacing), leaveWidth, height);
    }

    private void adjustScoreButtonPosition() {
        int width = scoreButton.getText().length() * 20;
        int y = (int) (scorePanel.getHeight() * 1.1);
        scoreButton.setBounds(getWidth() / 2 - width / 2, y, width, (int) (getHeight() * 0.2));
    }

    private void click(int value) {
        if (value == 4 && onNextRoundClicked != null) {
            onNextRoundClicked.accept(value);
        } else if (onPauseClicked != null) {
            onPauseClicked.accept(value);
        }
    }

    /**
     * Registers a listener for pause menu options (1=Play, 2=Setting, 3=Quit).
     */
    public void setPauseClicked(Consumer<Integer> listener) {
        this.onPauseClicked = listener;
    }

    /**
     * Registers a listener for the "Next round" button.
     */
    public void setNextRoundClicked(Consumer<Integer> listener) {
        this.onNextRoundClicked = listener;
    }

    /**
     * Updates the scoreboard with new names and scores.
     */
    protected void update(ArrayList<String> names, ArrayList<Integer> scores) {
        scorePanel.update(names, scores);
        scorePanel.repaint();
    }
}
