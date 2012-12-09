package com.tomclaw.tcsg;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author solkin
 */
public class Point extends Primitive {

  private int x, y;
  private int color;
  private boolean isProportional;
  private Figure figure;

  public Point( int x, int y, int color, boolean isProportional, Figure figure ) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.isProportional = isProportional;
    this.figure = figure;
  }

  public void paint( Graphics g ) {
    ScaleGraphics.setColor( g, color );
    if ( isProportional ) {
      ScaleGraphics.drawPoint( g, figure.getPropX( x ),
              figure.getPropY( y ) );
    } else {
      ScaleGraphics.drawPoint( g, figure.getAbsX( x ),
              figure.getAbsY( y ) );
    }
  }

  @Override
  public void setSecLocation( int x, int y ) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void setFigure( Figure figure ) {
    this.figure = figure;
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
    x = (Integer)fields[0][1];
    y = (Integer)fields[1][1];
    color = ((Color)fields[2][1]).getRGB();
    isProportional = (Boolean)fields[3][1];
  }
}
