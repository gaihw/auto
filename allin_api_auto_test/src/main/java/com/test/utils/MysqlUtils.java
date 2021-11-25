package com.test.utils;

import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlUtils {
	public YamlUtils instance = new YamlUtils();
	public static void main(String[] args) {
//		System.out.println(new MysqlUtils().select("SELECT * FROM `allin_test`.`test_date`  LIMIT 0,1000"));
		System.out.println(new MysqlUtils().insert("INSERT INTO `allin_test`.`test_date` (`year`, `month`, `day`) VALUES (2008, 4, 4)"));
	}

	String url = "";
	String user = "";
	String pass = "";

	public MysqlUtils(String url,String user,String password){
		this.url = url;
		this.user = user;
		this.pass = password;
	}

	public MysqlUtils(){
		url = String.valueOf(instance.getValueByKey("base.database.jdbc-url"));
		user = String.valueOf(instance.getValueByKey("base.database.username"));
		pass = String.valueOf(instance.getValueByKey("base.database.password"));
	}

	/**
	 * 查询语句，返回List查询结果
	 * @param sql
	 * @return
	 */
	public List select(String sql){

		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 注册驱动
		try {
			Class.forName(String.valueOf(instance.getValueByKey("base.database.driver-class-name")));
			conn = (Connection) DriverManager.getConnection(url,user,pass);
			stat = (Statement) conn.createStatement();
			rs = stat.executeQuery(sql);
			//查询结果，转化成list集合
			List select_res = convertList(rs);

			//完成后，关闭
			rs.close();
			stat.close();
			conn.close();

			return select_res;
			// 输出查询结果
//			while(rs.next()){
//				return rs.getString("year");
//			}

		} catch(SQLException se){
			// 处理 JDBC 错误
			se.printStackTrace();
		}catch(Exception e){
			// 处理 Class.forName 错误
			e.printStackTrace();
		}finally{
			// 关闭资源
			try{
				if(stat!=null) {
					stat.close();
				}
			}catch(SQLException se2){
			}// 什么都不做
			try{
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 传入更新语句后，然后执行结果
	 * @param sql
	 * @return
	 */
	public int update(String sql){

		Connection conn = null;
		Statement stat = null;
		// 注册驱动
		try {
			Class.forName(String.valueOf(instance.getValueByKey("base.database.driver-class-name")));
			conn = (Connection) DriverManager.getConnection(url,user,pass);
			stat = (Statement) conn.createStatement();
			int update_res =  stat.executeUpdate(sql);

			//完成后，关闭
			stat.close();
			conn.close();
			return update_res;
		} catch(SQLException se){
			// 处理 JDBC 错误
			se.printStackTrace();
		}catch(Exception e){
			// 处理 Class.forName 错误
			e.printStackTrace();
		}finally{
			// 关闭资源
			try{
				if(stat!=null) {
					stat.close();
				}
			}catch(SQLException se2){
			}// 什么都不做
			try{
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * 传入插入语句后，返回Boolean标识，是否插入成功
	 * @param sql
	 * @return
	 */
	public int insert(String sql){
		Connection conn = null;
		Statement stat = null;
		// 注册驱动
		try {
			Class.forName(String.valueOf(instance.getValueByKey("base.database.driver-class-name")));
			conn = (Connection) DriverManager.getConnection(url,user,pass);
			stat = (Statement) conn.createStatement();
			int insert_res =  stat.executeUpdate(sql);

			stat.close();
			conn.close();
			return insert_res;
		} catch(SQLException se){
			// 处理 JDBC 错误
			se.printStackTrace();
		}catch(Exception e){
			// 处理 Class.forName 错误
			e.printStackTrace();
		}finally{
			// 关闭资源
			try{
				if(stat!=null) {
					stat.close();
				}
			}catch(SQLException se2){
			}// 什么都不做
			try{
				if(conn!=null) {
					conn.close();
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * 查询语句，返回结果，转化为list集合
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static List convertList(ResultSet rs) throws SQLException {

		List list = new ArrayList();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		while (rs.next()) {
			JSONObject rowData = new JSONObject();
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
		}
		return list;
	}




	public int sql(String database,String user,String password,String sql){
    	String DB_URL = "jdbc:mysql://"+database;
    	String USER = user;
    	String PASS = password;
       Connection conn = null;
       Statement stat = null;
       ResultSet rs = null;
       // 注册驱动
       try {
		Class.forName("com.mysql.jdbc.Driver");
		conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
		stat = (Statement) conn.createStatement();
		if(sql.contains("INSERT")) {			
			stat.execute(sql);
		}else if(sql.contains("update")) {
			stat.executeUpdate(sql);
		}else if(sql.contains("select")) {
			if(sql.contains("count(*)")) {
				rs = stat.executeQuery(sql);
				// 输出查询结果
				while(rs.next()){
					return rs.getInt("count(*)");
//	           System.out.print(rs.getInt("from_user_id")+",");
				}
			}else if(sql.contains("id")){			
				rs = stat.executeQuery(sql);
				// 输出查询结果
				while(rs.next()){
					return rs.getInt("id");
//	           System.out.print(rs.getInt("from_user_id")+",");
				}
			}else if(sql.contains("keywords")) {
				rs = stat.executeQuery(sql);
				// 输出查询结果
				while(rs.next()){
					return rs.getInt("keywords");
//	           System.out.print(rs.getInt("from_user_id")+",");
				}
			}
		}else {
			stat.execute(sql);
		}
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       // 创建链接
       // 关闭
      try {
          if (rs != null) {
              rs.close();
          }
      } catch (SQLException e) {
          e.printStackTrace();
      } finally {
          try {
              if (stat != null) {
                  stat.close();
              }
          } catch (SQLException e) {
              e.printStackTrace();
          } finally {
              try {
                  if (conn != null) {
                      conn.close();
                  }
              } catch (SQLException e) {
                  e.printStackTrace();
              }
          }
      }
      return 0;
    }

}
