package tcsgeditor;

import java.awt.Color;

/**
 *
 * @author solkin
 */
public class NamedColor extends Color {

  private String name;

  public NamedColor( int color, String name ) {
    super( color );
    setName( name );
  }

  public String getName() {
    return name;
  }

  public final void setName( String name ) {
    this.name = name;
  }
}
