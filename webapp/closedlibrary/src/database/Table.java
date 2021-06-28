package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Table {
	
	// Fields
	
	private Connection connection = null;
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
	
	public Table(Connection connection, String tableName) {
		this.connection = connection;
		this.tableName = tableName;
	}
	
	public Table(Connection connection, String tableName, String primaryKey, String[] filters, String[] sorters,
			String order, Integer limit, Integer offset) {
		this.connection = connection;
		this.tableName = tableName;
		this.primaryKey = primaryKey;
		this.filters = filters;
		this.sorters = sorters;
		this.order = order;
		this.limit = limit;
		this.offset = offset;
	}

	// Standard getters and setters
	
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey() throws SQLException {
		String sql = String.format("""
				SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS
				WHERE TABLE_NAME = '%s' and COLUMN_KEY = 'PRI'
				""", tableName);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		result.next();
		primaryKey = result.getString(1);
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
	
	public String getSelect() throws SQLException {
		return getSelect("*");
	}
	
	public String getSelect(String...columns) throws SQLException {
		String column = String.join(", ", columns);
		String sql = String.format("SELECT %s FROM %s", column, tableName);
		
		if (filters != null) {
			sql += String.format(" WHERE %s", String.join(", ", filters));
		}
		
		if (sorters != null) {
			sql += String.format(" ORDER BY %s", String.join(", ", sorters));
		}
		
		if (order != null) {
			sql += " " + order;
		}
		
		if (offset != null) {
			sql += String.format(" LIMIT %S", limit);
		}
		
		if (limit != null) {
			sql += String.format(" OFFSET %s", offset);
		}
		
		return sql;
	}
	
	public ResultSet select() throws SQLException {
		return select("*");
	}
	
	public ResultSet select(String...columns) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(getSelect(columns));
	}
	
	// Insert methods
	
	public int insert(String column, String value) throws SQLException {
		return insert(new String[] {column}, new String[] {value});
	}
	
	public int insert(String[] columns, String[] values) throws SQLException {
		if (columns.length != values.length) {
			return 0;
		}
		
		String column = String.join(", ", columns);
		String value = String.join("', '", values);
		String sql = String.format("INSERT INTO %s ( %s ) VALUES ( '%s' )", tableName, column, value);
		Statement statement = connection.createStatement();
		return (int) statement.executeUpdate(sql);
	}
	
	// Update methods
	
	public int update(String column, String value) throws SQLException {
		return update(new String[] {column}, new String[] {value});
	}
	
	public int update(String[] columns, String[] values) throws SQLException {
		if (columns.length != values.length) {
			return 0;
		}
		
		if (primaryKey == null) {
			setPrimaryKey();
		}
		
		String columnsToValues = "";
		
		for (int i = 0; i < columns.length; i++) {
			columnsToValues += String.format("%s = '%s', ", columns[i], values[i]);
		}
		
		columnsToValues = columnsToValues.substring(0, columnsToValues.length() - 2);
		String sql = String.format("UPDATE %s SET %s WHERE %s IN ( %s )", tableName, columnsToValues, primaryKey, 
				getSelect(primaryKey));
		Statement statement = connection.createStatement();
		return (int) statement.executeUpdate(sql);
	}
	
	// Delete methods
	
	public int delete() throws SQLException {
		if (primaryKey == null) {
			setPrimaryKey();
		}
		
		String sql = String.format("DELETE FROM %s WHERE %s IN ( %s )", tableName, primaryKey, getSelect(primaryKey));
		Statement statement = connection.createStatement();
		return (int) statement.executeUpdate(sql);
	}
	
	// Filter methods
	
	public Table filter(String filter) {
		return filter(new String[] {filter});
	}
	
	public Table filter(String[] filters) {
		return new Table(connection, tableName, primaryKey, filters, sorters, order, limit, offset);
	}
	
	// Sort methods
	
	public Table sort(String sorter) {
		return sort(new String[] {sorter});
	}
	
	public Table sort(String[] sorters) {
		return new Table(connection, tableName, primaryKey, filters, sorters, order, limit, offset);
	}
	
	// Order methods
	
	public Table order(String order) {
		order = Pattern.matches("(?i)(-|d|desc|descending)", order) ? "DESC" : "ASC";
		return new Table(connection, tableName, primaryKey, filters, sorters, order, limit, offset);
	}
	
	// Range methods
	
	public Table limit(Integer limit) {
		return range(0, limit);
	}
	
	public Table range(Integer start, Integer end) {
		Integer limit = end - start + 1;
		Integer offset = start - 1;
		return new Table(connection, tableName, primaryKey, filters, sorters, order, limit, offset);
	}
	
}
