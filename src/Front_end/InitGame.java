package Front_end;

import javax.swing.*;

import java.util.function.BiConsumer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class InitGame extends JFrame {

    private JPanel inputPanel;
    private JButton validateButton;
    private JTextField playerCountField;
    private JTextField botCountField;
    private ArrayList<JTextField> nameFields = new ArrayList<>();
    private ArrayList<String> playerNames = new ArrayList<>();
    private BiConsumer<ArrayList<String>,Integer> onValidated;
    
    public InitGame() {
        setTitle("Initialisation de la partie");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centrer

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        JLabel players = new JLabel("Nombre de joueurs :");
        playerCountField = new JTextField();
        playerCountField.setMaximumSize(new Dimension(200, 30));
        
        JLabel bots = new JLabel("Nombre de bot :");
        botCountField = new JTextField();
        botCountField.setMaximumSize(new Dimension(200, 30));

        JButton generateFieldsButton = new JButton("Créer les champs");
        generateFieldsButton.addActionListener(e -> createPlayerFields());

        inputPanel.add(players);
        inputPanel.add(playerCountField);
        inputPanel.add(bots);
        inputPanel.add(botCountField);
        inputPanel.add(generateFieldsButton);

        validateButton = new JButton("Valider");
        validateButton.addActionListener(e -> handleValidation());

        add(inputPanel, BorderLayout.CENTER);
        add(validateButton, BorderLayout.SOUTH);

        nameFields = new ArrayList<>();

        setVisible(true);
    }

    private void createPlayerFields() {
    	int nbPlayers = Integer.parseInt(playerCountField.getText()) + Integer.parseInt(botCountField.getText());
    	if(nbPlayers <= 6 && nbPlayers >= 2 ) {	    	
	        // Supprimer les anciens champs
	        nameFields.clear();
	        Component[] components = inputPanel.getComponents();
	        for (int i = components.length - 1; i >= 3; i--) {
	            inputPanel.remove(components[i]);
	        }
	
	        try {
	            int count = Integer.parseInt(playerCountField.getText());
	            for (int i = 1; i <= count; i++) {
	                JTextField nameField = new JTextField("Joueur " + i);
	                nameField.setMaximumSize(new Dimension(200, 30));
	                nameFields.add(nameField);
	                inputPanel.add(new JLabel("Nom du joueur " + i + " :"));
	                inputPanel.add(nameField);
	            }
	            inputPanel.revalidate();
	            inputPanel.repaint();
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
	        }
    	}
    	else {
    		JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide ( bot + player = max 6 )");
    	}
    }

    private void handleValidation() {
    	playerNames.clear(); // anti-spam for strange user :)
    	for (JTextField field : nameFields) {
    	    String name = field.getText().trim();
    	    if (!name.isEmpty()) {
    	        playerNames.add(name);
    	    }
    	}
    	System.out.println("Noms des joueurs : " + playerNames);
    	
    	if(onValidated != null) {
    		onValidated.accept(playerNames, getBot());
    		onValidated = null;
    	}
    	this.dispose();
    }
    
    public ArrayList<String> getNamesPlayers(){
    	System.out.println("Noms des joueurs get : " + playerNames); 
    	return playerNames;
    }
    
    public int getBot(){
    	return Integer.parseInt(botCountField.getText());
    }
    
    public void setOnValidated(BiConsumer<ArrayList<String>,Integer> callback) {
        onValidated = callback;
    }
}
