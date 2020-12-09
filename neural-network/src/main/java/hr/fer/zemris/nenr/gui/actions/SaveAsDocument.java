package hr.fer.zemris.nenr.gui.actions;

import hr.fer.zemris.nenr.gui.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.*;

public class SaveAsDocument extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private final Component parent;
    private final Model model;

    public SaveAsDocument(String name, Component parent, Model model) {
        super(name);
        this.parent = parent;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save file");
        if (jfc.showSaveDialog(parent) != APPROVE_OPTION) {
            showMessageDialog(parent, "nothingsaved", "warning", INFORMATION_MESSAGE);
            return;
        }
        try {
            Files.write(jfc.getSelectedFile().toPath(), model.exportDocument());
        } catch (IllegalStateException | IOException e2) {
            showMessageDialog(parent, "nothingSaved", "error", ERROR_MESSAGE);
        }
    }
}
