package Front_end;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class ScoreBoard extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<String> names;
    private ArrayList<Integer> scores;

    public ScoreBoard(ArrayList<Integer> scores, ArrayList<String> names) {
        // Vérifie la cohérence des listes
        if (names.size() != scores.size()) {
            throw new IllegalArgumentException("names et scores doivent avoir la même taille");
        }
        this.scores = scores;
        this.names = names;
        setOpaque(false);

        setBounds(0, 0, 400, 300); // taille du tableau
        createTable();

        // 3) Création et configuration du JTable
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(30); // ajuste largeur colonne score

        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        // Centrage
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        // Apparence
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(new Color(0, 0, 0, 0));
        table.setOpaque(false);
        table.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBorder(null);
        table.setFont(new Font("SansSerif", Font.PLAIN, 24));
        table.setForeground(Color.WHITE);

        table.setBorder(BorderFactory.createEmptyBorder());
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(null);
        scrollPane.getViewport().setBorder(null);

        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JTableHeader header = getJTableHeader();
        table.setTableHeader(header);

        repaint();
    }

    private JTableHeader getJTableHeader() {
        JTableHeader header = new JTableHeader(table.getColumnModel()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Rend le fond 100 % transparent
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.Clear);  // empêche la peinture du fond
                super.paintComponent(g2);
                g2.setComposite(AlphaComposite.SrcOver); // remet à la normale
            }
        };

        header.setOpaque(false);
        header.setBackground(new Color(0, 0, 0, 0));
        header.setForeground(Color.WHITE);

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setOpaque(false);
                label.setForeground(Color.WHITE);
                label.setBackground(new Color(0, 0, 0, 0));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        return header;
    }

    private void createTable() {
        String[] columnNames = {"Players", "Scores"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // rend le tableau non éditable
            }
        };

        for (int i = 0; i < names.size(); i++) {
            Object[] row = {names.get(i), scores.get(i)};
            model.addRow(row);
        }
    }

    /**
     * Pour mettre à jour à tout moment :
     */
    public void updateScores(ArrayList<Integer> newScores, ArrayList<String> newNames) {
        if (newNames.size() != newScores.size()) {
            throw new IllegalArgumentException("Tailles incohérentes");
        }
        model.setRowCount(0); // vide le tableau
        for (int i = 0; i < newNames.size(); i++) {
            model.addRow(new Object[]{newNames.get(i), newScores.get(i)});
        }
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

    public void update(ArrayList<String> names, ArrayList<Integer> scores) {
        if (names.size() != scores.size()) {
            throw new IllegalArgumentException("names et scores doivent avoir la même taille");
        }
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            indices.add(i);
        }

        indices.sort((i1, i2) -> Integer.compare(scores.get(i1), scores.get(i2))); // sort

        model.setRowCount(0);
        this.names = new ArrayList<>();
        this.scores = new ArrayList<>();
        for (int i : indices) {
            String name = names.get(i);
            int score = scores.get(i);
            this.names.add(name);
            this.scores.add(score);
            model.addRow(new Object[]{name, score});
        }
    }
}
