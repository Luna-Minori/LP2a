package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {

    public Menu() {
        super();
        setTitle("Menu Principal");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        ImageIcon bgIcon = new ImageIcon("C:/Users/Luna/eclipse-workspace/Lp2a_Project/src/Front_end/Back.png");
        Image bgImage = bgIcon.getImage();
        BackgroundPanel background = new BackgroundPanel(bgImage);
        background.setLayout(null);
        
        setContentPane(background);

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
        jouerButton.addActionListener(e -> {
        	dispose();
            Game game = new Game();
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
        		repaint();
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		JButton button = (JButton) e.getSource();
        		button.setBackground(new Color(0, 0, 0, 0)); // Restore original semi-transparent background
        		repaint();
        	}
        };
        
        jouerButton.addMouseListener(hoverEffect);
        settingsButton.addMouseListener(hoverEffect);
        quittezButton.addMouseListener(hoverEffect);
        
        background.add(panelMenu);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int x = (int) (getWidth() * 0.1);
                int y = (int) (getWidth() * 0);
                int largeur = (int) (getWidth() * 0.3);
                int hauteur = (int) (getWidth());

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
	
        int x = (int) (getWidth() * 0.1);
        int y = (int) (getHeight() * 0);
        int largeur = (int) (getWidth() * 0.3);
        int hauteur = (int) (getHeight());

        panelMenu.setBounds(x, y, largeur, hauteur);

        for (Component component : panelMenu.getComponents()) {
        	System.out.println("aaaa");
            if (component instanceof JButton) {
                component.setFont(new Font("Arial", Font.PLAIN, (int) (hauteur * 0.05)));
                component.setPreferredSize(new Dimension(largeur, (int) (hauteur * 0.2)));
            }
        }

        panelMenu.revalidate();
        panelMenu.repaint();
        setVisible(true);
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
}
