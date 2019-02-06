package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.domain.User;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();

	private static int userCount = 3;

	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Brad", new Date()));
		users.add(new User(3, "Coop", new Date()));
	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		System.out.println("ID of the user to save  =" + user.getId());
		if (user.getId() == null) {
			user.setId(++userCount);
			users.add(user);
		}
		System.out.println("ID of the user to save  =" + user.getName() + " User Size " +users.size());
		return user;
	}

	public User findUser(Integer id) {
		for (User user : users) {
			if (user.getId() == id)
				return user;
		}
		return null;
	}

	public User deleteUser(Integer id) {
		System.out.println("ID of the user to remove  =" + id);
		Iterator<User> usrItr = users.iterator();
		while (usrItr.hasNext()) {
			User user = usrItr.next();
			if (user.getId() == id) {
					usrItr.remove();								
					return user;
			}
		}
		return null;
	}

}
