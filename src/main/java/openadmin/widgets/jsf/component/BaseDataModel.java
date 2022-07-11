package openadmin.widgets.jsf.component;

import java.util.List;

import org.primefaces.model.SelectableDataModel;

import jakarta.faces.model.ListDataModel;
import openDao.model.Base;

/**
 *
 * @author avbravo
 */
public class BaseDataModel extends ListDataModel<Base> implements SelectableDataModel<Base>{

    public BaseDataModel() {
    }
    public BaseDataModel(List<Base>data) {
        super(data);
    }

    @Override
    public Base getRowData(String rowKey) {
        List<Base> baseList = (List<Base>) getWrappedData();
        for (Base base : baseList) {
             if (base.getId().equals(rowKey)) {
                 return base;
             }
        }
        return null;
     }
    
     @Override
     public String getRowKey(Base base) {
         return (String) base.getId();
     }



}