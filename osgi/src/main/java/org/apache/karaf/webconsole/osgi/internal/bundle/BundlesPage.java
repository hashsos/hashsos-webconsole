package org.apache.karaf.webconsole.osgi.internal.bundle;

import static org.apache.wicket.model.Model.of;

import java.util.ArrayList;
import java.util.List;

import org.apache.karaf.webconsole.core.table.PropertyColumnExt;
import org.apache.karaf.webconsole.osgi.bundle.IActionProvider;
import org.apache.karaf.webconsole.osgi.bundle.IColumnProvider;
import org.apache.karaf.webconsole.osgi.bundle.IDecorationProvider;
import org.apache.karaf.webconsole.osgi.internal.OsgiPage;
import org.apache.karaf.webconsole.osgi.internal.bundle.view.BundlesDataTable;
import org.apache.karaf.webconsole.osgi.internal.bundle.view.DecorationPanel;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.ops4j.pax.wicket.api.PaxWicketBean;
import org.ops4j.pax.wicket.api.PaxWicketMountPoint;
import org.osgi.framework.Bundle;
import org.osgi.service.startlevel.StartLevel;

@PaxWicketMountPoint(mountPoint = "/osgi/bundles")
public class BundlesPage extends OsgiPage {

    @PaxWicketBean(name = "columnProviders")
    private List<IColumnProvider> columnProviders;

    @PaxWicketBean(name = "actionProviders")
    private List<IActionProvider> actionProviders;

    @PaxWicketBean(name = "decorationProviders")
    private List<IDecorationProvider> decorationProviders;

    @PaxWicketBean(name = "startLevel")
    private StartLevel startLevel;

    public BundlesPage() {
        List<IColumn<Bundle>> columns = new ArrayList<IColumn<Bundle>>();
        columns.add(new AbstractColumn<Bundle>(of("")) {
            public void populateItem(Item<ICellPopulator<Bundle>> cellItem, final String componentId, final IModel<Bundle> rowModel) {
                cellItem.add(new DecorationPanel(componentId, rowModel, decorationProviders));
            }
        });
        columns.add(new PropertyColumnExt<Bundle>("Bundle Id", "bundleId"));
        columns.add(new AbstractColumn<Bundle>(of("Start level")) {
            public void populateItem(Item<ICellPopulator<Bundle>> cellItem, final String componentId, final IModel<Bundle> rowModel) {
                cellItem.add(new Label(componentId, of(startLevel.getBundleStartLevel(rowModel.getObject()))));
            }
            
        });

        for (IColumnProvider provider : columnProviders) {
            columns.add(provider.getColumn());
        }

        columns.add(new PropertyColumnExt<Bundle>("Name", "symbolicName"));
        columns.add(new PropertyColumnExt<Bundle>("Version", "version.toString"));
//        columns.add(new AbstractColumn<Bundle>(Model.of("Operations")) {
//            public void populateItem(Item<ICellPopulator<Bundle>> cellItem, final String componentId, final IModel<Bundle> rowModel) {
//                cellItem.add(new BundleActionsPanel(componentId, rowModel, actionProviders));
//            }
//        });

        add(new BundlesDataTable("bundles", columns, new BundlesDataProvider(context), 100));
    }

}
