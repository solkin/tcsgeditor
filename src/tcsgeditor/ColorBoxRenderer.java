/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcsgeditor;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author solkin
 */
public class ColorBoxRenderer implements ListCellRenderer {

  private JLabel label = new JLabel();

  @Override
  public Component getListCellRendererComponent( JList jlist, Object e, int i, boolean bln, boolean bln1 ) {
    NamedColor namedColor = ( NamedColor ) e;
    if ( namedColor != null ) {
      label.setOpaque( true );
      label.setText( namedColor.getName() );
      label.setBackground( namedColor );
      label.setForeground( new Color( 255 - namedColor.getRed(),
              255 - namedColor.getGreen(), 255 - namedColor.getBlue() ) );
    }
    return label;
  }
}
