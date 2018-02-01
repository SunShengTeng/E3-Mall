package cn.sst.e3mall.common.Results;

import java.io.Serializable;
import java.util.List;

public class EasyUIDataGridResult implements Serializable{

	private long total;

	private List<?> rows;

	
	
	public EasyUIDataGridResult() {
		super();
	}

	public EasyUIDataGridResult(Integer total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}

	public EasyUIDataGridResult(Long total, List<?> rows) {
		this.total = total.intValue();
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
