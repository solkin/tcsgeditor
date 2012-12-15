package com.tomclaw.tcsg;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author solkin
 */
public class Point extends Primitive {

  private int x, y;
  private int color;
  private boolean isProportional;
  private Fragment fragment;

  public Point( int x, int y, int color, boolean isProportional, Fragment fragment ) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.isProportional = isProportional;
    this.fragment = fragment;
  }

  public void paint( Graphics g ) {
    ScaleGraphics.setColor( g, color );
    if ( isProportional ) {
      ScaleGraphics.drawPoint( g, fragment.getPropX( x ),
              fragment.getPropY( y ) );
    } else {
      ScaleGraphics.drawPoint( g, fragment.getAbsX( x ),
              fragment.getAbsY( y ) );
    }
  }

  @Override
  public void setSecLocation( int x, int y ) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void setFigure( Fragment fragment ) {
    this.fragment = fragment;
  }

  @Override
  public void setLocation( int x, int y ) {
    this.x = x;
    this.y = y;
  }

  @Override
  public Gabarite getGabarite() {
    return new Gabarite( x, y, x + ScaleGraphics.scaleFactor, y + ScaleGraphics.scaleFactor );
  }

  @Override
  public Object[][] getFields() {
    return new Object[][] {
              { "Положение X", Integer.valueOf( x ) },
              { "Положение Y", Integer.valueOf( y ) },
              { "Цвет", new Color( color ) },
              { "Пропорциональность", Boolean.valueOf( isProportional ) }
            };
  }

  @Override
  public void setFields( Object[][] fields ) {
    x = ( Integer ) fields[0][1];
    y = ( Integer ) fields[1][1];
    color = ( ( Color ) fields[2][1] ).getRGB();
    isProportional = ( Boolean ) fields[3][1];
  }

  @Override
  public int getType() {
    return Primitive.TYPE_POINT;
  }

  @Override
  public void write( DataOutputStream dos ) throws IOException {
    dos.writeChar( x );
    dos.writeChar( y );
    dos.writeInt( color );
    dos.writeBoolean( isProportional );
  }

  @Override
  public void read( DataInputStream dis ) {
    throw new UnsupportedOperationException( "Not supported yet." );
  }
}
