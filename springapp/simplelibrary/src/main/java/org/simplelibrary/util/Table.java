package org.simplelibrary.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Query;

// Experimental usage of JPQL for querying and updating tables
// May or may not be used in the final version
public class Table<T> extends DatabaseConnection {
  
  // Table fields
  
  private Class<T> tableClass = null;
  private String tableName = null;
  private String[] filters = null;
  private String[] orderByColumns = null;
  private String sort = null;
  private Integer limit= null;
  private Integer offset = null;
  
  // Constructors
  
  public Table() {
  }
  
  public Table(Class<T> tableClass) {
    this.tableClass = tableClass;
  }
  
  public Table(Class<T> tableClass, String[] filters, String[] orderByColumns,
         String sort, Integer limit, Integer offset) {
    this.tableClass = tableClass;
    this.filters = filters;
    this.orderByColumns = orderByColumns;
    this.sort = sort;
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

  public String[] getFilters() {
    return filters;
  }

  public void setFilter(String filter) {
    this.filters = new String[] {filter};
  }

  public void setFilters(String[] filters) {
    this.filters = filters;
  }

  public String[] getorderByColumns() {
    return orderByColumns;
  }

  public void setorderByColumn(String orderByColumn) {
    this.orderByColumns = new String[] {orderByColumn};
  }

  public void setorderByColumns(String[] orderByColumns) {
    this.orderByColumns = orderByColumns;
  }

  public String getsort() {
    return sort;
  }

  public void setsort(String sort) {
    this.sort = sort;
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
  
  // SQL clause methods

  private String getWhereClause() {
    String sql = "";
    int paramCount = 0;

    if (filters != null) {
      sql += "WHERE ";

      for (int i = 0; i < filters.length; i++) {
        String column = filters[i].split("=")[0];
        sql += String.format("t.%s = ?%s, ", column, ++paramCount);
      }

      sql = sql.substring(0, sql.lastIndexOf(","));
    }

    return sql;
  }

  private String getOrderByClause() {
    String sql = "";

    if (orderByColumns != null) {
      List<String> acceptedorderByColumns = new ArrayList<String>();

      for (String column : orderByColumns) {
        if (Pattern.matches("[a-zA-Z]+", column)) {
          acceptedorderByColumns.add(column);
        }
      }

      if (acceptedorderByColumns.size() > 0) {
        sql += "ORDER BY ";

        for (String column : acceptedorderByColumns) {
          if (Pattern.matches("[a-zA-Z]+", column)) {
            sql += column + ", ";
          }
        }

        sql = sql.substring(0, sql.lastIndexOf(","));
      }
    }

    return sql;
  }

  private String getSortClause() {
    String sql = "";

    if (sort != null) {
      if (Pattern.matches("[desc|descending|-](?i)", sort)) {
        sql += "DESC";
      }
      else {
        sql += "ASC";
      }
    }

    return sql;
  }

  private String getLimitClause() {
    String sql = "";

    if (limit != null) {
      sql += "LIMIT " + limit;
    }

    return sql;
  }

  private String getOffsetClause() {
    String sql = "";

    if (offset != null) {
      sql += "OFFSET " + offset;
    }

    return sql;
  }

  private String getAllClauses() {
    String sql = String.format("%s %s %s %s %s", getWhereClause(),
        getOrderByClause(), getSortClause(),
        getLimitClause(), getOffsetClause());
    return sql;
  }

  // Query methods
  
  private Query getSelectQuery(String sql) {
    return getSelectQuery(sql, false);
  }

  private Query getSelectQuery(String sql, boolean isNative) {
    System.out.println(sql);
    Query query = null;
    int paramCount = 0;

    if (isNative == true) {
      query = entityManager.createNativeQuery(sql, tableClass);
    }
    else {
      query = entityManager.createQuery(sql);
    }

    if (filters != null) {
      for (int i = 0; i < filters.length; i++) {
        String value = filters[i].split("=")[1];
        query.setParameter(++paramCount, value);
      }
    }

    return query;
  }

  // Select methods

  public List<T> select() {
    String sql = String.format("SELECT * FROM %s t %s", getTableName(), getAllClauses());
    this.connect();
    Query query = getSelectQuery(sql, true);
    List<T> results = query.getResultList();
    this.disconnect();
    return results;
  }
  
  // Insert methods
  
  public int insert(T entity) {
    List<T> entities = new ArrayList<T>();
    entities.add(entity);
    return insert(entities);
  }
  
  public int insert(List<T> entities) {
    this.begin();

    for (T entity : entities) {
      this.persist(entity);
    }

    this.commit();
    return entities.size();
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
    int paramCount = 1000;

    for (String column : columns) {
      columnsToValues += String.format("t.%s = ?%s, ", column, ++paramCount);
    }

    columnsToValues = columnsToValues.substring(0, columnsToValues.lastIndexOf(","));
    paramCount = 1000;
    String sql = String.format("UPDATE %s t SET %s %s", getTableName(), columnsToValues, getWhereClause());
    this.begin();
    Query query = getSelectQuery(sql);

    for (String value : values) {
      query.setParameter(++paramCount, value);
    }

    int result = query.executeUpdate();
    this.commit();
    return result;
  }
  
  // Delete methods
  
  public int delete() {
    String sql = String.format("DELETE FROM %s t %s", getTableName(), getWhereClause());
    this.begin();
    Query query = getSelectQuery(sql);
    int result = query.executeUpdate();
    this.commit();
    return result;
  }
  
  // Filter methods
  
  public Table<T> filter(String filter) {
    return filter(new String[] {filter});
  }
  
  public Table<T> filter(String[] filters) {
    return new Table<T>(tableClass, filters, orderByColumns, sort, limit, offset);
  }
  
  // Sort methods
  
  public Table<T> orderBy(String orderByColumn) {
    return orderBy(new String[] {orderByColumn});
  }
  
  public Table<T> orderBy(String[] orderByColumns) {
    return new Table<T>(tableClass, filters, orderByColumns, sort, limit, offset);
  }

  // Order methods

  public Table<T> sort(String sort) {
    sort = Pattern.matches("(?i)(-|d|desc|descending)", sort) ? "DESC" : "ASC";
    return new Table<T>(tableClass, filters, orderByColumns, sort, limit, offset);
  }

  // Range methods

  public Table<T> limit(Integer limit) {
    return range(0, limit - 1);
  }

  public Table<T> range(Integer start, Integer end) {
    Integer limit = end - start + 1;
    Integer offset = start;
    return new Table<T>(tableClass, filters, orderByColumns, sort, limit, offset);
  }

}
