package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class OracleDAO {
	Connection con;
	PreparedStatement psmt;
	ResultSet rs;
	
	public OracleDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource source =
					(DataSource)ctx.lookup("java:comp/env/jdbc/myoracle");
			con = source.getConnection();
			System.out.println("DBCP연결 성공");;
		}
		catch (Exception e) {
			System.out.println("DBCP연결실패");
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(psmt!=null) psmt.close();
			if(con!=null) con.close();
		} catch (Exception e) {
			System.out.println("자원반납시 예외발생");
			e.printStackTrace();
		}
		
	}
	
	public boolean isMemeber(String id, String pass) {
		
		String sql = "SELECT COUNT(*) FROM member"
				+ " WHERE id=? AND pass=?";
		int isMemeber = 0;
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pass);
			rs = psmt.executeQuery();
			rs.next();
			isMemeber = rs.getInt(1);
			System.out.println("affect:" + isMemeber);
			if(isMemeber==0) return false;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
				
	}
	
	
}
