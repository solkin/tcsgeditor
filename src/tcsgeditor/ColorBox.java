package tcsgeditor;

import javax.swing.JComboBox;

/**
 *
 * @author solkin
 */
public class ColorBox extends JComboBox {

  public ColorBox() {
    super(TCSGEditor.mainFrame.getNamedColors());
  }
}
