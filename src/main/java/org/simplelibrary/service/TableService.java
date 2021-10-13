package org.simplelibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.simplelibrary.util.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Query;

/**
* Experimental JPQL service class for querying and updating tables.
*/
@Slf4j
@Service
public class TableService extends DatabaseConnection {

  //////////////////
  // Table fields //
  //////////////////

  private String[] filters = null;
  private String[] sortColumns = null;
  private String order = null;
  private Integer limit= null;
  private Integer offset = null;

  //////////////////////////////////
  // Standard getters and setters //
  //////////////////////////////////

  /**
   * Get all filters.
   *
   * @return the filters
   */
  public String[] getFilters() {
    return filters;
  }

  /**
   * Set the filter.
   *
   * @param filter the filter to save
   */
  public void setFilter(String filter) {
    this.filters = new String[] {filter};
  }

  /**
   * Set the filters.
   *
   * @param filters the filters to save
   */
  public void setFilters(String[] filters) {
    this.filters = filters;
  }

  /**
   * Get all sort columns.
   *
   * @return the sort columns
   */
  public String[] getSortColumns() {
    return sortColumns;
  }

  /**
   * Set the sort column.
   *
   * @param sortColumn the sort column to save
   */
  public void setSortColumn(String sortColumns) {
    this.sortColumns = new String[] {sortColumns};
  }

  /**
   * Set the sort columns.
   *
   * @param sortColumns the sort columns to save
   */
  public void setSortColumns(String[] sortColumns) {
    this.sortColumns = sortColumns;
  }

  /**
   * Get the order.
   *
   * @return the order
   */
  public String getOrder() {
    return order;
  }

  /**
   * Set the order.
   *
   * @param order the order to save
   */
  public void setOrder(String order) {
    this.order = order;
  }

  /**
   * Get the order.
   *
   * @return the order
   */
  public Integer getOffset() {
    return offset;
  }

  /**
   * Set the offset.
   *
   * @param offset the offset to save
   */
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  /**
   * Get the limit.
   *
   * @return the limit
   */
  public Integer getLimit() {
    return limit;
  }

  /**
   * Set the limit.
   *
   * @param limit the limit to save
   */
  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  ////////////////////////
  // SQL clause methods //
  ////////////////////////

  /**
   * Get the JPQL WHERE clause.
   *
   * @return the WHERE clause
   */
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

  /**
   * Get the JPQL SORT clause.
   *
   * @return the SORT clause
   */
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

  /**
   * Get the JPQL ORDER clause.
   *
   * @return the ORDER clause
   */
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

  /**
   * Get the JPQL LIMIT clause.
   *
   * @return the LIMIT clause
   */
  private String getLimitClause() {
    String sql = "";

    if (limit != null) {
      sql += "LIMIT " + limit;
    }

    return sql;
  }

  /**
   * Get the JPQL OFFSET clause.
   *
   * @return the OFFSET clause
   */
  private String getOffsetClause() {
    String sql = "";

    if (offset != null) {
      sql += "OFFSET " + offset;
    }

    return sql;
  }

  /**
   * Get all JPQL clauses.
   *
   * @return the clauses
   */
  private String getAllClauses() {
    return String.format("%s %s %s %s %s", getWhereClause(),
                         getSortClause(), getOrderClause(),
                         getLimitClause(), getOffsetClause());
  }

  ///////////////////
  // Query methods //
  ///////////////////

  /**
   * Get the JPQL SELECT query.
   *
   * @param sql the SQL query
   * @return the SELECT query
   */
  private Query getSelectQuery(String sql) {
    return getSelectQuery(sql, null);
  }

  /**
   * Get the JPQL SELECT query.
   *
   * @param sql the SQL query
   * @param tableClass the JPA entity class
   * @return the SELECT query
   */
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

  ////////////////////
  // Select methods //
  ////////////////////

  /**
   * Selects a single row from a table.
   *
   * @param tableClass the JPA entity to select from
   * @return the object selected
   */
  public <T> T selectOne(Class<T> tableClass) {
    List<T> results = select(tableClass);

    if (results.isEmpty()) {
      return null;
    }
    else {
      return results.get(0);
    }
  }

  /**
   * Selects rows from a table.
   *
   * @param tableClass the JPA entity to select from
   * @return the list of the objects selected
   */
  public <T> List<T> select(Class<T> tableClass) {
    String sql = String.format("SELECT * FROM %s t %s", tableClass.getSimpleName().toLowerCase(), getAllClauses());
    log.debug(sql);
    this.connect();
    Query query = getSelectQuery(sql, tableClass);
    List<T> results = query.getResultList();
    this.disconnect();
    return results;
  }

  ////////////////////
  // Insert methods //
  ////////////////////

  /**
   * Inserts a single row to a table.
   *
   * @param entity the JPA entity object to insert
   * @return the number of rows inserted
   */
  public <T> int insert(T entity) {
    List<T> entities = new ArrayList<>();
    entities.add(entity);
    return insert(entities);
  }

  /**
   * Inserts multiple rows to a table.
   *
   * @param entities the JPA entity entities to insert
   * @return the number of rows inserted
   */
  public <T> int insert(List<T> entities) {
    this.begin();

    for (T entity : entities) {
      this.persist(entity);
    }

    this.commit();
    return entities.size();
  }

  ////////////////////
  // Update methods //
  ////////////////////

  /**
   * Updates a single column in a table.
   *
   * @param tableClass the JPA entity entity table to update
   * @param column the column to update
   * @param value the value to save in the update
   * @return the number of rows updated
   */
  public <T> int update(Class<T> tableClass, String column, String value) {
    return update(tableClass, new String[] {column}, new String[] {value});
  }

  /**
   * Updates multiple columns in a table.
   *
   * @param tableClass the JPA entity entity table to update
   * @param column the column to update
   * @param value the value to save in the update
   * @return the number of rows updated
   */
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

  ////////////////////
  // Delete methods //
  ////////////////////

  /**
   * Deletes rows from a table.
   *
   * @param tableClass the JPA entity entity table to delete from
   * @return the number of rows deleted
   */
  public <T> int delete(Class<T> tableName) {
    String sql = String.format("DELETE FROM %s t %s", tableName.getSimpleName().toLowerCase(), getWhereClause());
    this.begin();
    Query query = getSelectQuery(sql);
    int result = query.executeUpdate();
    this.commit();
    return result;
  }

  ////////////////////
  // Filter methods //
  ////////////////////

  /**
   * Returns a table service with an updated filter.
   *
   * @param filter the filter to save
   * @return the updated table service
   */
  public TableService filterBy(String filter) {
    return filterBy(new String[] {filter});
  }

  /**
   * Returns a table service with updated filters.
   *
   * @param filters the filters to save
   * @return the updated table service
   */
  public TableService filterBy(String[] filters) {
    this.setFilters(filters);
    return this;
  }

  //////////////////
  // Sort methods //
  //////////////////

  /**
   * Returns a table service with an updated sort column.
   *
   * @param sortColumn the sort column to save
   * @return the updated table service
   */
  public TableService sortBy(String sortColumn) {
    return sortBy(new String[] {sortColumn});
  }

  /**
   * Returns a table service with updated sort columns.
   *
   * @param sortColumns the sort columns to save
   * @return the updated table service
   */
  public TableService sortBy(String[] sortColumns) {
    this.setSortColumns(sortColumns);
    return this;
  }

  ///////////////////
  // Order methods //
  ///////////////////

  /**
   * Returns a table service with an updated order.
   *
   * @param order the order to save
   * @return the updated table service
   */
  public TableService inOrder(String order) {
    order = Pattern.matches("(?i)(-|d|desc|descending)", order) ? "DESC" : "ASC";
    this.setOrder(order);
    return this;
  }

  ///////////////////
  // Range methods //
  ///////////////////

  /**
   * Returns a table service with an updated limit.
   *
   * @param limit the limit to save
   * @return the updated table service
   */
  public TableService limitTo(Integer limit) {
    return inRange(0, limit - 1);
  }

  /**
   * Returns a table service with an updated range.
   *
   * @param range the range to save
   * @return the updated table service
   */
  public TableService inRange(Integer start, Integer end) {
    this.setLimit(end - start + 1);
    this.setOffset(start);
    return this;
  }

}
