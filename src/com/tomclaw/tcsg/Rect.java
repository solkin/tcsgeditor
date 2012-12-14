package com.tomclaw.tcsg;

import com.tomclaw.utils.ArrayOutputStream;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Solkin
 */
public class Rect extends Primitive {

  private int x, y, width, height;
  private int color;
  private boolean isProportional;
  private Fragment figure;
  private boolean isFill;
  private int t_x, t_y, t_w, t_h;

  public Rect( int x, int y, int width, int height, int color, boolean isFill, boolean isProportional, Fragment figure ) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
    this.isFill = isFill;
    this.isProportional = isProportional;
    this.figure = figure;
  }

  public void paint( Graphics g ) {
    ScaleGraphics.setColor( g, color );
    if ( isProportional ) {
      t_x = figure.getPropX( x );
      t_y = figure.getPropY( y );
      t_w = figure.getPropWidth( width );
      t_h = figure.getPropHeight( height );
    } else {
      t_x = figure.getAbsX( x );
      t_y = figure.getAbsY( y );
      t_w = figure.getAbsWidth( x, width );
      t_h = figure.getAbsHeight( y, height );
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
  public void setFigure( Fragment figure ) {
    this.figure = figure;
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
    return new Object[][] {
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
    x = (Integer)fields[0][1];
    y = (Integer)fields[1][1];
    width = (Integer)fields[2][1];
    height = (Integer)fields[3][1];
    color = ((Color)fields[4][1]).getRGB();
    isProportional = (Boolean)fields[5][1];
    isFill = (Boolean)fields[6][1];
  }
  
  @Override
  public int getType() {
    return Primitive.TYPE_RECT;
  }

  @Override
  public byte[] serialize() {
    ArrayOutputStream aos = new ArrayOutputStream(14);
    aos.writeWord( x );
    aos.writeWord( y );
    aos.writeWord( width );
    aos.writeWord( height );
    aos.writeDWord( color );
    aos.writeBool( isProportional );
    aos.writeBool( isFill );
    return aos.getData();
  }

  @Override
  public void deserialize( byte[] data ) {
    throw new UnsupportedOperationException( "Not supported yet." );
  }
}
