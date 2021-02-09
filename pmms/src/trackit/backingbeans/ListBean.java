package trackit.backingbeans;

import trackit.service.impl.DataInUseException;
import trackit.util.FacesUtils;


public abstract class ListBean extends BaseBean {
	
	private int scrollerPage;
	private IListItem selectedListItem;
	
	public int getScrollerPage() {
        return scrollerPage;
    }

    public void setScrollerPage(int scrollerPage) {
        this.scrollerPage = scrollerPage;
    }
    
    public String deleteIt() {
		if (selectedListItem != null) {
			try {
				deleteItem(selectedListItem);
			} catch (DataInUseException ex) {
				FacesUtils.addErrorMessage(ex.getMessage());
				return "";
			}
			selectedListItem = null;
		}
		
		reset();
		
		return getListNavigaionId();
      
    }
    
    public abstract String getListNavigaionId();
    
    public abstract void deleteItem(IListItem item) throws DataInUseException;
	
	public String editIt() {		
		if (selectedListItem != null) {
			
			editItem(selectedListItem);
			
			selectedListItem = null;
			
			return getEditNavigationId();
		}
		
		return getListNavigaionId();
    
	}
	
	public abstract String getEditNavigationId();

	public abstract void editItem(IListItem item);

	public IListItem getSelectedListItem() {
		return selectedListItem;
	}

	public void setSelectedListItem(IListItem item) {
		this.selectedListItem = item;
	}
	
	public abstract void reset();
}
