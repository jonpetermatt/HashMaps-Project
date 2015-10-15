import java.util.ArrayList;
import java.util.List;

public class SimplePasswordManager<K extends Comparable<K>, V>  {
	
	//Uses ChainingHashMap
	
	private ChainingHashMap<String, Long> passwords;
	private List<String> users  = new ArrayList<String>();
	
	// construct a SimplePasswordManager with 4000 places and default hash parameters
	// multiplier = 1 and modulus = 4271
	public SimplePasswordManager() {
		passwords = new ChainingHashMap<>(4000, 1, 47271);
	}
	// construct a SimplePasswordManager with given size, multiplier and modulus
	public SimplePasswordManager(int size, int multiplier, int modulus) {
		passwords = new ChainingHashMap<>(size, multiplier, modulus);
	}
	
	//hashing of the password
	//uses the djb2 algorithm
	public Long hashPasswords(String password) {
		Long hash = 5381l;
		for (int i = 0; i < password.length(); i++) {
			hash = hash*31 + Long.parseLong((Character.toString((password.charAt(i)))), 10);
		}
		return hash;
	}
	//returns a list of users
	public List<String> listUsers() {
		return users;
	}
	//authenticates user
	//compares the given password with the hashed password
	//if it matches return username
	//if it does not match return "Failed to authenticate user"
	//else return "No such user exists"
	public String authenticate(String username, String password) {
		if (passwords.get(username) == hashPasswords(password)) {
			return username;
		}
		if (passwords.get(username) != hashPasswords(password)) {
			return "Failed to authenticate user";
		}
		else {
			return "No such user exists";
		}
	}
	//Adds a new user
	//checks to see if the user already exits using get
	//if get does not return null return "user already exists"
	//else use put with the username as well as hashing the password
	public String addNewUser(String username, String password){
		if (passwords.get(username) != null) {
			return "User already exists";
		}
		passwords.put(username, hashPasswords(password));
		return username;
	}
	//deletes a user
	//tries to authenticate the user and if it fails, returns the message given by authenticate
	//else use remove to delete the user and return username 
	public String deleteUser(String username, String password) {
		String result = authenticate(username, password);
		if (result == "No such user exists") {
			return "No such user exists";
		}
		if (result == "Failed to authenticate user") {
			return "Failed to authenticate user";
		}
		passwords.remove(username);
		return username;
	}
	//changes the password of the user
	//tries to authenticate the user and if it fails, returns the message given by authenticate
	//else replace the the old password with the new one using put
	public String resetPassword(String username, String oldPassword, String newPassword) {
		String result = authenticate(username, oldPassword);
		if (result == "No such user exists") {
			return "No such user exists";
		}
		if (result == "Failed to authenticate user") {
			return "Failed to authenticate user";
		}
		passwords.remove(username);
		passwords.put(username, hashPasswords(newPassword));
		return username;
	}
}
