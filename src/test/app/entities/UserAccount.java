/**
 * 
 */
package test.app.entities;

import java.util.List;

/**
 * @author paulp
 *
 * Entity that holds the user credentials and a list
 * of all the animated gif's in the user's profile
 */
public class UserAccount
{
	private String userName;
	private String password;
	private List<AnimatedGIF> listOfGifs;

	public UserAccount()
	{
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public List<AnimatedGIF> getListOfGifs()
	{
		return listOfGifs;
	}

	public void setListOfGifs(List<AnimatedGIF> listOfGifs)
	{
		this.listOfGifs = listOfGifs;
	}
}
