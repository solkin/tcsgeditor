/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcsgeditor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

/**
 *
 * @author solkin
 */
public class SelectorBox extends JComboBox {

  private Selector selector;

  public SelectorBox( Selector selector ) {
    super( selector.getArray() );
    this.selector = selector;
    this.addItemListener( new ItemListener() {
      @Override
      public void itemStateChanged( ItemEvent ie ) {
        updateSelectedIndex();
      }
    } );
    this.setSelectedIndex( selector.getSelectedIndex() );
  }

  public Selector getSelector() {
    return selector;
  }

  private void updateSelectedIndex() {
    selector.setSelctedIndex( getSelectedIndex() );
  }
}
