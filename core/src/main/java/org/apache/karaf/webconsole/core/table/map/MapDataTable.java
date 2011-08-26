package org.apache.karaf.webconsole.core.table.map;

import java.util.Arrays;
import java.util.Map.Entry;

import org.apache.karaf.webconsole.core.table.OrdinalColumn;
import org.apache.karaf.webconsole.core.table.PropertyColumnExt;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.markup.repeater.data.IDataProvider;

public class MapDataTable<K, V> extends DefaultDataTable<Entry<K, V>> {

    public MapDataTable(String id, IColumn<Entry<K, V>>[] columns, ISortableDataProvider<Entry<K, V>> dataProvider, int rowsPerPage) {
        super(id, Arrays.asList(columns), dataProvider, rowsPerPage);
    }

    public MapDataTable(String id, ISortableDataProvider<Entry<K, V>> dataProvider, int rowsPerPage) {
        this(id, getDefaultColumns(dataProvider), dataProvider, rowsPerPage);
    }

    @SuppressWarnings("unchecked")
    static <K,V> IColumn<Entry<K, V>>[] getDefaultColumns(IDataProvider<Entry<K, V>> provider) {
        IColumn<Entry<K, V>>[] columns = new IColumn[] {
            new OrdinalColumn<Entry<K, V>>(),
            new PropertyColumnExt<Entry<K, V>>("Key", "key"),
            new PropertyColumnExt<Entry<K, V>>("Value", "value")
        };

        return columns;
    }
}