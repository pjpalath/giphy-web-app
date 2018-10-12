/**
 * 
 */
package test.app.entities;

/**
 * @author paulp
 *
 * Entity that holds the animated gif URL and type
 */
public class AnimatedGIF {

	private String url;
	private GIFClassification type;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public GIFClassification getType() {
		return type;
	}
	public void setType(GIFClassification type) {
		this.type = type;
	}
}
