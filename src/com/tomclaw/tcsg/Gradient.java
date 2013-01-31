package com.tomclaw.tcsg;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import tcsgeditor.Selector;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 * @author Solkin
 */
public class Gradient extends Primitive {

  private int x, y, width, height;
  private int colorFrom, colorFinl;
  private boolean isProportional;
  private Fragment fragment;
  private boolean isFill;
  private boolean isVertical;
  private int t_x, t_y, t_w, t_h;

  public Gradient( DataInputStream dis ) throws IOException {
    read( dis );
  }

  public Gradient( int x, int y, int width, int height, int colorFrom,
          int colorFinl, boolean isFill, boolean isVertical,
          boolean isProportional, Fragment fragment ) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.colorFrom = colorFrom;
    this.colorFinl = colorFinl;
    this.isFill = isFill;
    this.isVertical = isVertical;
    this.isProportional = isProportional;
    this.fragment = fragment;
  }

  @Override
  public void paint( Graphics g ) {
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
      if ( isVertical ) {
        ScaleGraphics.fillVerticalGradient( g, t_x, t_y, t_w, t_h, colorFrom, colorFinl );
      } else {
        ScaleGraphics.fillHorizontalGradient( g, t_x, t_y, t_w, t_h, colorFrom, colorFinl );
      }
    } else {
      if ( isVertical ) {
        ScaleGraphics.drawVerticalGradient( g, t_x, t_y, t_w, t_h, colorFrom, colorFinl );
      } else {
        ScaleGraphics.drawHorizontalGradient( g, t_x, t_y, t_w, t_h, colorFrom, colorFinl );
      }
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
              { "Цвет начальный", new Color( colorFrom ) },
              { "Цвет конечный", new Color( colorFinl ) },
              { "Пропорциональность", Boolean.valueOf( isProportional ) },
              { "Заполнение", Boolean.valueOf( isFill ) },
              { "Направление", new Selector( new String[]{ "Вертикальный",
                  "Горизонтальный" }, isVertical ? 0 : 1 ) }
            };
  }

  @Override
  public void setFields( Object[][] fields ) {
    x = ( Integer ) fields[0][1];
    y = ( Integer ) fields[1][1];
    width = ( Integer ) fields[2][1];
    height = ( Integer ) fields[3][1];
    colorFrom = ( ( Color ) fields[4][1] ).getRGB();
    colorFinl = ( ( Color ) fields[5][1] ).getRGB();
    isProportional = ( Boolean ) fields[6][1];
    isFill = ( Boolean ) fields[7][1];
    isVertical = ( ( Selector ) fields[8][1] ).getSelectedIndex() == 0
            ? true : false;
  }

  @Override
  public int getType() {
    return Primitive.TYPE_GRADIENT;
  }

  @Override
  public void write( DataOutputStream dos ) throws IOException {
    dos.writeShort( x );
    dos.writeShort( y );
    dos.writeShort( width );
    dos.writeShort( height );
    dos.writeInt( colorFrom );
    dos.writeInt( colorFinl );
    dos.writeBoolean( isProportional );
    dos.writeBoolean( isFill );
    dos.writeBoolean( isVertical );
  }

  @Override
  public final void read( DataInputStream dis ) throws IOException {
    x = dis.readShort();
    y = dis.readShort();
    width = dis.readShort();
    height = dis.readShort();
    colorFrom = dis.readInt();
    colorFinl = dis.readInt();
    isProportional = dis.readBoolean();
    isFill = dis.readBoolean();
    isVertical = dis.readBoolean();
  }
}
