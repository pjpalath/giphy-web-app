/**
 * 
 */
package test.app.entities;

import java.util.List;

/**
 * @author paulp
 *
 */
public class UserAccount {

	private String userName;
	private String password;
	private List<AnimatedGIF> listOfGifs;

	public UserAccount() {

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AnimatedGIF> getListOfGifs() {
		return listOfGifs;
	}

	public void setListOfGifs(List<AnimatedGIF> listOfGifs) {
		this.listOfGifs = listOfGifs;
	}
}
