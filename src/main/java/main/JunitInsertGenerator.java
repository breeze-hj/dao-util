package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JunitInsertGenerator {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws SQLException {
		String tableName = "course";

		Connection connection = DBUtil.getConnection(DBUtil.url, DBUtil.user, DBUtil.password);
		ResultSet rs = DBUtil.checkTable(connection, tableName);

		List<String> fields = fields(rs);
		Statement statement = connection.createStatement();
		String sql = "select " + selectFieldSQL(fields) + " from " + tableName + " limit 1";
		statement.execute(sql);
		rs = statement.getResultSet();
		List<Object> values = insertFieldSQL(rs, fields);

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ").append(tableName).append("(");

		for (String field : fields) {
			sb.append(field).append(",");
		}
		sb.setLength(sb.length() - 1);
		
		sb.append(")\r\n").append("VALUES").append("(");
		for (Object value : values) {
			sb.append(getValue(value)).append(",");
		}
		sb.setLength(sb.length() - 1);
		sb.append(")");
		System.out.println(sb.toString());
	}

	public static Object getValue(Object obj) {
		if (obj != null && obj instanceof Date) {
			obj = sdf.format(obj);
		}
		if (obj instanceof String) {
			return "'" + obj + "'";
		}
		return obj;
	}

	public static List<String> fields(ResultSet rs) throws SQLException {
		List<String> fields = new ArrayList<String>();
		rs.beforeFirst();
		while (rs.next()) {
			fields.add(rs.getString(DBConstants.COLUMN_NAME));
		}
		return fields;
	}

	public static String selectFieldSQL(List<String> fields) {
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(field).append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public static List<Object> insertFieldSQL(ResultSet rs, List<String> fields) throws SQLException {
		rs.beforeFirst();
		rs.next();
		List<Object> values = new ArrayList<Object>();
		for (String field : fields) {
			values.add(rs.getObject(field));
		}
		return values;
	}
}
