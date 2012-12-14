package com.tomclaw.tcsg;

import com.tomclaw.utils.ArrayOutputStream;
import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Solkin
 */
public class Fragment {

  private Primitive[] items;
  private int templateWidth, templateHeight;
  private int drawX, drawY, drawWidth, drawHeight;

  public Fragment( int templateWidth, int templateHeight,
          int drawX, int drawY, int drawWidth, int drawHeight ) {
    this.templateWidth = templateWidth;
    this.templateHeight = templateHeight;
    this.drawX = drawX;
    this.drawY = drawY;
    this.drawWidth = drawWidth;
    this.drawHeight = drawHeight;
  }

  public Fragment( Fragment figure ) {
    /** Clonning figure **/
    this( figure.templateWidth, figure.templateHeight, figure.drawX,
            figure.drawY, figure.drawWidth, figure.drawHeight );
    /** Cycling all items **/
    items = new Primitive[ figure.items.length ];
    for ( int c = 0; c < figure.items.length; c++ ) {
      /** Clonning primitive **/
      Primitive primitive = ( Primitive ) figure.items[c].clone();
      primitive.setFigure( Fragment.this );
      items[c] = primitive;
    }
  }

  public void setTemplateSize( int templateWidth, int templateHeight ) {
    this.templateWidth = templateWidth;
    this.templateHeight = templateHeight;
  }

  public void setDrawSize( int drawWidth, int drawHeight ) {
    this.drawWidth = drawWidth;
    this.drawHeight = drawHeight;
  }

  public void setDrawLocation( int drawX, int drawY ) {
    this.drawX = drawX;
    this.drawY = drawY;
  }

  public int getDrawWidth() {
    return drawWidth;
  }

  public int getDrawHeight() {
    return drawHeight;
  }

  public int getDrawX() {
    return drawX;
  }

  public int getDrawY() {
    return drawY;
  }

  public void setPrimitives( Primitive[] items ) {
    this.items = items;
  }

  public void paint( Graphics g ) {
    /** Cycling all items **/
    for ( int c = 0; c < items.length; c++ ) {
      /** Drawing primitive **/
      items[c].paint( g );
    }
  }

  public int getPropX( int x ) {
    return getPropPoint( x, templateWidth, drawX, drawWidth );
  }

  public int getPropY( int y ) {
    return getPropPoint( y, templateHeight, drawY, drawHeight );
  }

  public int getSecPropX( int x1, int x2 ) {
    return getPropX( x1 ) + getPropSize( x2 - x1, templateWidth, drawWidth );
  }

  public int getSecPropY( int y1, int y2 ) {
    return getPropY( y1 ) + getPropSize( y2 - y1, templateHeight, drawHeight );
  }

  public int getPropWidth( int width ) {
    return getPropSize( width, templateWidth, drawWidth );
  }

  public int getPropHeight( int height ) {
    return getPropSize( height, templateHeight, drawHeight );
  }

  public int getPropPoint( int point, int templateSize, int drawPoint, int drawSize ) {
    /** Calculating proportional size **/
    return drawPoint + drawSize * point / templateSize;
  }

  public int getPropSize( int size, int templateSize, int drawSize ) {
    /** Calculating proportional size **/
    return ( size + 1 ) * drawSize / templateSize - ScaleGraphics.scaleFactor;
  }

  /************* Absolute coordinates *************/
  public int getAbsX( int x ) {
    return getAbsPoint( x, templateWidth, drawX, drawWidth );
  }

  public int getAbsY( int y ) {
    return getAbsPoint( y, templateHeight, drawY, drawHeight );
  }

  public int getSecAbsX( int x2 ) {
    return getAbsX( x2 );
  }

  public int getSecAbsY( int y2 ) {
    return getAbsY( y2 );
  }

  public int getAbsWidth( int x, int width ) {
    return getAbsSize( width, x, templateWidth, drawWidth );
  }

  public int getAbsHeight( int y, int height ) {
    return getAbsSize( height, y, templateHeight, drawHeight );
  }

  public int getAbsPoint( int point, int templateSize, int drawPoint, int drawSize ) {
    /** Calculating absolute size **/
    if ( point >= 0 && point < templateSize ) {
      return drawPoint + point * ScaleGraphics.scaleFactor;
    } else if ( point == templateSize ) {
      return drawPoint + drawSize - ScaleGraphics.scaleFactor;
    } else {
      return drawPoint + drawSize + ( point - 1 ) * ScaleGraphics.scaleFactor;
    }
  }

  public int getAbsSize( int size, int point, int templateSize, int drawSize ) {
    /** Calculating absolute size **/
    if ( size >= 0 && size < templateSize ) {
      return size * ScaleGraphics.scaleFactor;
    } else if ( size == templateSize ) {
      return drawSize - ( point + 1 ) * ScaleGraphics.scaleFactor;
    } else {
      return drawSize - ( point - size + 1 ) * ScaleGraphics.scaleFactor;
    }
  }

  public Primitive[] getPrimitives() {
    return items;
  }

  public int getPrimitivesCount() {
    return items.length;
  }

  public int getTemplateWidth() {
    return templateWidth;
  }

  public int getTemplateHeight() {
    return templateHeight;
  }
  
  public byte[] serialize(String name) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      dos.writeUTF( name );
      dos.writeChar( templateWidth );
      dos.writeChar( templateHeight );
      dos.writeChar( items.length );
      /** Cycling all items **/
      for ( int c = 0; c < items.length; c++ ) {
        byte[] data = items[c].serialize();
        dos.writeChar( items[c].getType() );
        dos.writeChar( data.length );
        dos.write( data );
      }
      dos.flush();
      return baos.toByteArray();
    } catch ( IOException ex ) {
      Logger.getLogger( Fragment.class.getName() ).log( Level.SEVERE, null, ex );
    }
    return null;
  }
}
