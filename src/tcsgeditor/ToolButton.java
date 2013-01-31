package tcsgeditor;

import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public abstract class ToolButton extends JButton {

  public ToolButton( String icon, String title ) {
    setIcon( new javax.swing.ImageIcon( Class.class.getResource( icon ) ) );
    setText( title );
    this.setBorderPainted( false );
    this.setHorizontalAlignment( SwingConstants.LEFT );

    addActionListener( new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed( java.awt.event.ActionEvent evt ) {
        ToolButton.this.actionPerformed();
      }
    } );
  }

  public abstract void actionPerformed();
}
