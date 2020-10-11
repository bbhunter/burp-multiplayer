/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package burp.gui;

import burp.HTTPMessageEditor;
import burp.IBurpExtenderCallbacks;
import burp.Multiplayer;
import burp.MultiplayerRequestResponse;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


/**
 *
 * @author moloch
 */
public class InScopePane extends javax.swing.JPanel {

    private Multiplayer multiplayer;
    private IBurpExtenderCallbacks callbacks;
    private ListSelectionListener rowSelectionListener;
    
    /**
     * Creates new form InScopePane
     */
    public InScopePane(Multiplayer multiplayer, IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.multiplayer = multiplayer;
        initComponents();
        
        // Hide ID column
        inScopeTable.getColumnModel().getColumn(0).setMinWidth(0);
        inScopeTable.getColumnModel().getColumn(0).setMaxWidth(0);

        // Highlight column
        int highlightColumnIndex = multiplayer.history.columns.indexOf(multiplayer.history.Highlight);
        TableColumn highlightColumn = inScopeTable.getColumnModel().getColumn(highlightColumnIndex);
        JComboBox highlightComboBox = new JComboBox();
        for (String colorName : multiplayer.history.highlights) {
            highlightComboBox.addItem(colorName);
        }
        highlightColumn.setCellEditor(new DefaultCellEditor(highlightComboBox));

        // Assessment column
        int assessmentColumnIndex = multiplayer.history.columns.indexOf(multiplayer.history.Assessment);
        TableColumn assessmentColumn = inScopeTable.getColumnModel().getColumn(assessmentColumnIndex);
        JComboBox assessmentStateComboBox = new JComboBox();
        for (String state : multiplayer.history.assessmentStates) {
            assessmentStateComboBox.addItem(state);
        }
        assessmentColumn.setCellEditor(new DefaultCellEditor(assessmentStateComboBox));
        
        // Table listener
        this.multiplayer.history.addTableModelListener(inScopeTable);
        
        // Row Selection Listener
        rowSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    String reqRespId = (String) inScopeTable.getValueAt(inScopeTable.getSelectedRow(), 0);
                    displayMessageEditorFor(reqRespId);
                }
            }
        };
        inScopeTable.getSelectionModel().addListSelectionListener(rowSelectionListener);
        
        this.repaint();
        this.revalidate();
    }
    
    public void displayMessageEditorFor(String reqRespId) {
        callbacks.printOutput(String.format("Selected: %s", reqRespId));
        MultiplayerRequestResponse reqResp = multiplayer.history.getById(reqRespId);
        
        // Save active tab
        int selectedTabIndex = bottomTabbedPane.getSelectedIndex();
        if (selectedTabIndex == -1) {
            selectedTabIndex = 0;
        }
        
        bottomTabbedPane.removeAll();
        
        HTTPMessageEditor editor = new HTTPMessageEditor(reqResp, callbacks);
        bottomTabbedPane.addTab("Request", editor.getRequestEditor().getComponent());
        bottomTabbedPane.addTab("Response", editor.getResponseEditor().getComponent());
        bottomTabbedPane.setSelectedIndex(selectedTabIndex);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        parentSplitPane = new javax.swing.JSplitPane();
        inScopeTablePane = new javax.swing.JScrollPane();
        inScopeTable = new javax.swing.JTable(this.multiplayer.history) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                JComponent jComponent = (JComponent) component;
                String id = (String) this.getValueAt(row, 0);
                Color backgroundColor = multiplayer.history.getColorForId(id);
                callbacks.printOutput(String.format("Row = %d, Column = %d (%s) -> %s", row, column, id, backgroundColor));
                if(!component.getBackground().equals(getSelectionBackground())) {
                    component.setBackground(backgroundColor);
                }
                return component;
            }
        };
        bottomTabbedPane = new javax.swing.JTabbedPane();
        jButton1 = new javax.swing.JButton();

        parentSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        inScopeTable.setColumnSelectionAllowed(true);
        inScopeTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        inScopeTable.setRowSelectionAllowed(true);
        inScopeTable.setColumnSelectionAllowed(false);
        inScopeTable.setAutoCreateRowSorter(true);
        inScopeTablePane.setViewportView(inScopeTable);
        inScopeTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        parentSplitPane.setTopComponent(inScopeTablePane);
        parentSplitPane.setRightComponent(bottomTabbedPane);

        jButton1.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(parentSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(parentSplitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane bottomTabbedPane;
    private javax.swing.JTable inScopeTable;
    private javax.swing.JScrollPane inScopeTablePane;
    private javax.swing.JButton jButton1;
    private javax.swing.JSplitPane parentSplitPane;
    // End of variables declaration//GEN-END:variables


}
