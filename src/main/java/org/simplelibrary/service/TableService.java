package org.simplelibrary.service;

import org.simplelibrary.util.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Query;

// Experimental usage of JPQL for querying and updating tables.
// Will only be used for the search page, where repositories may be inefficient.
@Service
public class TableService extends DatabaseConnection {

  // Table fields

  private String[] filters = null;
  private String[] sortColumns = null;
  private String order = null;
  private Integer limit= null;
  private Integer offset = null;

  // Standard getters and setters

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

  // SQL clause methods

  private String getWhereClause() {
    String sql = "";
    int paramCount = 0;

    if (filters != null) {
      sql += "WHERE ";

      for (String filter : filters) {
        filter = filter.toLowerCase();

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

      if (!acceptedSortColumns.isEmpty()) {
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
    return String.format("%s %s %s %s %s", getWhereClause(),
                         getSortClause(), getOrderClause(),
                         getLimitClause(), getOffsetClause());
  }

  // Query methods

  private Query getSelectQuery(String sql) {
    return getSelectQuery(sql, null);
  }

  private Query getSelectQuery(String sql, Class<?> tableClass) {
    Query query = null;
    int paramCount = 0;

    if (tableClass != null) {
      query = entityManager.createNativeQuery(sql, tableClass);
    }
    else {
      query = entityManager.createQuery(sql);
    }

    if (filters != null) {
      for (String filter : filters) {
        filter = filter.toLowerCase();

        if (filter.contains("=")) {
          String value = filter.split("=")[1].trim();
          query.setParameter(++paramCount, value);
        } else if (filter.contains("contains")) {
          String value = "%" + filter.split("contains")[1].trim() + "%";
          query.setParameter(++paramCount, value);
        }
      }
    }

    return query;
  }

  // Select methods

  public <T> T selectOne(Class<T> tableClass) {
    List<T> results = select(tableClass);

    if (results.isEmpty()) {
      return null;
    }
    else {
      return results.get(0);
    }
  }

  public <T> List<T> select(Class<T> tableClass) {
    String sql = String.format("SELECT * FROM %s t %s", tableClass.getSimpleName().toLowerCase(), getAllClauses());
    this.connect();
    Query query = getSelectQuery(sql, tableClass);
    List<T> results = query.getResultList();
    this.disconnect();
    return results;
  }

  // Insert methods

  public <T> int insert(T entity) {
    List<T> entities = new ArrayList<>();
    entities.add(entity);
    return insert(entities);
  }

  public <T> int insert(List<T> entities) {
    this.begin();

    for (T entity : entities) {
      this.persist(entity);
    }

    this.commit();
    return entities.size();
  }

  // Update methods

  public <T> int update(Class<T> tableClass, String column, String value) {
    return update(tableClass, new String[] {column}, new String[] {value});
  }

  public <T> int update(Class<T> tableClass, String[] columns, String[] values) {
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
    String sql = String.format("UPDATE %s t SET %s %s", tableClass.getSimpleName().toLowerCase(), columnsToValues, getWhereClause());
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

  public <T> int delete(Class<T> tableName) {
    String sql = String.format("DELETE FROM %s t %s", tableName.getSimpleName().toLowerCase(), getWhereClause());
    this.begin();
    Query query = getSelectQuery(sql);
    int result = query.executeUpdate();
    this.commit();
    return result;
  }

  // Filter methods

  public TableService filterBy(String filter) {
    return filterBy(new String[] {filter});
  }

  public TableService filterBy(String[] filters) {
    this.setFilters(filters);
    return this;
  }

  // Sort methods

  public TableService sortBy(String sortColumn) {
    return sortBy(new String[] {sortColumn});
  }

  public TableService sortBy(String[] sortColumns) {
    this.setSortColumns(sortColumns);
    return this;
  }

  // Order methods

  public TableService inOrder(String order) {
    order = Pattern.matches("(?i)(-|d|desc|descending)", order) ? "DESC" : "ASC";
    this.setOrder(order);
    return this;
  }

  // Range methods

  public TableService limitTo(Integer limit) {
    return inRange(0, limit - 1);
  }

  public TableService inRange(Integer start, Integer end) {
    this.setLimit(end - start + 1);
    this.setOffset(start);
    return this;
  }

}
