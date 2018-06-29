package com.otus.homework9;

import java.sql.ResultSet;

public interface RowMapper<T> {
  T mapRow(ResultSet rs);
}
