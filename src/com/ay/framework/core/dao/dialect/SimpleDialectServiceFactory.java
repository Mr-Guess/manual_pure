package com.ay.framework.core.dao.dialect;

public class SimpleDialectServiceFactory {
	private IDialect dialect;

	public IDialect getDialect() {
		return dialect;
	}

	public void setDialect(IDialect dialect) {
		this.dialect = dialect;
	}
	
    /**
     * 根据不同的数据库得到不同的alter table sql
     * @return
     */
    public String alterTableColumn() {
    	return dialect.alterTableColumn();
    }
}
