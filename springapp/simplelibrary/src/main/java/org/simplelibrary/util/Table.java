package org.simplelibrary.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Query;

// Hasn't yet been thoroughly tested; errors may occur
public class Table<T> extends DatabaseConnection {
	
    // Table fields
	
	private Class<T> tableClass;
	private String tableName = null;
	private String primaryKey = null;
	private String[] filters = null;
	private String[] sorters = null;
	private String order = null;
	private Integer limit= null;
	private Integer offset = null;
	
	// Constructors
	
	public Table() {
	}
	
	public Table(Class<T> tableClass) {
		this.tableClass = tableClass;
	}
	
	public Table(Class<T> tableClass, String primaryKey, String[] filters, 
			String[] sorters, String order, Integer limit, Integer offset) {
		this.tableClass = tableClass;
		this.primaryKey = primaryKey;
		this.filters = filters;
		this.sorters = sorters;
		this.order = order;
		this.limit = limit;
		this.offset = offset;
	}

	// Standard getters and setters

	public Class<T> getTableClass() {
		return tableClass;
	}

	public void setTableClass(Class<T> tableClass) {
		this.tableClass = tableClass;
	}
	
	public String getTableName() {
		if (tableName == null) {
			tableName = tableClass.getSimpleName();
		}
		
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPrimaryKey() {
		if (primaryKey == null) {
			String sql = String.format(
					"SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS" +
					"WHERE TABLE_NAME = '%s' and COLUMN_KEY = 'PRI'",
					tableName);
			
			try {
				this.connect();
				Statement statement = connection.createStatement();
				this.disconnect();
				ResultSet results = statement.executeQuery(sql);
				results.next();
				primaryKey = results.getString(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String[] getFilters() {
		return filters;
	}

	public void setFilter(String filter) {
		this.filters = new String[] {filter};
	}

	public void setFilters(String[] filters) {
		this.filters = filters;
	}

	public String[] getSorters() {
		return sorters;
	}

	public void setSorter(String sorter) {
		this.sorters = new String[] {sorter};
	}

	public void setSorters(String[] sorters) {
		this.sorters = sorters;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setRange(Integer start, Integer end) {
		this.limit = end - start + 1;
		this.offset = start - 1;
	}
	
	// Select methods
	
	public List<T> select() {
		return select("*");
	}
	
	public List<T> select(String...columns) {
//		String sql = String.format("SELECT :columns FROM %s t", getTableName());
//		
//		if (filters != null) {
//			sql += " WHERE t.:filters";
//		}
//		
//		if (sorters != null) {
//			sql += " ORDER BY t.:sorters";
//		}
//		
//		if (order != null) {
//			sql += " :order";
//		}
//		
//		if (offset != null) {
//			sql += " OFFSET :offset";
//		}
//		
//		if (limit != null) {
//			sql += " LIMIT :limit";
//		}
		
		// TODO - Fix this crap!!!!
		this.connect();
		String sql = String.format("SELECT * FROM %s", getTableName());
		Query query = entityManager.createNativeQuery(sql, tableClass);
		
//		query.setParameter("columns", String.join(", t.", columns));
//		
//		if (filters != null) {
//			query.setParameter("filters", String.join(", t.", filters));
//		}
//		
//		if (sorters != null) {
//			query.setParameter("sorters", String.join(", t.", sorters));
//		}
//		
//		if (order != null) {
//			query.setParameter("order", order);
//		}
//		
//		if (offset != null) {
//			query.setParameter("offset", offset);
//		}
//		
//		if (limit != null) {
//			query.setParameter("limit", limit);
//		}
		
		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();
		this.disconnect();
		return results;
	}
	
	// Insert methods
	
	public int insert(String column, String value) {
		return insert(new String[] {column}, new String[] {value});
	}
	
	public int insert(String[] columns, String[] values) {
		if (columns.length != values.length) {
			return 0;
		}
		
		String sql = "INSERT INTO ? ( ? ) VALUES ( '?' )";
		
		try {
			this.connect();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(0, getTableName());
			preparedStatement.setString(1, String.join(", ", columns));
			preparedStatement.setString(2, String.join("', '", values));
			int result = preparedStatement.executeUpdate();
			this.disconnect();
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	// Update methods
	
	public int update(String column, String value) {
		return update(new String[] {column}, new String[] {value});
	}
	
	public int update(String[] columns, String[] values) {
		if (columns.length != values.length) {
			return 0;
		}
		
		String columnsToValues = "";
		
		for (int i = 0; i < columns.length; i++) {
			columnsToValues += String.format("t.%s = '%s', ", columns[i], values[i]);
		}
		
		columnsToValues = columnsToValues.substring(0, columnsToValues.length() - 2);
		this.connect();
		String sql = "UPDATE :table t SET :columnsToValues WHERE t.:primaryKey IN ( :selectPrimaryKeys )";
		String selectPrimaryKey = String.format("SELECT keyt.%s FROM %s keyt", getPrimaryKey(), getTableName());
		Query query = entityManager.createQuery(sql);
		query.setParameter("table", getTableName());
		query.setParameter("columnsToValues", columnsToValues);
		query.setParameter("primaryKey", getPrimaryKey());
		query.setParameter("selectPrimaryKey", selectPrimaryKey);
		int result = query.executeUpdate();
		this.disconnect();
		return result;
	}
	
	// Delete methods
	
	public int delete() {
		this.connect();
		String sql = "DELETE FROM :table t WHERE t.:primaryKey IN :selectPrimaryKeys";
		String selectPrimaryKey = String.format("SELECT keyt.%s FROM %s keyt", getPrimaryKey(), getTableName());
		Query query = entityManager.createQuery(sql);
		query.setParameter("table", getTableName());
		query.setParameter("primaryKey", getPrimaryKey());
		query.setParameter("selectPrimaryKey", selectPrimaryKey);
		int result = query.executeUpdate();
		this.disconnect();
		return result;
	}
	
	// Filter methods
	
	public Table<T> filter(String filter) {
		return filter(new String[] {filter});
	}
	
	public Table<T> filter(String[] filters) {
		return new Table<T>(tableClass, primaryKey, filters, sorters, order, limit, offset);
	}
	
	// Sort methods
	
	public Table<T> sort(String sorter) {
		return sort(new String[] {sorter});
	}
	
	public Table<T> sort(String[] sorters) {
		return new Table<T>(tableClass, primaryKey, filters, sorters, order, limit, offset);
	}
	
	// Order methods
	
	public Table<T> order(String order) {
		order = Pattern.matches("(?i)(-|d|desc|descending)", order) ? "DESC" : "ASC";
		return new Table<T>(tableClass, primaryKey, filters, sorters, order, limit, offset);
	}
	
	// Range methods
	
	public Table<T> limit(Integer limit) {
		return range(0, limit);
	}
	
	public Table<T> range(Integer start, Integer end) {
		Integer limit = end - start + 1;
		Integer offset = start - 1;
		return new Table<T>(tableClass, primaryKey, filters, sorters, order, limit, offset);
	}
	
}
