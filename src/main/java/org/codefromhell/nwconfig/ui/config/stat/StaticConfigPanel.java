package org.codefromhell.nwconfig.ui.config.stat;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

/**
 * TODO
 *
 * @author Grunert, Marco (magomi@gmail.com)
 */
public class StaticConfigPanel extends Panel {
    private String ip;
    private String nwMask;
    private String netAddress;
    private String broadcastAddress;
    private String nameServer;
    private String defaultGw;

    public StaticConfigPanel(String id) {
        super(id);

        Form form = new Form("form", new CompoundPropertyModel(this));
        add(form);
        form.add(new TextField<String>("ip"));
        form.add(new TextField<String>("nwMask"));
        form.add(new TextField<String>("netAddress"));
        form.add(new TextField<String>("broadcastAddress"));
        form.add(new TextField<String>("nameServer"));
        form.add(new TextField<String>("defaultGw"));

    }
}
