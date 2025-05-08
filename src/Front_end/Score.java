package Front_end;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Score extends JPanel {
    private JTable table;
    private DefaultTableModel model;

    public Score(String[] scores, ArrayList<String> names) {
        setLayout(new BorderLayout());

        // Entêtes des colonnes
        String[] columnNames = {"Player", "Score"};

        // Données initiales vides
        //model = new DefaultTableModel(columnNames, scores); // 0 = nombre de lignes initiales

        table = new JTable(model);
        table.setFillsViewportHeight(true); // occupe tout l'espace
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    // Ajouter ou mettre à jour une ligne
    public void updateScore(String joueur, int score) {
        boolean updated = false;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(joueur)) {
                model.setValueAt(score, i, 1); // met à jour score
                updated = true;
                break;
            }
        }
        if (!updated) {
            model.addRow(new Object[]{joueur, score});
        }
    }

    // Réinitialiser le tableau
    public void resetScores() {
        model.setRowCount(0); // efface toutes les lignes
    }
}
