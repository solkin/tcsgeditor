package com.tomclaw.tcsg;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author solkin
 */
public class ScaleGraphics {

  public static int scaleFactor = 6;
  /** Primary colors **/
  private static int pRed__ = 0;
  private static int pGreen = 0;
  private static int pBlue_ = 0;
  /** Secondary colors **/
  private static int sRed__ = 0;
  private static int sGreen = 0;
  private static int sBlue_ = 0;
  /** Editor objects **/
  public static Primitive lastPainted;
  public static Primitive fixedPainted;
  public static int fixedX = 0;
  public static int fixedY = 0;

  public static void setColor( Graphics g, int color ) {
    System.out.println( "Color: " + color );
    g.setColor( new Color( color ) );
  }

  public static void setColor( Graphics g, int red, int green, int blue ) {
    System.out.println( "Color (RGB): (" + red + ", " + green + ", " + blue + ")" );
    setColor( g, ( red << 16 ) | ( green << 8 ) | blue );
  }

  public static void drawPoint( Graphics g, int x, int y ) {
    System.out.println( "Point: (" + x + ", " + y + ")" );
    g.fillRect( x, y, scaleFactor, scaleFactor );
    /** Editor's code **/
    checkFixedLocation( x, y, scaleFactor, scaleFactor );
  }

  public static void drawRect( Graphics g, int x, int y, int w, int h ) {
    System.out.println( "Rect: (" + x + ", " + y + ") : [" + w + ", " + h + "]" );
    if ( scaleFactor == 1 ) {
      g.drawRect( x, y, w, h );
    } else {
      g.fillRect( x, y, w, scaleFactor );
      g.fillRect( x, y, scaleFactor, h );
      g.fillRect( x + w, y, scaleFactor, h + scaleFactor );
      g.fillRect( x, y + h, w, scaleFactor );
    }
    /** Editor's code **/
    checkFixedLocation( x, y, w, scaleFactor );
    checkFixedLocation( x, y, scaleFactor, h );
    checkFixedLocation( x + w, y, scaleFactor, h + scaleFactor );
    checkFixedLocation( x, y + h, w, scaleFactor );
  }

  public static void fillRect( Graphics g, int x, int y, int w, int h ) {
    w += scaleFactor;
    h += scaleFactor;
    System.out.println( "Fill Rect: (" + x + ", " + y + ") : [" + w + ", " + h + "]" );
    g.fillRect( x, y, w, h );
    /** Editor's code **/
    checkFixedLocation( x, y, w, h );
  }

  public static void drawLine( Graphics g, int x1, int y1, int x2, int y2 ) {
    System.out.println( "Line: (" + x1 + ", " + y1 + ") - (" + x2 + ", " + y2 + ")" );
    if ( scaleFactor == 1 ) {
      g.drawLine( x1, y1, x2, y2 );
    } else {
      if ( Math.abs( x2 - x1 ) >= Math.abs( y2 - y1 ) ) {
        for ( int x = 0; x <= ( x2 - x1 ); x += scaleFactor ) {
          g.fillRect( x1 + x,
                  y1 + roundScale( ( y2 - y1 ) * x / ( x2 - x1 ) ), scaleFactor, scaleFactor );
        }
      } else {
        for ( int y = 0; y <= ( y2 - y1 ); y += scaleFactor ) {
          g.fillRect( x1 + roundScale( ( x2 - x1 ) * y / ( y2 - y1 ) ),
                  y1 + y, scaleFactor, scaleFactor );
        }
      }
    }
    /** Editor's code **/
    if ( Math.abs( x2 - x1 ) >= Math.abs( y2 - y1 ) ) {
      for ( int x = 0; x <= ( x2 - x1 ); x += scaleFactor ) {
        /** Editor's code **/
        checkFixedLocation( x1 + x,
                y1 + roundScale( ( y2 - y1 ) * x / ( x2 - x1 ) ), scaleFactor, scaleFactor );
      }
    } else {
      for ( int y = 0; y <= ( y2 - y1 ); y += scaleFactor ) {
        /** Editor's code **/
        checkFixedLocation( x1 + roundScale( ( x2 - x1 ) * y / ( y2 - y1 ) ),
                y1 + y, scaleFactor, scaleFactor );
      }
    }
  }

  private static int roundScale( int a ) {
    return ( ( int ) ( a / scaleFactor ) ) * scaleFactor;
  }

  public static void fillHorizontalGradient( Graphics g, int objX, int objY, int objWidth, int objHeight, int color1, int color2 ) {
    objWidth += scaleFactor;
    objHeight += scaleFactor;
    System.out.println( "Fill Gradient: (" + objX + ", " + objY + ") : [" + objWidth + ", " + objHeight + "]" );
    /** Primary colors **/
    pRed__ = ( color1 & 0xFF0000 ) >> 16;
    pGreen = ( color1 & 0x00FF00 ) >> 8;
    pBlue_ = ( color1 & 0x0000FF );
    /** Secondary colors **/
    sRed__ = ( color2 & 0xFF0000 ) >> 16;
    sGreen = ( color2 & 0x00FF00 ) >> 8;
    sBlue_ = ( color2 & 0x0000FF );
    /** Drawing gradient **/
    for ( int x = 0; x < objWidth; x += scaleFactor ) {
      setColor( g, ( sRed__ - pRed__ ) * x / objWidth + pRed__,
              ( sGreen - pGreen ) * x / objWidth + pGreen,
              ( sBlue_ - pBlue_ ) * x / objWidth + pBlue_ );
      g.fillRect( objX + x, objY, scaleFactor, objHeight );
      /** Editor's code **/
      checkFixedLocation( objX + x, objY, scaleFactor, objHeight );
    }
  }

  public static void drawHorizontalGradient( Graphics g, int objX, int objY, int objWidth, int objHeight, int color1, int color2 ) {
    System.out.println( "Gradient: (" + objX + ", " + objY + ") : [" + objWidth + ", " + objHeight + "]" );
    /** Drawing horizontal lines **/
    setColor( g, color1 );
    g.fillRect( objX, objY, scaleFactor, objHeight + scaleFactor );
    setColor( g, color2 );
    g.fillRect( objX + objWidth, objY, scaleFactor, objHeight + scaleFactor );
    /** Drawing vertical lines **/
    /** Primary colors **/
    pRed__ = ( color1 & 0xFF0000 ) >> 16;
    pGreen = ( color1 & 0x00FF00 ) >> 8;
    pBlue_ = ( color1 & 0x0000FF );
    /** Secondary colors **/
    sRed__ = ( color2 & 0xFF0000 ) >> 16;
    sGreen = ( color2 & 0x00FF00 ) >> 8;
    sBlue_ = ( color2 & 0x0000FF );
    /** Drawing gradient **/
    for ( int x = scaleFactor; x < objWidth; x += scaleFactor ) {
      setColor( g, ( sRed__ - pRed__ ) * x / objWidth + pRed__,
              ( sGreen - pGreen ) * x / objWidth + pGreen,
              ( sBlue_ - pBlue_ ) * x / objWidth + pBlue_ );
      g.fillRect( objX + x, objY, scaleFactor, scaleFactor );
      g.fillRect( objX + x, objY + objHeight, scaleFactor, scaleFactor );
      /** Editor's code **/
      checkFixedLocation( objX + x, objY, scaleFactor, scaleFactor );
      checkFixedLocation( objX + x, objY + objHeight, scaleFactor, scaleFactor );
    }
    /** Editor's code **/
    checkFixedLocation( objX, objY, scaleFactor, objHeight + scaleFactor );
    checkFixedLocation( objX + objWidth, objY, scaleFactor, objHeight + scaleFactor );
  }

  public static void fillVerticalGradient( Graphics g, int objX, int objY, int objWidth, int objHeight, int color1, int color2 ) {
    objWidth += scaleFactor;
    objHeight += scaleFactor;
    System.out.println( "Fill Gradient: (" + objX + ", " + objY + ") : [" + objWidth + ", " + objHeight + "]" );
    /** Primary colors **/
    pRed__ = ( color1 & 0xFF0000 ) >> 16;
    pGreen = ( color1 & 0x00FF00 ) >> 8;
    pBlue_ = ( color1 & 0x0000FF );
    /** Secondary colors **/
    sRed__ = ( color2 & 0xFF0000 ) >> 16;
    sGreen = ( color2 & 0x00FF00 ) >> 8;
    sBlue_ = ( color2 & 0x0000FF );
    /** Drawing gradient **/
    for ( int y = 0; y < objHeight; y += scaleFactor ) {
      setColor( g, ( sRed__ - pRed__ ) * y / objHeight + pRed__,
              ( sGreen - pGreen ) * y / objHeight + pGreen,
              ( sBlue_ - pBlue_ ) * y / objHeight + pBlue_ );
      g.fillRect( objX, objY + y, objWidth, scaleFactor );
      /** Editor's code **/
      checkFixedLocation( objX, objY + y, objWidth, scaleFactor );
    }
  }

  public static void drawVerticalGradient( Graphics g, int objX, int objY, int objWidth, int objHeight, int color1, int color2 ) {
    System.out.println( "Gradient: (" + objX + ", " + objY + ") : [" + objWidth + ", " + objHeight + "]" );
    /** Drawing horizontal lines **/
    setColor( g, color1 );
    g.fillRect( objX, objY, objWidth + scaleFactor, scaleFactor );
    setColor( g, color2 );
    g.fillRect( objX, objY + objHeight, objWidth + scaleFactor, scaleFactor );
    /** Drawing vertical lines **/
    /** Primary colors **/
    pRed__ = ( color1 & 0xFF0000 ) >> 16;
    pGreen = ( color1 & 0x00FF00 ) >> 8;
    pBlue_ = ( color1 & 0x0000FF );
    /** Secondary colors **/
    sRed__ = ( color2 & 0xFF0000 ) >> 16;
    sGreen = ( color2 & 0x00FF00 ) >> 8;
    sBlue_ = ( color2 & 0x0000FF );
    /** Drawing gradient **/
    for ( int y = scaleFactor; y < objHeight; y += scaleFactor ) {
      setColor( g, ( sRed__ - pRed__ ) * y / objHeight + pRed__,
              ( sGreen - pGreen ) * y / objHeight + pGreen,
              ( sBlue_ - pBlue_ ) * y / objHeight + pBlue_ );
      g.fillRect( objX, objY + y, scaleFactor, scaleFactor );
      g.fillRect( objX + objWidth, objY + y, scaleFactor, scaleFactor );
      /** Editor's code **/
      checkFixedLocation( objX, objY + y, scaleFactor, scaleFactor );
      checkFixedLocation( objX + objWidth, objY + y, scaleFactor, scaleFactor );
    }
    /** Editor's code **/
    checkFixedLocation( objX, objY, objWidth + scaleFactor, scaleFactor );
    checkFixedLocation( objX, objY + objHeight, objWidth + scaleFactor, scaleFactor );
  }

  public static void checkFixedLocation( int x, int y, int w, int h ) {
    if ( x <= fixedX && y <= fixedY && fixedX < x + w && fixedY < y + h ) {
      // System.out.println( x + ">=" + fixedX + "&&" + y + ">=" + fixedY 
      //         + "&&" + fixedX + "<" + ( x + w ) + "&&" + fixedY + "<" + ( y + h ) );
      fixedPainted = lastPainted;
    }
  }
}
