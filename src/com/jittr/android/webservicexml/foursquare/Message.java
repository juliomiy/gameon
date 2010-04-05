package com.jittr.android.webservicexml.foursquare;
import java.net.MalformedURLException;
import java.net.URL;

public class Message implements Comparable<Message> {
public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public URL getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		try {
			this.photo = new URL(photo);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
private String id;
private String user;
private String firstname;
private String lastname;
private String name;
private URL photo;
private String gender;
private String email;
private String facebook;
private String twitter;

public String getFacebook() {
	return facebook;
}
public void setFacebook(String facebook) {
	this.facebook = facebook;
}
public String getTwitter() {
	return twitter;
}
public void setTwitter(String twitter) {
	this.twitter = twitter;
}
public void setPhoto(URL photo) {
	this.photo = photo;
}
/*public int compareTo(final Message o) {
	// TODO Auto-generated method stub
	return 0;
}
*/
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Message [email=");
	builder.append(email);
	builder.append(", facebook=");
	builder.append(facebook);
	builder.append(", firstname=");
	builder.append(firstname);
	builder.append(", gender=");
	builder.append(gender);
	builder.append(", id=");
	builder.append(id);
	builder.append(", lastname=");
	builder.append(lastname);
	builder.append(", name=");
	builder.append(name);
	builder.append(", photo=");
	builder.append(photo);
	builder.append(", twitter=");
	builder.append(twitter);
	builder.append(", user=");
	builder.append(user);
	builder.append("]");
	return builder.toString();
}
public int compareTo(Message arg0) {
	// TODO Auto-generated method stub
	return 0;
}
}
