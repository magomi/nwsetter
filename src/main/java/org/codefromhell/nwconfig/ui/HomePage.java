package org.codefromhell.nwconfig.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.codefromhell.nwconfig.ui.config.NwConfig;
import org.codefromhell.nwconfig.ui.config.dhcp.DhcpConfigPanel;
import org.codefromhell.nwconfig.ui.config.parser.NwConfigParseException;
import org.codefromhell.nwconfig.ui.config.parser.NwConfigParser;
import org.codefromhell.nwconfig.ui.config.stat.StaticConfigPanel;

import java.util.List;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    private String type = "dhcp";

    private String nwConfigBasePath = "/Users/mgt/Workspace/cc2/dev/nwsetter/test";

    private List<NwConfig> nwConfigList;

    public HomePage(final PageParameters parameters) throws NwConfigParseException {
        super(parameters);

        nwConfigList = NwConfigParser.getConfigs(nwConfigBasePath);

        final Form form = new Form("form");
        add(form);

        final WebMarkupContainer configPanelWmc = new WebMarkupContainer("configPanelWmc");
        configPanelWmc.setOutputMarkupId(true);
        form.add(configPanelWmc);

        RadioGroup<HomePage> typeRadioGroup = new RadioGroup<HomePage>("typeRadioGroup", new PropertyModel<HomePage>(this, "type"));
        typeRadioGroup.add(new Radio<String>("dhcpType", new Model<String>("dhcp")));
        typeRadioGroup.add(new Radio<String>("staticType", new Model<String>("static")));
        typeRadioGroup.add(new AjaxFormChoiceComponentUpdatingBehavior() {
            @Override
            protected void onUpdate(AjaxRequestTarget ajaxRequestTarget) {
                if (type.equalsIgnoreCase("dhcp")) {
                    configPanelWmc.addOrReplace(new DhcpConfigPanel("configPanel"));
                } else if (type.equalsIgnoreCase("static")) {
                    configPanelWmc.addOrReplace(new StaticConfigPanel("configPanel"));
                }
                ajaxRequestTarget.add(configPanelWmc);
            }
        });
        form.add(typeRadioGroup);

        if (type.equalsIgnoreCase("dhcp")) {
            configPanelWmc.add(new DhcpConfigPanel("configPanel"));
        } else if (type.equalsIgnoreCase("static")) {
            configPanelWmc.add(new StaticConfigPanel("configPanel"));
        }

        form.add(new AjaxButton("activateConfigButton") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

            }
        });
    }
}
