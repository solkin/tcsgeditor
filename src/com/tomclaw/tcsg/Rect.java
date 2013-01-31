package com.tomclaw.tcsg;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class Rect extends Primitive {

  private int x, y, width, height;
  private int color;
  private boolean isProportional;
  private Fragment fragment;
  private boolean isFill;
  private int t_x, t_y, t_w, t_h;

  public Rect( DataInputStream dis ) throws IOException {
    read( dis );
  }

  public Rect( int x, int y, int width, int height, int color, boolean isFill, boolean isProportional, Fragment fragment ) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
    this.isFill = isFill;
    this.isProportional = isProportional;
    this.fragment = fragment;
  }

  public void paint( Graphics g ) {
    ScaleGraphics.setColor( g, color );
    if ( isProportional ) {
      t_x = fragment.getPropX( x );
      t_y = fragment.getPropY( y );
      t_w = fragment.getPropWidth( width );
      t_h = fragment.getPropHeight( height );
    } else {
      t_x = fragment.getAbsX( x );
      t_y = fragment.getAbsY( y );
      t_w = fragment.getAbsWidth( x, width );
      t_h = fragment.getAbsHeight( y, height );
    }
    if ( isFill ) {
      ScaleGraphics.fillRect( g, t_x, t_y, t_w, t_h );
    } else {
      ScaleGraphics.drawRect( g, t_x, t_y, t_w, t_h );
    }
  }

  @Override
  public void setSecLocation( int x, int y ) {
    width = x - this.x;
    height = y - this.y;
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
    return new Gabarite( x, y, x + width, y + height );
  }

  @Override
  public Object[][] getFields() {
    return new Object[][]{
              { "Положение X", Integer.valueOf( x ) },
              { "Положение Y", Integer.valueOf( y ) },
              { "Ширина", Integer.valueOf( width ) },
              { "Высота", Integer.valueOf( height ) },
              { "Цвет", new Color( color ) },
              { "Пропорциональность", Boolean.valueOf( isProportional ) },
              { "Заполнение", Boolean.valueOf( isFill ) }
            };
  }

  @Override
  public void setFields( Object[][] fields ) {
    x = ( Integer ) fields[0][1];
    y = ( Integer ) fields[1][1];
    width = ( Integer ) fields[2][1];
    height = ( Integer ) fields[3][1];
    color = ( ( Color ) fields[4][1] ).getRGB();
    isProportional = ( Boolean ) fields[5][1];
    isFill = ( Boolean ) fields[6][1];
  }

  @Override
  public int getType() {
    return Primitive.TYPE_RECT;
  }

  @Override
  public void write( DataOutputStream dos ) throws IOException {
    dos.writeShort( x );
    dos.writeShort( y );
    dos.writeShort( width );
    dos.writeShort( height );
    dos.writeInt( color );
    dos.writeBoolean( isProportional );
    dos.writeBoolean( isFill );
  }

  @Override
  public final void read( DataInputStream dis ) throws IOException {
    x = dis.readShort();
    y = dis.readShort();
    width = dis.readShort();
    height = dis.readShort();
    color = dis.readInt();
    isProportional = dis.readBoolean();
    isFill = dis.readBoolean();
  }
}
