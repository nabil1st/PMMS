package trackit.backingbeans;

import trackit.businessobjects.Entity;
import trackit.util.FacesUtils;

public abstract class GenericEntityBean extends BaseBean {
	
	private Long id;
	protected boolean editMode;
	
	public String createAction() {
		
		try {
			validate();
		} catch (Exception ex) {
			FacesUtils.addErrorMessage(ex.getMessage());
            return null;
		}
		
		String result = null;
		
		try {
			result = doCreateIt();
		} catch (Exception ex) {
			FacesUtils.addErrorMessage(ex.getMessage());
			return null;
		}
		
		editMode = false;
		resetListBean();
		
		return result;
	}
	
	private void resetListBean() {
		String listBeanName = getListBeanName();
		if (listBeanName != null) {
			ListBean eb = (ListBean) FacesUtils.getManagedBean(listBeanName);			
			if (eb != null) {
				eb.reset();
			}
		}
	}
	
	public abstract String getListBeanName();
	
	public String cancelAction() {
		String result = doCancel();
		this.editMode = false;
		return result;
	}
	
	public abstract String doCancel();
	
	public abstract String doCreateIt() throws Exception;
		
	public abstract void validate() throws Exception;
	
	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public void edit(Entity entity) {
		this.editMode = true;
		this.id = entity.getId();
		editIt(entity);
	}
	
	public abstract void editIt(Entity entity);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
