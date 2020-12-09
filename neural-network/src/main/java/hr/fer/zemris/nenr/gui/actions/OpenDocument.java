package hr.fer.zemris.nenr.gui.actions;

import hr.fer.zemris.nenr.gui.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class OpenDocument extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private final Component parent;
    private final Model model;

    public OpenDocument(String name, Component parent, Model model) {
        super(name);
        this.parent = parent;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open file");
        if (jfc.showOpenDialog(parent) != APPROVE_OPTION)
            return;

        Path openedFilePath = jfc.getSelectedFile().toPath();
        if (!Files.isReadable(openedFilePath)) {
            showMessageDialog(parent, "noReadPersmision", "error",
                    ERROR_MESSAGE);
            return;
        }

        try {
            model.loadDocument(Files.readAllLines(openedFilePath));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
