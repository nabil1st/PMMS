package trackit.backingbeans;

import java.util.List;

import trackit.util.FacesUtils;

public class StatementComparisonBean extends BaseBean {
	
	public List<StatementMatchItem> getComparisonModel() {
		return FacesUtils.getSessionBean().getStatementMatchModel();
	}

}
