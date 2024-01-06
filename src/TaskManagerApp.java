import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskManagerApp {
    private DefaultListModel<String> taskListModel;
    private DefaultTableModel taskTableModel;
    private JTree categoryTree;

    public TaskManagerApp() {
        taskListModel = new DefaultListModel<>();
        taskTableModel = new DefaultTableModel();
        taskTableModel.addColumn("Catégorie");
        taskTableModel.addColumn("Nom de la tâche");
        taskTableModel.addColumn("Description");
        taskTableModel.addColumn("Date limite");

        categoryTree = createCategoryTree();
        JList<String> taskList = new JList<>(taskListModel);
        JTable taskDetailsTable = new JTable(taskTableModel);
        JButton addButton = new JButton("Ajouter Tâche");
        JButton deleteButton = new JButton("Supprimer Tâche");
        JButton completeButton = new JButton("Marquer comme Terminée");
        JButton editButton = new JButton("Éditer Tâche");

        JFrame frame = new JFrame("Gestion de Tâches");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JScrollPane(categoryTree), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(new JScrollPane(taskDetailsTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(editButton);

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) categoryTree.getLastSelectedPathComponent();
                if (selectedNode == null || selectedNode.isRoot()) {
                    JOptionPane.showMessageDialog(null, "Sélectionnez une catégorie avant d'ajouter une tâche.");
                    return;
                }

                String category = selectedNode.toString();

                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(4, 2));
                JTextField taskNameField = new JTextField();
                JTextField descriptionField = new JTextField();
                JTextField deadlineField = new JTextField();

                panel.add(new JLabel("Nom de la tâche:"));
                panel.add(taskNameField);
                panel.add(new JLabel("Description:"));
                panel.add(descriptionField);
                panel.add(new JLabel("Date limite:"));
                panel.add(deadlineField);

                int result = JOptionPane.showConfirmDialog(null, panel, "Entrez les détails de la tâche", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String taskName = taskNameField.getText();
                    String description = descriptionField.getText();
                    String deadline = deadlineField.getText();

                    if (taskName.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Le nom de la tâche ne peut pas être vide.");
                        return;
                    }

                    String taskDetails = category + ": " + taskName + " (Description: " + description + ", Date limite: " + deadline + ")";
                    taskListModel.addElement(taskDetails);
                    taskTableModel.addRow(new Object[]{category, taskName, description, deadline});
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                    taskTableModel.removeRow(selectedIndex);
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String editedTaskDetails = JOptionPane.showInputDialog(null, "Modifier la tâche:", taskListModel.get(selectedIndex));
                    if (editedTaskDetails != null && !editedTaskDetails.isEmpty()) {
                        taskListModel.setElementAt(editedTaskDetails, selectedIndex);
                        taskTableModel.setValueAt(editedTaskDetails, selectedIndex, 1);
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private JTree createCategoryTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Catégories");
        DefaultMutableTreeNode personnelNode = new DefaultMutableTreeNode("Personnel");
        DefaultMutableTreeNode travailNode = new DefaultMutableTreeNode("Travail");
        DefaultMutableTreeNode projetNode = new DefaultMutableTreeNode("Projet X");

        rootNode.add(personnelNode);
        rootNode.add(travailNode);
        rootNode.add(projetNode);

        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        return new JTree(treeModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TaskManagerApp();
            }
        });
    }
}