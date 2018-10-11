package test.app.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import test.app.entities.AnimatedGIF;
import test.app.entities.GIFClassification;
import test.app.entities.UserAccount;

public class DBUtils {

	public DBUtils() {
	}

	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {

		String sql = "Select a.uname, a.pwd from user a where a.uname = ? and a.pwd= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {			
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			return user;
		}
		return null;
	}

	public static UserAccount findUser(Connection conn, String uname) throws SQLException {

		String sql = "Select a.uname, a.pwd from user a where a.uname = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);

		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			String password = rs.getString("pwd");			
			UserAccount user = new UserAccount();
			user.setUserName(uname);
			user.setPassword(password);
			user.setListOfGifs(queryGif(conn, uname));
			return user;
		}
		return null;
	}
	
	public static void insertUser(Connection conn, String uname, String pwd) throws SQLException {
		
		String sql;
		PreparedStatement pstm;
		
		sql = "insert into user values (?, ?)";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);
		pstm.setString(2, pwd);		
		pstm.executeUpdate();
	}

	public static AnimatedGIF retrieveGif(Connection conn, String gifurl) throws SQLException {
		String sql = "Select a.giftype from gif a where a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, gifurl);		
		ResultSet rs = pstm.executeQuery();
		AnimatedGIF aGif = new AnimatedGIF();
		while (rs.next()) {			
			String giftype = rs.getString("giftype");			
			aGif.setUrl(gifurl);			
			aGif.setType(GIFClassification.valueOf(giftype));			
		}
		return aGif;
	}

	public static List<AnimatedGIF> queryGif(Connection conn, String uname) throws SQLException {
		String sql = "Select a.gifurl from gif_user a where a.uname = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);
		
		ResultSet rs = pstm.executeQuery();
		List<AnimatedGIF> list = new ArrayList<AnimatedGIF>();
		while (rs.next()) {
			String gifurl = rs.getString("gifurl");			
			AnimatedGIF aGif = retrieveGif(conn, gifurl);			
			list.add(aGif);
		}
		return list;
	}	

	public static boolean isGifExists(Connection conn, String gifurl) throws SQLException {
		String sql = "Select a.giftype from gif a where a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);		
		pstm.setString(1, gifurl);
		
		ResultSet rs = pstm.executeQuery();		
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public static boolean isGifExistsForUser(Connection conn, String uname, String gifurl) throws SQLException {
		String sql = "Select a.gifurl from gif_user a where a.uname = ? and a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);
		pstm.setString(2, gifurl);
		
		ResultSet rs = pstm.executeQuery();		
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public static boolean isGifExistsForAnyUser(Connection conn, String gifurl) throws SQLException {
		String sql = "Select a.uname from gif_user a where a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);		
		pstm.setString(1, gifurl);
		
		ResultSet rs = pstm.executeQuery();		
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public static void insertGif(Connection conn, String uname, String gifurl, String giftype) throws SQLException {
		
		String sql;
		PreparedStatement pstm;
		
		if (!isGifExists(conn, gifurl))
		{
			sql = "insert into gif values (?, ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, gifurl);
			pstm.setString(2, giftype);		
			pstm.executeUpdate();
		}
		
		if (!isGifExistsForUser(conn, uname, gifurl))
		{
			sql = "insert into gif_user values (?, ?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, gifurl);
			pstm.setString(2, uname);		
			pstm.executeUpdate();
		}
	}
	
	public static void updateGif(Connection conn, String uname, String gifurl, String giftype) throws SQLException {
		String sql = "update gif set giftype = ? where gifurl = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, giftype);
		pstm.setString(2, gifurl);
		pstm.executeUpdate();
	}
	
	public static void deleteGif(Connection conn, String uname, String gifurl) throws SQLException {
		String sql = "delete from gif_user where gifurl = ? and uname = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, gifurl);
		pstm.setString(2, uname);
		pstm.executeUpdate();
		
		if (!isGifExistsForAnyUser(conn, gifurl))
		{
			sql = "delete from gif where gifurl = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, gifurl);			
			pstm.executeUpdate();
		}
	}
}
