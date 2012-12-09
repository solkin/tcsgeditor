package com.tomclaw.tcsg;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Solkin
 */
public class Line extends Primitive {

  public int x1, y1, x2, y2;
  private int color;
  private boolean isProportional;
  private Figure figure;

  public Line( int x1, int y1, int x2, int y2, int color, boolean isProportional, Figure figure ) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
    this.color = color;
    this.isProportional = isProportional;
    this.figure = figure;
  }

  @Override
  public void paint( Graphics g ) {
    ScaleGraphics.setColor( g, color );
    if ( isProportional ) {
      ScaleGraphics.drawLine( g, figure.getPropX( x1 ),
              figure.getPropY( y1 ),
              figure.getSecPropX( x1, x2 ),
              figure.getSecPropY( y1, y2 ) );
    } else {
      ScaleGraphics.drawLine( g, figure.getAbsX( x1 ),
              figure.getAbsY( y1 ),
              figure.getSecAbsX( x2 ),
              figure.getSecAbsY( y2 ) );
    }
  }

  @Override
  public void setSecLocation( int x, int y ) {
    x2 = x;
    y2 = y;
  }

  @Override
  public void setFigure( Figure figure ) {
    this.figure = figure;
  }

  @Override
  public void setLocation( int x, int y ) {
    this.x1 = x;
    this.y1 = y;
  }

  @Override
  public Gabarite getGabarite() {
    return new Gabarite( x1, y1, x2, y2 );
  }

  @Override
  public Object[][] getFields() {
    return new Object[][] {
              { "Начальный X", Integer.valueOf( x1 ) },
              { "Начальный Y", Integer.valueOf( y1 ) },
              { "Конецный X", Integer.valueOf( x2 ) },
              { "Конечный Y", Integer.valueOf( y2 ) },
              { "Цвет", new Color( color ) },
              { "Пропорциональность", Boolean.valueOf( isProportional ) }
            };
  }

  @Override
  public void setFields( Object[][] fields ) {
    x1 = (Integer)fields[0][1];
    y1 = (Integer)fields[1][1];
    x2 = (Integer)fields[2][1];
    y2 = (Integer)fields[3][1];
    color = ((Color)fields[4][1]).getRGB();
    isProportional = (Boolean)fields[5][1];
  }
}
