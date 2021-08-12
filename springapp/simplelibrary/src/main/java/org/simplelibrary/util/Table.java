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
  private String[] sortColumns = null;
  private String order = null;
  private Integer limit= null;
  private Integer offset = null;

  // Constructors

  public Table() {
  }

  public Table(Class<T> tableClass) {
    this.tableClass = tableClass;
  }

  public Table(Class<T> tableClass, String tableName, String[] filters,
               String[] sortColumns, String order, Integer limit, Integer offset) {
    this.tableClass = tableClass;
    this.tableName = tableName;
    this.filters = filters;
    this.sortColumns = sortColumns;
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

  public String[] getFilters() {
    return filters;
  }

  public void setFilter(String filter) {
    this.filters = new String[] {filter};
  }

  public void setFilters(String[] filters) {
    this.filters = filters;
  }

  public String[] getSortColumns() {
    return sortColumns;
  }

  public void setSortColumn(String sortColumns) {
    this.sortColumns = new String[] {sortColumns};
  }

  public void setSortColumns(String[] sortColumns) {
    this.sortColumns = sortColumns;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String sort) {
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

  // SQL clause methods

  private String getWhereClause() {
    String sql = "";
    int paramCount = 0;

    if (filters != null) {
      sql += "WHERE ";

      for (int i = 0; i < filters.length; i++) {
        String filter = filters[i].toLowerCase();

        if (filter.contains("=")) {
          String column = filter.split("=")[0].trim();
          sql += String.format("t.%s = ?%s AND ", column, ++paramCount);
        }
        else if (filter.contains("contains")) {
          String column = filter.split("contains")[0].trim();
          sql += String.format("t.%s like ?%s AND ", column, ++paramCount);
        }
      }

      sql = sql.substring(0, sql.lastIndexOf(" AND"));
    }

    return sql;
  }

  private String getSortClause() {
    String sql = "";

    if (sortColumns != null) {
      List<String> acceptedSortColumns = new ArrayList<String>();

      for (String column : sortColumns) {
        if (Pattern.matches("[_a-zA-Z]+", column)) {
          acceptedSortColumns.add(column);
        }
      }

      if (acceptedSortColumns.size() > 0) {
        sql += "ORDER BY t.";

        for (String column : acceptedSortColumns) {
          if (Pattern.matches("[_a-zA-Z]+", column)) {
            sql += column + ", t.";
          }
        }

        sql = sql.substring(0, sql.lastIndexOf(","));
      }
    }

    return sql;
  }

  private String getOrderClause() {
    String sql = "";

    if (order != null) {
      if (Pattern.matches("(?i)(-|d|desc|descending)", order)) {
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
        getSortClause(), getOrderClause(),
        getLimitClause(), getOffsetClause());
    return sql;
  }

  // Query methods

  private Query getSelectQuery(String sql) {
    return getSelectQuery(sql, false);
  }

  private Query getSelectQuery(String sql, boolean isNative) {
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
        String filter = filters[i].toLowerCase();

        if (filter.contains("=")) {
          String value = filter.split("=")[1].trim();
          query.setParameter(++paramCount, value);
        }
        else if (filter.contains("contains")) {
          String value = "%" + filter.split("contains")[1].trim() + "%";
          query.setParameter(++paramCount, value);
        }
      }
    }

    return query;
  }

  // Select methods

  public T selectOne() {
    List<T> results = select();

    if (results.size() == 0) {
      return null;
    }
    else {
      return results.get(0);
    }
  }

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

  public Table<T> filterBy(String filter) {
    return filterBy(new String[] {filter});
  }

  public Table<T> filterBy(String[] filters) {
    return new Table<T>(tableClass, tableName, filters, sortColumns, order, limit, offset);
  }

  // Sort methods

  public Table<T> sortBy(String sortColumn) {
    return sortBy(new String[] {sortColumn});
  }

  public Table<T> sortBy(String[] sortColumns) {
    return new Table<T>(tableClass, tableName, filters, sortColumns, order, limit, offset);
  }

  // Order methods

  public Table<T> inOrder(String order) {
    order = Pattern.matches("(?i)(-|d|desc|descending)", order) ? "DESC" : "ASC";
    return new Table<T>(tableClass, tableName, filters, sortColumns, order, limit, offset);
  }

  // Range methods

  public Table<T> limitTo(Integer limit) {
    return fromRange(0, limit - 1);
  }

  public Table<T> fromRange(Integer start, Integer end) {
    Integer limit = end - start + 1;
    Integer offset = start;
    return new Table<T>(tableClass, tableName, filters, sortColumns, order, limit, offset);
  }

}
