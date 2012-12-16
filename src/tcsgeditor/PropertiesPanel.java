package tcsgeditor;

import com.tomclaw.tcsg.Primitive;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author solkin
 */
public class PropertiesPanel extends javax.swing.JPanel {

  private final Primitive primitive;
  private final Object[][] fields;
  private final ArrayList<Component> panels;

  /** Creates new form PropertiesPanel */
  public PropertiesPanel( final EditorPanel editorPanel, final Primitive primitive ) {
    initComponents();
    this.primitive = primitive;
    this.fields = primitive.getFields();
    /*DefaultTableModel model = new DefaultTableModel() {
     Class[] types = new Class[]{
     java.lang.String.class, java.lang.Object.class
     };
     boolean[] canEdit = new boolean[]{
     false, false
     };

     @Override
     public Class getColumnClass( int columnIndex ) {
     return types[columnIndex];
     }

     @Override
     public boolean isCellEditable( int rowIndex, int columnIndex ) {
     return canEdit[columnIndex];
     }
     };
     model.setDataVector( fields, new String[]{ "Поле", "Значение" } );
     jTable1.setDefaultRenderer( Object.class, new PropertiesTableRenderer() );
     jTable1.setModel( model );*/
    panels = new ArrayList();
    objectsPanel.setLayout( new VerticalFlowLayout() );
    for ( int c = 0; c < fields.length; c++ ) {
      panels.add( cast( fields[c] ) );
      objectsPanel.add( panels.get( c ) );
    }
    JButton jRemoveButton = new JButton("Удалить");
    jRemoveButton.addActionListener( new ActionListener(){

      @Override
      public void actionPerformed( ActionEvent ae ) {
        editorPanel.removePrimitive( primitive );
        TCSGEditor.mainFrame.setSelectedPrimitive( editorPanel, null);
        editorPanel.repaint();
      }
    } );
    objectsPanel.add( jRemoveButton );
    JButton jUpButton = new JButton("Поднять");
    jUpButton.addActionListener( new ActionListener(){

      @Override
      public void actionPerformed( ActionEvent ae ) {
        editorPanel.zOrderPrimitiveUp( primitive );
        editorPanel.repaint();
      }
    } );
    objectsPanel.add( jUpButton );
    JButton jDownButton = new JButton("Опустить");
    jDownButton.addActionListener( new ActionListener(){

      @Override
      public void actionPerformed( ActionEvent ae ) {
        editorPanel.zOrderPrimitiveDown( primitive );
        editorPanel.repaint();
      }
    } );
    objectsPanel.add( jDownButton );
    
    updateUI();
  }

  public final Component cast( Object object[] ) {
    String title = ( String ) object[0];
    Object value = object[1];
    JPanel panel = new JPanel();
    panel.setLayout( new BorderLayout() );
    panel.add( new JLabel( title.concat( ": " ) ), BorderLayout.WEST );
    Component component = null;
    if ( value instanceof Integer ) {
      component = new JSpinner();
      ( ( JSpinner ) component ).addChangeListener( new ChangeListener() {
        @Override
        public void stateChanged( ChangeEvent ce ) {
          updateProperties();
        }
      } );
      ( ( JSpinner ) component ).setValue( value );
    } else if ( value instanceof Color ) {
      component = new ColorBox();
      ( ( ColorBox ) component ).setRenderer( new ColorBoxRenderer() );
      ( ( ColorBox ) component ).addItemListener( new ItemListener() {
        @Override
        public void itemStateChanged( ItemEvent ie ) {
          updateProperties();
        }
      } );
      ( ( ColorBox ) component ).setSelectedItem( value );
    } else if ( value instanceof Boolean ) {
      component = new JCheckBox( "", ( ( Boolean ) value ).booleanValue() );
      ( ( JCheckBox ) component ).addItemListener( new ItemListener() {
        @Override
        public void itemStateChanged( ItemEvent ie ) {
          updateProperties();
        }
      } );
    } else if ( value instanceof Selector ) {
      component = new SelectorBox( ( ( Selector ) value ) );
      ( ( SelectorBox ) component ).addItemListener( new ItemListener() {
        @Override
        public void itemStateChanged( ItemEvent ie ) {
          updateProperties();
        }
      } );
    }
    if ( component != null ) {
      panel.add( component, BorderLayout.CENTER );
    }
    return panel;
  }

  public Object[] uncast( JPanel panel ) {
    Object[] object = new Object[ 2 ];
    JLabel title = ( JLabel ) panel.getComponent( 0 );
    Component component = panel.getComponent( 1 );
    object[0] = title.getText();
    Object value = null;
    if ( component instanceof JSpinner ) {
      value = ( ( JSpinner ) component ).getValue();
    } else if ( component instanceof ColorBox ) {
      value = ( ( NamedColor ) ( ( ColorBox ) component ).getSelectedItem() );
      if ( value == null ) {
        value = Color.red;
      }
    } else if ( component instanceof JCheckBox ) {
      value = ( ( JCheckBox ) component ).isSelected();
    } else if ( component instanceof SelectorBox ) {
      value = ( ( ( SelectorBox ) component ).getSelector() );
    }
    if ( value != null ) {
      object[1] = value;
    }
    return object;
  }

  public Object[][] getProperties() {
    for ( int c = 0; c < panels.size(); c++ ) {
      fields[c] = uncast( ( JPanel ) panels.get( c ) );
    }
    return fields;
  }

  public void updateProperties() {
    primitive.setFields( getProperties() );
    TCSGEditor.mainFrame.getActiveEditorPanel().updateUI();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings( "unchecked" )
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    objectsPanel = new javax.swing.JPanel();

    setLayout(new java.awt.BorderLayout());

    objectsPanel.setLayout(null);
    add(objectsPanel, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel objectsPanel;
  // End of variables declaration//GEN-END:variables
}
