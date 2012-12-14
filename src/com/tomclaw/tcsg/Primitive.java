package com.tomclaw.tcsg;

import java.awt.Graphics;

/**
 *
 * @author Solkin
 */
public abstract class Primitive implements Cloneable {

  public static final int TYPE_POINT = 0x01;
  public static final int TYPE_LINE = 0x02;
  public static final int TYPE_RECT = 0x03;
  public static final int TYPE_GRADIENT = 0x04;

  public abstract void paint( Graphics g );

  public abstract void setSecLocation( int x, int y );

  public abstract void setFigure( Fragment figure );

  public abstract void setLocation( int x, int y );

  public abstract Gabarite getGabarite();

  public abstract Object[][] getFields();

  public abstract void setFields( Object[][] fields );

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch ( CloneNotSupportedException ex ) {
    }
    return null;
  }

  public abstract int getType();

  public abstract byte[] serialize();

  public abstract void deserialize( byte[] data );
}
