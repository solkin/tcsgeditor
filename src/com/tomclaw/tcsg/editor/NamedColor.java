package com.tomclaw.tcsg.editor;

import java.awt.*;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 *
 * @author Solkin
 */
public class NamedColor extends Color {

    private String name;

    public NamedColor(int color, String name) {
        super(color);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }
}
