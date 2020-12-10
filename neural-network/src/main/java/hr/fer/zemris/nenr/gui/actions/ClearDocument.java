package hr.fer.zemris.nenr.gui.actions;

import hr.fer.zemris.nenr.gui.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClearDocument extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private final Model model;

    public ClearDocument(String title, Model model) {
        super(title);
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        model.clear();
    }
}
