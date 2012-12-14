package com.tomclaw.utils;

import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author solkin
 */
public class ArrayOutputStream extends OutputStream {

  private final byte[] data;
  private int offset;

  public ArrayOutputStream( int size ) {
    data = new byte[ size ];
    offset = 0;
  }

  public void writeBool( boolean a ) {
    data[offset] = ( byte ) ( a ? 1 : 0 );
    offset++;
  }

  public void writeByte( byte a ) {
    data[offset] = a;
    offset++;
  }

  public void writeWord( int a ) {
    data[offset] = ( byte ) ( ( a >> 8 ) & 0xff );
    data[++offset] = ( byte ) ( a & 0xff );
  }

  public void writeWordLE( int a ) {
    data[offset] = ( byte ) ( a & 0xff );
    data[++offset] = ( byte ) ( ( a >> 8 ) & 0xff );
  }

  public void writeDWord( long a ) {
    data[offset] = ( byte ) ( ( a >> 24 ) & 0xff );
    data[++offset] = ( byte ) ( ( a >> 16 ) & 0xff );
    data[++offset] = ( byte ) ( ( a >> 8 ) & 0xff );
    data[++offset] = ( byte ) ( a & 0xff );
  }

  public void writeDWordLE( long a ) {
    data[offset] = ( byte ) ( a & 0xff );
    data[++offset] = ( byte ) ( ( a >> 8 ) & 0xff );
    data[++offset] = ( byte ) ( ( a >> 16 ) & 0xff );
    data[++offset] = ( byte ) ( ( a >> 24 ) & 0xff );
  }
  
  public byte[] getData() {
    return data;
  }

  @Override
  public void write( int i ) throws IOException {
    writeWord( i );
  }
}
