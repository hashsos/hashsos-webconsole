package org.apache.karaf.webconsole.osgi.internal.configuration.view;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.osgi.service.cm.Configuration;

public class ConfigurationstDataTable extends DefaultDataTable<Configuration> {

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static List COLUMNS = Arrays.asList(
        new PropertyColumn<Configuration>(Model.of("pid"), "pid"),
        new AbstractColumn<Configuration>(Model.of("operations")) {
            public void populateItem(Item<ICellPopulator<Configuration>> cellItem, String componentId, IModel<Configuration> model) {
                cellItem.add(new ConfigurationsActionPanel(componentId, model));
            }
        }
    );

    public ConfigurationstDataTable(String id, ISortableDataProvider<Configuration> dataProvider, int rowsPerPage) {
        super(id, COLUMNS, dataProvider, rowsPerPage);
    }

    @Override
    protected Item<Configuration> newRowItem(String id, int index,
            IModel<Configuration> model) {
        Configuration cfg = model.getObject();

        if (cfg.getFactoryPid() != null) {
            return new FactoryPidItem(id, index, model);
        }

        return super.newRowItem(id, index, model);
    }
}