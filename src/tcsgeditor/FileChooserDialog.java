package tcsgeditor;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class FileChooserDialog extends javax.swing.JDialog {

  private boolean isSave;

  /** Creates new form FileChooserDialog */
  public FileChooserDialog( java.awt.Frame parent, boolean modal, boolean isSave, File file ) {
    super( parent, modal );
    this.isSave = isSave;
    initComponents();
    if ( file != null ) {
      jFileChooser1.setSelectedFile( file );
    }
    jFileChooser1.setDialogType( isSave ? JFileChooser.SAVE_DIALOG : JFileChooser.OPEN_DIALOG );
    jFileChooser1.setFileFilter( new ExtensionFileFilter( "TomClaw Scalable Graphics Digest (*.dig)", new String[]{ "dig" } ) );
    setLocationRelativeTo( parent );
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings( "unchecked" )
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jFileChooser1 = new javax.swing.JFileChooser();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Выбор файла");

    jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jFileChooser1ActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
    if ( !evt.getActionCommand().equals( JFileChooser.CANCEL_SELECTION ) ) {
      if ( isSave ) {
        TCSGEditor.mainFrame.saveToFile( jFileChooser1.getSelectedFile() );
      } else {
        TCSGEditor.mainFrame.openFromFile( jFileChooser1.getSelectedFile() );
      }
    }
    dispose();
  }//GEN-LAST:event_jFileChooser1ActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JFileChooser jFileChooser1;
  // End of variables declaration//GEN-END:variables
}

class ExtensionFileFilter extends FileFilter {

  String description;
  String extensions[];

  public ExtensionFileFilter( String description, String extension ) {
    this( description, new String[]{ extension } );
  }

  public ExtensionFileFilter( String description, String extensions[] ) {
    if ( description == null ) {
      this.description = extensions[0];
    } else {
      this.description = description;
    }
    this.extensions = ( String[] ) extensions.clone();
    toLower( this.extensions );
  }

  private void toLower( String array[] ) {
    for ( int i = 0, n = array.length; i < n; i++ ) {
      array[i] = array[i].toLowerCase();
    }
  }

  public String getDescription() {
    return description;
  }

  public boolean accept( File file ) {
    if ( file.isDirectory() ) {
      return true;
    } else {
      String path = file.getAbsolutePath().toLowerCase();
      for ( int i = 0, n = extensions.length; i < n; i++ ) {
        String extension = extensions[i];
        if ( ( path.endsWith( extension ) && ( path.charAt( path.length() - extension.length() - 1 ) ) == '.' ) ) {
          return true;
        }
      }
    }
    return false;
  }
}
