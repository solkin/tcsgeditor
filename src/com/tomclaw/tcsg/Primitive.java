package com.tomclaw.tcsg;

import java.awt.Graphics;

/**
 *
 * @author Solkin
 */
public abstract class Primitive implements Cloneable {

  public abstract void paint( Graphics g );

  public abstract void setSecLocation( int x, int y );

  public abstract void setFigure( Figure figure );

  public abstract void setLocation( int x, int y );

  public abstract Gabarite getGabarite( );
  
  public abstract Object[][] getFields();
  
  public abstract void setFields(Object[][] fields);
  
  @Override
  public Object clone(){
    try {
      return super.clone();
    } catch ( CloneNotSupportedException ex ) {
    }
    return null;
}
}
