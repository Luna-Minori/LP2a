package Front_end;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Menu {
	private InitGame initGame;
    private BiConsumer<ArrayList<String>,Integer> initGameEnd;
	private ArrayList<String> names;
	private int bot;
	
    public Menu() {
        JFrame frame = new JFrame("Lama Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Menu Principal");
        frame.setSize(500, 500);

        ImageIcon bgIcon = new ImageIcon("C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        BackgroundPanel background = new BackgroundPanel(bgImage);
        background.setLayout(null);
        
        frame.setContentPane(background);

        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(0, 0, 0, 200)); // Semi-transparent background
        panelMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 0, FlowLayout.CENTER));
        panelMenu.setOpaque(true);
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // top, left, bottom, right

        JButton jouerButton = new JButton("Jouer");
        configureButton(jouerButton);
        panelMenu.add(jouerButton);
        
        JButton settingsButton = new JButton("Settings");
        configureButton(settingsButton);
        panelMenu.add(settingsButton);

        JButton quittezButton = new JButton("Quittez");
        configureButton(quittezButton);
        panelMenu.add(quittezButton);

        quittezButton.addActionListener(e -> System.exit(0));        	
        jouerButton.addActionListener(a -> {
            jouerButton.setEnabled(false);
        	frame.removeAll();
            frame.dispose();
            initGame = new InitGame();
            initGame.setOnValidated((playerNames,nbBots) -> {
            	if(initGameEnd != null) {
            		initGameEnd.accept(playerNames, nbBots);
            		initGameEnd = null;
            	}
            });
        });
        
        settingsButton.addActionListener(e -> {
        	Settings settings = new Settings();
        });
        
        MouseAdapter hoverEffect = new MouseAdapter() {
        	@Override
        	public void mouseEntered(MouseEvent e) {
        		JButton button = (JButton) e.getSource();
        		button.toString();
        		button.setBackground(new Color(0, 0, 0, 50)); // Slightly transparent on hover
        		frame.repaint();
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		JButton button = (JButton) e.getSource();
        		button.setBackground(new Color(0, 0, 0, 0)); // Restore original semi-transparent background
        		frame.repaint();
        	}
        };
        
        jouerButton.addMouseListener(hoverEffect);
        settingsButton.addMouseListener(hoverEffect);
        quittezButton.addMouseListener(hoverEffect);
        
        background.add(panelMenu);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int x = (int) (frame.getWidth() * 0.1);
                int y = (int) (frame.getWidth() * 0);
                int largeur = (int) (frame.getWidth() * 0.3);
                int hauteur = (int) (frame.getWidth());

                panelMenu.setBounds(x, y, largeur, hauteur);

                for (Component component : panelMenu.getComponents()) {
                    if (component instanceof JButton) {
                        component.setFont(new Font("Arial", Font.PLAIN, (int) (hauteur * 0.05)));
                        component.setPreferredSize(new Dimension(largeur, (int) (hauteur * 0.2)));
                    }
                }

                panelMenu.revalidate();
                panelMenu.repaint();
            }
        });
	
        int x = (int) (frame.getWidth() * 0.1);
        int y = (int) (frame.getHeight() * 0);
        int largeur = (int) (frame.getWidth() * 0.3);
        int hauteur = (int) (frame.getHeight());

        panelMenu.setBounds(x, y, largeur, hauteur);

        for (Component component : panelMenu.getComponents()) {
            if (component instanceof JButton) {
                component.setFont(new Font("Arial", Font.PLAIN, (int) (hauteur * 0.05)));
                component.setPreferredSize(new Dimension(largeur, (int) (hauteur * 0.2)));
            }
        }
        panelMenu.revalidate();
        panelMenu.repaint();
        frame.setVisible(true);
    }

    private void configureButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    public void initGameReady(BiConsumer<ArrayList<String>,Integer>callback) {
        this.initGameEnd = callback;
    }

}
