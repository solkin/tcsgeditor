package tcsgeditor;

import javax.swing.JComboBox;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class ColorBox extends JComboBox {

  public ColorBox() {
    super( TCSGEditor.mainFrame.getNamedColors() );
  }
}
