package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Overlay extends JPanel {
    private Consumer<Integer> onPauseClicked;
    private TextPanel title;
    private TextPanel setting;
    private TextPanel leave;
    private TextPanel game;

    public Overlay(boolean score, int parentWidth, int parentHeigh) {
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 200));
        setBounds(0, 0, parentWidth, parentHeigh);

        addMouseListener(new MouseAdapter() {
        });
        addMouseMotionListener(new MouseAdapter() {
        });
        pause();
        adjustTextPanelSize();
        if (score) {

        } else {
            pause();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // fond noir semi-transparent
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        adjustTextPanelSize();
    }

    public void showOverlay(BoardPanel rootPane) {
        //rootPane.setGlassPane(this);
        setVisible(true);
    }

    public void hideOverlay() {
        setVisible(false);
    }

    public void pause() {
        title = new TextPanel("Pause");
        title.setFont(new Font("MedievalSharp", Font.BOLD, 35));
        title.setForeground(Color.WHITE);
        game = new TextPanel("Play");
        game.setFont(new Font("MedievalSharp", Font.BOLD, 30));
        game.setForeground(Color.WHITE);
        setting = new TextPanel("Setting");
        setting.setFont(new Font("Uncial Antiqua", Font.BOLD, 30));
        setting.setForeground(Color.WHITE);
        leave = new TextPanel("Quit");
        leave.setFont(new Font("Uncial Antiqua", Font.BOLD, 30));
        leave.setForeground(Color.WHITE);

        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // underline when hover
                TextPanel text = (TextPanel) e.getSource();
                Font baseFont = text.getFont();
                Map<TextAttribute, Object> attrs = new HashMap<>(baseFont.getAttributes());
                attrs.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                Font underlinedFont = new Font(attrs);
                text.setFont(underlinedFont);
                text.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                TextPanel text = (TextPanel) e.getSource();
                Font underlinedFont = text.getFont();

                // Enlève le soulignement : on part de la font actuelle sans attribut UNDERLINE
                Map<TextAttribute, Object> attrs = new HashMap<>(underlinedFont.getAttributes());
                attrs.remove(TextAttribute.UNDERLINE);
                Font baseFont = new Font(attrs);

                text.setFont(baseFont);
                text.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Action à effectuer lorsque la carte est cliquée
                if(e.getSource() == leave) {
                    System.out.println("Leave clicked");
                    Click(3);
                } else if(e.getSource() == game) {
                    System.out.println("Game clicked");
                    Click(1);
                } else if(e.getSource() == setting) {
                    System.out.println("Setting clicked");
                    Click(2);
                }
            }

        };
        add(setting);
        add(title);
        add(leave);
        add(game);
        setting.addMouseListener(hoverEffect);
        leave.addMouseListener(hoverEffect);
        game.addMouseListener(hoverEffect);
    }

    private void adjustTextPanelSize() {
        //length of the text
        int xWordTitle = title.getText().length() * 20;
        int xWordSetting = setting.getText().length() * 20;
        int xWordleave = leave.getText().length() * 20;
        int xWordGame = game.getText().length() * 20;

        // height of the text
        int heightWord = (int) (getHeight() * 0.1);
        // y position of the text
        int marge = 20;
        int yWordGame = -heightWord - marge;
        int yWordSetting = (-heightWord - marge) * 2;
        int yWordleave = (-heightWord - marge) * 3;
        int xWord = (xWordTitle + xWordSetting + xWordleave + xWordGame) / 4; // for center

        int y = getHeight() / 4;
        int yGame = y - yWordGame;
        int ySetting = y - yWordSetting;
        int yleave = y - yWordleave;

        title.setBounds((getWidth() / 2) - xWord, y, xWordTitle, heightWord);
        game.setBounds(getWidth() / 2 - xWord, yGame, xWordGame, heightWord);
        ;
        setting.setBounds((getWidth() / 2) - xWord, ySetting, xWordSetting, heightWord);
        leave.setBounds((getWidth() / 2) - xWord, yleave, xWordleave, heightWord);
    }

    private void Click(int value) {
        if (onPauseClicked != null) {
            onPauseClicked.accept(value);
        }
    }

    public void setPauseClicked(Consumer<Integer> listener) {
        this.onPauseClicked = listener;
    }
}
