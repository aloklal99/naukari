package alok.trials;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

public class GuavaFunctionalTrial {

	public static void main(String[] args) {
		final Set<String> users = new HashSet<String>();
		users.add("user1");
		users.add("user2");
		users.add("user3");
		users.add("user4");

		final Map<String, Set<String>> map = new HashMap<String, Set<String>>();
		Set<String> groupUsers = new HashSet<String>();
		groupUsers.add("user1");
		groupUsers.add("user2");
		groupUsers.add("user3");
		map.put("group1", groupUsers);
		
		groupUsers = new HashSet<String>();
		groupUsers.add("user3");
		groupUsers.add("user4");
		groupUsers.add("user5"); // invalid/unknown user
		map.put("group2", groupUsers);
		
		Map<String, Set<String>> filteredMap = Maps.filterKeys(map, new Predicate<String>() {

			@Override
			public boolean apply(String key) {
				return !users.containsAll(map.get(key));
			}
		});
		
		System.out.println(filteredMap);
		
		
//		Map<String, Boolean> filterdKeys =
}

}
