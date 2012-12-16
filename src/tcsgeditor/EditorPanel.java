package tcsgeditor;

import com.tomclaw.tcsg.Fragment;
import com.tomclaw.tcsg.Gabarite;
import com.tomclaw.tcsg.Primitive;
import com.tomclaw.tcsg.ScaleGraphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/** 
 *
 * @author solkin
 */
public class EditorPanel extends javax.swing.JPanel {

  private Fragment fragment;
  private Color dark = Color.darkGray;
  private Color light = Color.gray;
  private int startX, startY;
  private int activX, activY;
  private Primitive primitive;
  private Gabarite selected;

  /** Creates new form EditorPanel */
  public EditorPanel( int templateWidth, int templateHeight ) {
    initComponents();

    fragment = new Fragment( templateWidth, templateHeight, 0, 0,
            templateWidth * ScaleGraphics.scaleFactor,
            templateHeight * ScaleGraphics.scaleFactor );
    Primitive[] items1 = new Primitive[ 0 ];

    /*items1[0] = new Gradient( 0, 0, 9, 5, actGradFrom, actGradMidd, true, true, fragment );
     items1[1] = new Gradient( 0, 6, 9, 3, actGradAftr, actGradFinl, true, true, fragment );
     items1[2] = new Rect( 0, 0, 10, 10, actBotBorder, false, false, fragment );
     items1[3] = new Line( 0, 0, 10, 0, actOnlTopBorder, false, fragment );
     items1[4] = new Line( 1, -1, -1, -1, actInOnlBotBorder, false, fragment );*/
    /*items1[0] = new Point( -3, -1, 0x000000, false, fragment );
     items1[1] = new Rect( 1, 1, -1, -1, 0x000000, false, false, fragment );
     items1[2] = new Line( 0, 0, 5, 5, 0xff0000, false, fragment );*/
    //items1[0] = new Gradient( 1,1,29, 29, 0x0000ff, 0x00ffff , true, false, fragment );

    fragment.setPrimitives( items1 );

    startX = -1;
    startY = -1;

    updateDrawSize();
  }

  /** Creates new form EditorPanel */
  public EditorPanel( Fragment fragment ) {
    initComponents();

    this.fragment = fragment;

    startX = -1;
    startY = -1;

    updateDrawSize();
  }

  @Override
  public void paint( Graphics g ) {
    /** Locating **/
    fragment.setDrawLocation( ( getWidth() - fragment.getDrawWidth() ) / 2,
            ( getHeight() - fragment.getDrawHeight() ) / 2 );
    g.setColor( Color.black );
    g.fillRect( fragment.getDrawX(), fragment.getDrawY(), fragment.getDrawWidth(), fragment.getDrawHeight() );
    /** Drawing net **/
    for ( int y = 0; y < fragment.getDrawHeight(); y += ScaleGraphics.scaleFactor ) {
      for ( int x = 0; x < fragment.getDrawWidth(); x += ScaleGraphics.scaleFactor ) {
        g.setColor( ( ( ( y / ScaleGraphics.scaleFactor ) % 2 + x / ScaleGraphics.scaleFactor ) % 2 == 0 ? dark : light ) );
        g.fillRect( fragment.getDrawX() + x, fragment.getDrawY() + y, ScaleGraphics.scaleFactor, ScaleGraphics.scaleFactor );
      }
    }
    /** Fixing time **/
    long time = System.currentTimeMillis();
    /** Painting **/
    fragment.paint( g );
    if ( primitive != null ) {
      primitive.paint( g );
    }
    if ( selected != null ) {
    }
    /** Showing time elapsed **/
    System.out.println( "Time: " + ( System.currentTimeMillis() - time ) );
  }

  public final void setTemplateSize( int width, int height ) {
    fragment.setTemplateSize( width, height );
    fragment.setDrawSize( width * ScaleGraphics.scaleFactor, height * ScaleGraphics.scaleFactor );
    updateDrawSize();
  }

  public Dimension getTemplateSize() {
    return new Dimension( fragment.getTemplateWidth(), fragment.getTemplateHeight() );
  }

  public final void updateDrawSize() {
    fragment.setDrawSize( fragment.getTemplateWidth() * ScaleGraphics.scaleFactor,
            fragment.getTemplateHeight() * ScaleGraphics.scaleFactor );
    setPreferredSize( new Dimension( fragment.getDrawWidth(), fragment.getDrawHeight() ) );
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings( "unchecked" )
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    addMouseWheelListener(new java.awt.event.MouseWheelListener() {
      public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
        formMouseWheelMoved(evt);
      }
    });
    addMouseListener(new java.awt.event.MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent evt) {
        formMousePressed(evt);
      }
      public void mouseReleased(java.awt.event.MouseEvent evt) {
        formMouseReleased(evt);
      }
    });
    addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseDragged(java.awt.event.MouseEvent evt) {
        formMouseDragged(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 300, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents

  private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
    if ( evt.getX() >= fragment.getDrawX() && evt.getX() < fragment.getDrawX() + fragment.getDrawWidth()
            && evt.getY() >= fragment.getDrawY() && evt.getY() < fragment.getDrawY() + fragment.getDrawHeight() ) {
      startX = ( evt.getX() - fragment.getDrawX() ) / ScaleGraphics.scaleFactor;
      startY = ( evt.getY() - fragment.getDrawY() ) / ScaleGraphics.scaleFactor;
      primitive = TCSGEditor.mainFrame.getActivePrimitive();
      if ( primitive != null ) {
        /** Drawing **/
        primitive.setFigure( fragment );
        primitive.setLocation( startX, startY );
      } else {
        /** Selection **/
        if ( fragment.getPrimitivesCount() > 0 ) {
          Primitive[] items = fragment.getPrimitives();
          for ( int c = items.length - 1; c >= 0; c-- ) {
            Gabarite gabarite = items[c].getGabarite();
            System.out.println( gabarite.x1 + "<=" + startX + "&&" + gabarite.y1 + "<=" + startY
                    + "&&" + gabarite.x2 + ">" + startX + "&&" + gabarite.y2 + ">" + startY );
            if ( gabarite.x1 <= startX && gabarite.y1 <= startY
                    && gabarite.x2 > startX && gabarite.y2 > startY ) {
              System.out.println( "Item selected: " + c );
              setSelectedPrimitive( items[c] );
              return;
            }
          }
          selected = null;
          repaint();
        }
      }
      System.out.println( "Mouse Pressed (Locked)" );
    } else {
      startX = -1;
      startY = -1;
      System.out.println( "Mouse Pressed (Free)" );
    }
  }//GEN-LAST:event_formMousePressed

  private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
    System.out.println( "Mouse Released" );
    if ( primitive != null ) {
      Primitive[] items = new Primitive[ fragment.getPrimitivesCount() + 1 ];
      System.arraycopy( fragment.getPrimitives(), 0, items, 0, fragment.getPrimitivesCount() );
      items[items.length - 1] = primitive;
      fragment.setPrimitives( items );
      primitive = null;
    }
  }//GEN-LAST:event_formMouseReleased

  private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
    if ( startX >= 0 && startY >= 0 ) {
      if ( evt.getX() >= fragment.getDrawX() && evt.getX() < fragment.getDrawX() + fragment.getDrawWidth()
              && evt.getY() >= fragment.getDrawY() && evt.getY() < fragment.getDrawY() + fragment.getDrawHeight() ) {
        System.out.println( "Mouse Dragged" );
        activX = ( evt.getX() - fragment.getDrawX() ) / ScaleGraphics.scaleFactor;
        activY = ( evt.getY() - fragment.getDrawY() ) / ScaleGraphics.scaleFactor;
        if ( primitive != null ) {
          primitive.setSecLocation( activX, activY );
        }
      } else {
        System.out.println( "Mouse Drag Stopped" );
        startX = -1;
        startY = -1;
        primitive = null;
      }
      repaint();
    } else {
      System.out.println( "Mouse Dragged (Free)" );
    }
  }//GEN-LAST:event_formMouseDragged

  private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
    System.out.println( "Mouse Wheel: getPreciseWheelRotation: " + evt.getPreciseWheelRotation() );
    TCSGEditor.mainFrame.updateScaleFactor( ( int ) ( ScaleGraphics.scaleFactor 
            - evt.getPreciseWheelRotation() ) );
  }//GEN-LAST:event_formMouseWheelMoved

  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
  public Fragment getFigure() {
    return fragment;
  }

  private void setSelectedPrimitive( Primitive primitive ) {
    selected = primitive.getGabarite();
    TCSGEditor.mainFrame.setSelectedPrimitive( primitive );
    repaint();
  }
}
