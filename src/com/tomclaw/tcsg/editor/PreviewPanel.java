package com.tomclaw.tcsg.editor;

import com.tomclaw.tcsg.Fragment;

import java.awt.*;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 *
 * @author Solkin
 */
public class PreviewPanel extends javax.swing.JPanel {

    private Fragment fragment;
    private Color dark = Color.gray;
    private Color light = Color.lightGray;
    /**
     * Colors
     */
    public static int unactOnlTopBorder = 0xBDBABD;
    public static int unactBotBorder = 0xADAAAD;
    public static int unactInTopBorder = 0xE7E3E7;
    public static int unactInOnlBotBorder = 0xD6D3D6;
    public static int unactGradFrom = 0xD6D3D6;
    public static int unactGradTo = 0xADAEAD;
    public static int foreColor = 0x424142;
    public static int foreShadowColor = 0xDEDFDE;
    public static int actOnlTopBorder = 0xB5B2E7;
    public static int actBotBorder = 0xA59ECE;
    public static int actInOnlBotBorder = 0xC6BEF7;
    public static int actGradFrom = 0xDEDBF7;
    public static int actGradMidd = 0xB5AAEF;
    public static int actGradAftr = 0xA59EEF;
    public static int actGradFinl = 0xADA6EF;
    public static int actOuterLight = 0xBDC7FF;
    public static int actInnerLight = 0x8C9AFF;
    public static int prsdGradFrom = 0xB5AAEF;
    public static int prsdGradFinl = 0xDEDBF7;

    /**
     * Creates new form PreviewPanel
     */
    public PreviewPanel(Fragment fragment) {
        //this.fragment = fragment;
        initComponents();

        //setPreferredSize( new Dimension( 10, 10 ) );
        this.fragment = fragment;
    /*fragment = new Fragment( 5, 5, 0, 0, 240, 50 );
     this.fragment = fragment;
     Primitive[] items1 = new Primitive[ 5 ];

     items1[0] = new Gradient( 0, 0, 4, 2, actGradFrom, actGradMidd, true, true, true, fragment );
     items1[1] = new Gradient( 0, 3, 4, 1, actGradAftr, actGradFinl, true, true, true, fragment );
     items1[2] = new Rect( 0, 0, 5, 5, actBotBorder, false, false, fragment );
     items1[3] = new Line( 0, 0, -1, 0, actOnlTopBorder, false, fragment );
     items1[4] = new Line( 1, -1, -1, -1, actInOnlBotBorder, false, fragment );

     fragment.setPrimitives( items1 );*/

        updateDrawSize();
    }

    @Override
    public void paint(Graphics g) {
        /** Drawing back **/
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        /** Locating **/
        fragment.setDrawLocation(0, 0);
        /** Fixing time **/
        long time = System.currentTimeMillis();
        /** Painting **/
        fragment.paint(g);
        /** Showing time elapsed **/
        System.out.println("Time: " + (System.currentTimeMillis() - time));
    }

    public final void updateDrawSize() {
        fragment.setDrawSize(getWidth(), getHeight());
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <com.tomclaw.tcsg.editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 398, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 298, Short.MAX_VALUE)
        );
    }// </com.tomclaw.tcsg.editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        updateDrawSize();
    }//GEN-LAST:event_formComponentResized
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
