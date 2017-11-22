package com.tomclaw.tcsg;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 *
 * @author Solkin
 */
public class Line extends Primitive {

    public int x1, y1, x2, y2;
    private int color;
    private boolean isProportional;
    private Fragment fragment;

    public Line(DataInputStream dis) throws IOException {
        read(dis);
    }

    public Line(int x1, int y1, int x2, int y2, int color, boolean isProportional, Fragment fragment) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
        this.isProportional = isProportional;
        this.fragment = fragment;
    }

    @Override
    public void paint(Graphics g) {
        ScaleGraphics.setColor(g, color);
        if (isProportional) {
            ScaleGraphics.drawLine(g, fragment.getAlignX(x1),
                    fragment.getAlignY(y1),
                    fragment.getAlignX(x2),
                    fragment.getAlignY(y2));
        } else {
            ScaleGraphics.drawLine(g, fragment.getAbsX(x1),
                    fragment.getAbsY(y1),
                    fragment.getSecAbsX(x2),
                    fragment.getSecAbsY(y2));
        }
    }

    @Override
    public void setSecLocation(int x, int y) {
        x2 = x;
        y2 = y;
    }

    @Override
    public void setFigure(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void setLocation(int x, int y) {
        this.x1 = x;
        this.y1 = y;
    }

    @Override
    public Gabarite getGabarite() {
        return new Gabarite(x1, y1, x2, y2);
    }

    @Override
    public Object[][] getFields() {
        return new Object[][]{
                {"Начальный X", Integer.valueOf(x1)},
                {"Начальный Y", Integer.valueOf(y1)},
                {"Конецный X", Integer.valueOf(x2)},
                {"Конечный Y", Integer.valueOf(y2)},
                {"Цвет", new Color(color)},
                {"Пропорциональность", Boolean.valueOf(isProportional)}
        };
    }

    @Override
    public void setFields(Object[][] fields) {
        x1 = (Integer) fields[0][1];
        y1 = (Integer) fields[1][1];
        x2 = (Integer) fields[2][1];
        y2 = (Integer) fields[3][1];
        color = ((Color) fields[4][1]).getRGB();
        isProportional = (Boolean) fields[5][1];
    }

    @Override
    public int getType() {
        return Primitive.TYPE_LINE;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeShort(x1);
        dos.writeShort(y1);
        dos.writeShort(x2);
        dos.writeShort(y2);
        dos.writeInt(color);
        dos.writeBoolean(isProportional);
    }

    @Override
    public final void read(DataInputStream dis) throws IOException {
        x1 = dis.readShort();
        y1 = dis.readShort();
        x2 = dis.readShort();
        y2 = dis.readShort();
        color = dis.readInt();
        isProportional = dis.readBoolean();
    }
}
