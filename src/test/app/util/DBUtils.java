/**
 * 
 */
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

/**
 * @author paulp
 *
 * This utility class is used to insert, updated and delete records from the
 * database for users and animated gid records
 */
public class DBUtils
{
	/**
	 * 
	 */
	public DBUtils()
	{
	}

	/**
	 * Retrieve the user account given username and password
	 *  
	 * @param conn
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException
	{
		String sql = "Select a.uname, a.pwd from user a where a.uname = ? and a.pwd= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next())
		{
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			return user;
		}
		return null;
	}

	/**
	 * Retrieve the user account given username
	 * 
	 * @param conn
	 * @param uname
	 * @return
	 * @throws SQLException
	 */
	public static UserAccount findUser(Connection conn, String uname) throws SQLException
	{
		String sql = "Select a.uname, a.pwd from user a where a.uname = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);

		ResultSet rs = pstm.executeQuery();

		if (rs.next())
		{
			String password = rs.getString("pwd");			
			UserAccount user = new UserAccount();
			user.setUserName(uname);
			user.setPassword(password);
			user.setListOfGifs(queryGif(conn, uname));
			return user;
		}
		return null;
	}
	
	/**
	 * Insert a new user to the user database
	 * 
	 * @param conn
	 * @param uname
	 * @param pwd
	 * @throws SQLException
	 */
	public static void insertUser(Connection conn, String uname, String pwd) throws SQLException
	{	
		String sql;
		PreparedStatement pstm;
		
		sql = "insert into user values (?, ?)";
		pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);
		pstm.setString(2, pwd);		
		pstm.executeUpdate();
	}

	/**
	 * Retrieve an animated gif record given the gif url
	 * 
	 * @param conn
	 * @param gifurl
	 * @return
	 * @throws SQLException
	 */
	public static AnimatedGIF retrieveGif(Connection conn, String gifurl) throws SQLException
	{
		String sql = "Select a.giftype from gif a where a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, gifurl);		
		ResultSet rs = pstm.executeQuery();
		AnimatedGIF aGif = new AnimatedGIF();
		while (rs.next())
		{			
			String giftype = rs.getString("giftype");			
			aGif.setUrl(gifurl);			
			aGif.setType(GIFClassification.valueOf(giftype));			
		}
		return aGif;
	}

	/**
	 * Retrieve all the gif's for a given user
	 * 
	 * @param conn
	 * @param uname
	 * @return
	 * @throws SQLException
	 */
	public static List<AnimatedGIF> queryGif(Connection conn, String uname) throws SQLException
	{
		String sql = "Select a.gifurl from gif_user a where a.uname = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);
		
		ResultSet rs = pstm.executeQuery();
		List<AnimatedGIF> list = new ArrayList<AnimatedGIF>();
		while (rs.next())
		{
			String gifurl = rs.getString("gifurl");			
			AnimatedGIF aGif = retrieveGif(conn, gifurl);			
			list.add(aGif);
		}
		return list;
	}	

	/**
	 * This utility method checks if a particular GIF given its URL
	 * exists in the database
	 * 
	 * @param conn
	 * @param gifurl
	 * @return
	 * @throws SQLException
	 */
	public static boolean isGifExists(Connection conn, String gifurl) throws SQLException {
		String sql = "Select a.giftype from gif a where a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);		
		pstm.setString(1, gifurl);
		
		ResultSet rs = pstm.executeQuery();		
		if (rs.next())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This utility method checks if a particular GIF given its URL
	 * exists in the database for the specified user
	 * @param conn
	 * @param uname
	 * @param gifurl
	 * @return
	 * @throws SQLException
	 */
	public static boolean isGifExistsForUser(Connection conn, String uname, String gifurl) throws SQLException
	{
		String sql = "Select a.gifurl from gif_user a where a.uname = ? and a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, uname);
		pstm.setString(2, gifurl);
		
		ResultSet rs = pstm.executeQuery();		
		if (rs.next())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This utility method checks if a particular GIF given its URL
	 * exists in the database for any user
	 * 
	 * @param conn
	 * @param gifurl
	 * @return
	 * @throws SQLException
	 */
	public static boolean isGifExistsForAnyUser(Connection conn, String gifurl) throws SQLException
	{
		String sql = "Select a.uname from gif_user a where a.gifurl = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);		
		pstm.setString(1, gifurl);
		
		ResultSet rs = pstm.executeQuery();		
		if (rs.next())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Insert GIF into database if it does not already exist. If the GIF already exists
	 * but is not associated with the specified user, then add it to the specified users
	 * account
	 * 
	 * @param conn
	 * @param uname
	 * @param gifurl
	 * @param giftype
	 * @throws SQLException
	 */
	public static void insertGif(Connection conn, String uname, String gifurl, String giftype) throws SQLException
	{	
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
	
	/**
	 * Update a GIF record in the database given the updated value
	 * 
	 * @param conn
	 * @param uname
	 * @param gifurl
	 * @param giftype
	 * @throws SQLException
	 */
	public static void updateGif(Connection conn, String uname, String gifurl, String giftype) throws SQLException
	{
		String sql = "update gif set giftype = ? where gifurl = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, giftype);
		pstm.setString(2, gifurl);
		pstm.executeUpdate();
	}
	
	/**
	 * Delete the gif given the gif url for the given user. Since the gif might be
	 * associated with other users, check to see if there are any other users associated
	 * with this gif - if none then delete from gif table
	 * 
	 * @param conn
	 * @param uname
	 * @param gifurl
	 * @throws SQLException
	 */
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
