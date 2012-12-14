package tcsgeditor;

import com.tomclaw.tcsg.Gradient;
import com.tomclaw.tcsg.Line;
import com.tomclaw.tcsg.Point;
import com.tomclaw.tcsg.Primitive;
import com.tomclaw.tcsg.Rect;
import com.tomclaw.tcsg.ScaleGraphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author solkin
 */
public class MainFrame extends javax.swing.JFrame {

  public EditorPanel ePanel;
  private Primitive activePrimitive;
  public String author;

  /** Creates new form MainFrame */
  public MainFrame() {
    initComponents();
    initToolPanel();
    setLocationRelativeTo( null );
    /** Sample figure **/
    createFigure( "figure1", 32, 32 );
    author = "TomClaw Software";
  }

  /**
   * Initializing toolbox
   */
  private void initToolPanel() {
    toolPanel.setLayout( new VerticalFlowLayout() );
    toolPanel.add( new ToolButton( "/res/icon-cursor.png", "Указатель" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( null );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-point.png", "Точка" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Point( 0, 0,
                TCSGEditor.mainFrame.getActiveColor(), false, null ) );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-line.png", "Линия" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Line( 0, 0, 0, 0,
                TCSGEditor.mainFrame.getActiveColor(), false, null ) );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-rect.png", "Прямоугольник" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Rect( 0, 0, 0, 0,
                TCSGEditor.mainFrame.getActiveColor(), false, false, null ) );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-fill-rect.png", "Залитый прямоугольник" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Rect( 0, 0, 0, 0,
                TCSGEditor.mainFrame.getActiveColor(), true, false, null ) );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-gradient.png", "Градиент (вер.)" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Gradient( 0, 0, 0, 0,
                TCSGEditor.mainFrame.getActiveColor(),
                TCSGEditor.mainFrame.getNearestColor(), false, true, false, null ) );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-fill-gradient.png", "Залитый градиент (вер.)" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Gradient( 0, 0, 0, 0,
                TCSGEditor.mainFrame.getActiveColor(),
                TCSGEditor.mainFrame.getNearestColor(), true, true, false, null ) );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-gradient-horiz.png", "Градиент (гор.)" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Gradient( 0, 0, 0, 0,
                TCSGEditor.mainFrame.getActiveColor(),
                TCSGEditor.mainFrame.getNearestColor(), false, false, false, null ) );
      }
    } );
    toolPanel.add( new ToolButton( "/res/icon-fill-gradient-horiz.png", "Залитый градиент (гор.)" ) {
      @Override
      public void actionPerformed() {
        TCSGEditor.mainFrame.setActivePrimitive( new Gradient( 0, 0, 0, 0,
                TCSGEditor.mainFrame.getActiveColor(),
                TCSGEditor.mainFrame.getNearestColor(), true, false, false, null ) );
      }
    } );
  }

  /**
   * Creates new figure and new tab
   * @param title
   * @param templateWidth
   * @param templateHeight 
   */
  public final void createFigure( String title, int templateWidth, int templateHeight ) {
    javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
    jTabbedPane1.addTab( title, jScrollPane );
    ePanel = new EditorPanel( templateWidth, templateHeight );
    jScrollPane.setViewportView( ePanel );
  }

  /**
   * Updates figure for specified params
   * @param index
   * @param title
   * @param templateWidth
   * @param templateHeight 
   */
  public final void updateFigure( int index, String title, int templateWidth, int templateHeight ) {
    EditorPanel editorPanel = getActiveEditorPanel();
    if ( editorPanel != null && index >= 0
            && index < jTabbedPane1.getTabCount() ) {
      jTabbedPane1.setTitleAt( index, title );
      editorPanel.setTemplateSize( templateWidth, templateHeight );
      updateScaleFactor( ScaleGraphics.scaleFactor );
    }
  }

  /**
   * Setting up primitive as selected one
   * @param activePrimitive 
   */
  public void setActivePrimitive( Primitive activePrimitive ) {
    this.activePrimitive = activePrimitive;
  }

  /**
   * Returns selected primitive for painting
   * @return Primitive
   */
  public Primitive getActivePrimitive() {
    return activePrimitive;
  }

  /**
   * Returns active (selected) color
   * @return int (RGB)
   */
  public int getActiveColor() {
    Color[] colors = getActiveColorObjects();
    if ( colors != null ) {
      return colors[0].getRGB();
    }
    return Color.red.getRGB();
  }

  /**
   * Returns darker or brighter colorfrom selected
   * @return int (RGB)
   */
  public int getNearestColor() {
    Color[] colors = getActiveColorObjects();
    Color color;
    if ( colors != null ) {
      if ( colors.length > 1 ) {
        return colors[1].getRGB();
      } else {
        color = colors[0];
      }
    } else {
      color = Color.red;
    }
    if ( color.brighter().getRGB() == color.getRGB() ) {
      return color.darker().getRGB();
    } else {
      return color.brighter().getRGB();
    }
  }

  /**
   * Returns selected colors
   * @return Color[]
   */
  private Color[] getActiveColorObjects() {
    DefaultTableModel model = ( ( DefaultTableModel ) jTable1.getModel() );
    int[] selectedRows = jTable1.getSelectedRows();
    if ( selectedRows != null && selectedRows.length > 0 ) {
      Color[] color = new Color[ selectedRows.length ];
      for ( int c = 0; c < color.length; c++ ) {
        color[c] = ( ( Color ) model.getValueAt( selectedRows[c], 0 ) );
      }
      return color;
    }
    return null;
  }

  /**
   * Returns all colors
   * @return NamedColor[]
   */
  public NamedColor[] getNamedColors() {
    DefaultTableModel model = ( ( DefaultTableModel ) jTable1.getModel() );
    NamedColor[] namedColors = new NamedColor[ model.getRowCount() ];
    for ( int c = 0; c < namedColors.length; c++ ) {
      namedColors[c] = new NamedColor( ( ( Color ) model.getValueAt( c, 0 ) ).getRGB(),
              ( String ) model.getValueAt( c, 1 ) );
    }
    return namedColors;
  }

  /**
   * Updatesor creates specified color
   * @param color
   * @param name 
   */
  public void setColor( Color color, String name ) {
    DefaultTableModel model = ( ( DefaultTableModel ) jTable1.getModel() );
    for ( int c = 0; c < model.getRowCount(); c++ ) {
      String t_name = ( String ) model.getValueAt( c, 1 );
      if ( t_name != null && t_name.equals( name ) ) {
        model.setValueAt( color, c, 0 );
        return;
      }
    }
    model.addRow( new Object[] { color, name } );
  }

  /**
   * Returns active editor panel or null in case of editor panels doesnt exist
   * @return EditorPanel
   */
  public EditorPanel getActiveEditorPanel() {
    /** Checking for at least one tab exist **/
    if ( jTabbedPane1.getTabCount() > 0 ) {
      return ( ( EditorPanel ) ( ( JScrollPane ) jTabbedPane1.getSelectedComponent() ).getViewport().getView() );
    }
    return null;
  }

  /**
   * Updates global scale factor and perform full UI update
   * @param scaleFactor 
   */
  public void updateScaleFactor( int scaleFactor ) {
    /** Obtain editor panel **/
    EditorPanel editorPanel = getActiveEditorPanel();
    if ( editorPanel != null ) {
      /** Setting scale factor up **/
      ScaleGraphics.scaleFactor = scaleFactor;
      editorPanel.updateDrawSize();
      /** Updating tabbed pane **/
      jTabbedPane1.updateUI();
      /** Updating scrolls **/
      JScrollPane scrollPane = ( JScrollPane ) jTabbedPane1.getSelectedComponent();
      scrollPane.updateUI();
    }
  }

  /**
   * Setting specified primitive as selected 
   * and opening properties panel for this primitive
   * @param primitive 
   */
  public void setSelectedPrimitive( Primitive primitive ) {
    propertiesPanel.removeAll();
    propertiesPanel.add( new PropertiesPanel( primitive ), BorderLayout.CENTER );
    propertiesPanel.updateUI();
  }
  
  public void writeToStream(OutputStream os) {
    try {
      DataOutputStream dos = new DataOutputStream(os);
      /** Header **/
      dos.writeChars( "TCSG" );
      /** Info **/
      dos.writeChar( 0x00 );
      dos.writeUTF( author );
      dos.writeLong( System.currentTimeMillis() );
      /** Colors **/
      dos.writeByte( 0x01 );
      NamedColor[] colors = getNamedColors();
      dos.writeChar( colors.length );
      for(int c=0;c<colors.length;c++) {
        dos.writeInt( colors[c].getRGB() );
        dos.writeUTF( colors[c].getName() );
      }
      dos.flush();
      /** Fragments **/
      dos.writeByte( 0x02 );
      int fragmentCount = jTabbedPane1.getTabCount();
      dos.writeChar( fragmentCount );
      for(int c=0;c<fragmentCount;c++){
        String name = jTabbedPane1.getTitleAt( c );
        EditorPanel t_ePanel = ( ( EditorPanel ) ( ( JScrollPane ) jTabbedPane1.getComponentAt( c ) ).getViewport().getView() );
        dos.write( t_ePanel.getFigure().serialize( name ) );
      }
      dos.flush();
    } catch ( IOException ex ) {
      Logger.getLogger( MainFrame.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings( "unchecked" )
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jToolBar1 = new javax.swing.JToolBar();
    jButton1 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jButton6 = new javax.swing.JButton();
    jSeparator1 = new javax.swing.JToolBar.Separator();
    jButton7 = new javax.swing.JButton();
    jButton8 = new javax.swing.JButton();
    jButton9 = new javax.swing.JButton();
    jSeparator2 = new javax.swing.JToolBar.Separator();
    jButton10 = new javax.swing.JButton();
    jButton11 = new javax.swing.JButton();
    jButton12 = new javax.swing.JButton();
    jSeparator3 = new javax.swing.JToolBar.Separator();
    jButton13 = new javax.swing.JButton();
    jSplitPane1 = new javax.swing.JSplitPane();
    jPanel1 = new javax.swing.JPanel();
    jButton4 = new javax.swing.JButton();
    jButton5 = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jButton2 = new javax.swing.JButton();
    jSplitPane2 = new javax.swing.JSplitPane();
    jSplitPane3 = new javax.swing.JSplitPane();
    propertiesPanel = new javax.swing.JPanel();
    jScrollPane4 = new javax.swing.JScrollPane();
    toolPanel = new javax.swing.JPanel();
    jTabbedPane1 = new javax.swing.JTabbedPane();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("TCSGEditor");

    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);

    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-create-file.png"))); // NOI18N
    jButton1.setFocusable(false);
    jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton1);

    jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-open-file.png"))); // NOI18N
    jButton3.setFocusable(false);
    jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar1.add(jButton3);

    jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-save-file.png"))); // NOI18N
    jButton6.setFocusable(false);
    jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton6ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton6);
    jToolBar1.add(jSeparator1);

    jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-create-figure.png"))); // NOI18N
    jButton7.setToolTipText("");
    jButton7.setFocusable(false);
    jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton7.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton7ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton7);

    jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-remove-figure.png"))); // NOI18N
    jButton8.setFocusable(false);
    jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton8ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton8);

    jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-edit-figure.png"))); // NOI18N
    jButton9.setFocusable(false);
    jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton9.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton9ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton9);
    jToolBar1.add(jSeparator2);

    jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-zoom-in.png"))); // NOI18N
    jButton10.setFocusable(false);
    jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton10.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton10ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton10);

    jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-zoom-out.png"))); // NOI18N
    jButton11.setFocusable(false);
    jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton11.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton11ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton11);

    jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-zoom-original.png"))); // NOI18N
    jButton12.setFocusable(false);
    jButton12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton12.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton12.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton12ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton12);
    jToolBar1.add(jSeparator3);

    jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icon-preview.png"))); // NOI18N
    jButton13.setFocusable(false);
    jButton13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton13.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton13.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton13ActionPerformed(evt);
      }
    });
    jToolBar1.add(jButton13);

    jSplitPane1.setDividerLocation(150);

    jButton4.setText("Изменить");
    jButton4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton4ActionPerformed(evt);
      }
    });

    jButton5.setText("Удалить");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton5ActionPerformed(evt);
      }
    });

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {

      },
      new String [] {
        "Цвет", "Название"
      }
    ) {
      Class[] types = new Class [] {
        java.awt.Color.class, java.lang.String.class
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }
    });
    jTable1.setDefaultRenderer( java.awt.Color.class, new ColorTableRenderer() );
    jTable1.getTableHeader().setReorderingAllowed(false);
    jScrollPane1.setViewportView(jTable1);

    jButton2.setText("Добавить");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton2ActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
      .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
      .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButton2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButton4)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jButton5))
    );

    jSplitPane1.setLeftComponent(jPanel1);

    jSplitPane2.setDividerLocation(390);
    jSplitPane2.setResizeWeight(1.0);

    jSplitPane3.setDividerLocation(160);
    jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    jSplitPane3.setResizeWeight(0.5);

    propertiesPanel.setLayout(new java.awt.BorderLayout());
    jSplitPane3.setRightComponent(propertiesPanel);

    javax.swing.GroupLayout toolPanelLayout = new javax.swing.GroupLayout(toolPanel);
    toolPanel.setLayout(toolPanelLayout);
    toolPanelLayout.setHorizontalGroup(
      toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 239, Short.MAX_VALUE)
    );
    toolPanelLayout.setVerticalGroup(
      toolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 158, Short.MAX_VALUE)
    );

    jScrollPane4.setViewportView(toolPanel);

    jSplitPane3.setLeftComponent(jScrollPane4);

    jSplitPane2.setRightComponent(jSplitPane3);

    jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
    jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        jTabbedPane1StateChanged(evt);
      }
    });
    jSplitPane2.setLeftComponent(jTabbedPane1);

    jSplitPane1.setRightComponent(jSplitPane2);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jSplitPane1))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // TODO
  }//GEN-LAST:event_jButton1ActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    ( new ColorDialog( this, true, null, new ColorListener() {
      @Override
      public void colorChanged( NamedColor namedColor ) {
        setColor( namedColor, namedColor.getName() );
      }
    } ) ).setVisible( true );
  }//GEN-LAST:event_jButton2ActionPerformed

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    DefaultTableModel model = ( ( DefaultTableModel ) jTable1.getModel() );
    int selectedRow = jTable1.getSelectedRow();
    if ( selectedRow >= 0 && selectedRow < model.getRowCount() ) {
      ( new ColorDialog( this, true, new NamedColor( ( ( Color ) model.getValueAt( selectedRow, 0 ) ).getRGB(),
              ( String ) model.getValueAt( selectedRow, 1 ) ),
              new ColorListener() {
                @Override
                public void colorChanged( NamedColor namedColor ) {
                  setColor( namedColor, namedColor.getName() );
                }
              } ) ).setVisible( true );

    }
  }//GEN-LAST:event_jButton4ActionPerformed

  private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    DefaultTableModel model = ( ( DefaultTableModel ) jTable1.getModel() );
    int selectedRow = jTable1.getSelectedRow();
    if ( selectedRow >= 0 && selectedRow < model.getRowCount() ) {
      model.removeRow( selectedRow );
    }
  }//GEN-LAST:event_jButton5ActionPerformed

  private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
    ( new EditFigureDialog( this, true ) ).setVisible( true );
  }//GEN-LAST:event_jButton7ActionPerformed

  private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
    updateScaleFactor( ScaleGraphics.scaleFactor + 1 );
  }//GEN-LAST:event_jButton10ActionPerformed

  private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
    if ( ScaleGraphics.scaleFactor > 1 ) {
      updateScaleFactor( ScaleGraphics.scaleFactor - 1 );
    }
  }//GEN-LAST:event_jButton11ActionPerformed

  private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
    updateScaleFactor( 1 );
  }//GEN-LAST:event_jButton12ActionPerformed

  private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
    updateScaleFactor( ScaleGraphics.scaleFactor );
  }//GEN-LAST:event_jTabbedPane1StateChanged

  private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
    new PreviewDialog( this, true, getActiveEditorPanel().getFigure() )
            .setVisible( true );
  }//GEN-LAST:event_jButton13ActionPerformed

  private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
    int selectedIndex = jTabbedPane1.getSelectedIndex();
    if ( selectedIndex >= 0 && selectedIndex < jTabbedPane1.getTabCount() ) {
      jTabbedPane1.remove( selectedIndex );
      jTabbedPane1.updateUI();
    }
  }//GEN-LAST:event_jButton8ActionPerformed

  private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
    int selectedIndex = jTabbedPane1.getSelectedIndex();
    EditorPanel editorPanel = getActiveEditorPanel();
    if ( editorPanel != null && selectedIndex >= 0
            && selectedIndex < jTabbedPane1.getTabCount() ) {
      String figureName = jTabbedPane1.getTitleAt( selectedIndex );
      Dimension templateSize = editorPanel.getTemplateSize();
      ( new EditFigureDialog( this, true, selectedIndex, figureName,
              templateSize.width, templateSize.height ) ).setVisible( true );
    }
  }//GEN-LAST:event_jButton9ActionPerformed

  private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    try {
      java.io.FileOutputStream fos = new java.io.FileOutputStream( "/home/solkin/fragments.cut" );
      writeToStream( fos );
      fos.close();
    } catch ( IOException ex ) {
      Logger.getLogger( MainFrame.class.getName() ).log( Level.SEVERE, null, ex );
    }
  }//GEN-LAST:event_jButton6ActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton10;
  private javax.swing.JButton jButton11;
  private javax.swing.JButton jButton12;
  private javax.swing.JButton jButton13;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButton5;
  private javax.swing.JButton jButton6;
  private javax.swing.JButton jButton7;
  private javax.swing.JButton jButton8;
  private javax.swing.JButton jButton9;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane4;
  private javax.swing.JToolBar.Separator jSeparator1;
  private javax.swing.JToolBar.Separator jSeparator2;
  private javax.swing.JToolBar.Separator jSeparator3;
  private javax.swing.JSplitPane jSplitPane1;
  private javax.swing.JSplitPane jSplitPane2;
  private javax.swing.JSplitPane jSplitPane3;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTable jTable1;
  private javax.swing.JToolBar jToolBar1;
  private javax.swing.JPanel propertiesPanel;
  private javax.swing.JPanel toolPanel;
  // End of variables declaration//GEN-END:variables
}
