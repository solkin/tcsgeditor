package com.tomclaw.tcsg.editor;

import javax.swing.*;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 *
 * @author Solkin
 */
public class TCSGEditor {

    public static MainFrame mainFrame;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.lipstikLF.LipstikLookAndFeel");
            } catch (Exception e) {
                System.out.println("Substance Graphite failed to initialize");
            }
            mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}
