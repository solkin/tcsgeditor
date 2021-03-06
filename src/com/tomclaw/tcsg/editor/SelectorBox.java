package com.tomclaw.tcsg.editor;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Solkin Igor Viktorovich, TomClaw Software, 2003-2013
 * http://www.tomclaw.com/
 *
 * @author Solkin
 */
public class SelectorBox extends JComboBox {

    private Selector selector;

    public SelectorBox(Selector selector) {
        super(selector.getArray());
        this.selector = selector;
        this.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                updateSelectedIndex();
            }
        });
        this.setSelectedIndex(selector.getSelectedIndex());
    }

    public Selector getSelector() {
        return selector;
    }

    private void updateSelectedIndex() {
        selector.setSelctedIndex(getSelectedIndex());
    }
}
