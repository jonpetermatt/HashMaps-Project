import java.util.ArrayList;
import java.util.List;

public class SimplePasswordManager<K extends Comparable<K>, V>  {
	
	private ChainingHashMap<String, Long> passwords;
	private List<String> users  = new ArrayList<String>();
	
	// construct a SimplePasswordManager with 4000 places and default hash parameters
	// multiplier = 1 and modulus = 4271
	public SimplePasswordManager() {
		passwords = new ChainingHashMap<>(4000, 1, 47271);
	}
	
	public SimplePasswordManager(int size, int multiplier, int modulus) {
		passwords = new ChainingHashMap<>(size, multiplier, modulus);
	}
	
	//hashing
	public Long hashPasswords(String password) {
		Long hash = 5381l;
		for (int i = 0; i < password.length(); i++) {
			hash = hash*31 + Long.parseLong((Character.toString((password.charAt(i)))), 10);
		}
		return hash;
	}
	
	public List<String> listUsers() {
		return users;
	}
	
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
	
	public String addNewUser(String username, String password){
		if (passwords.get(username) == null) {
			return "User already exists";
		}
		passwords.put(username, hashPasswords(password));
		return username;
	}
	
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
