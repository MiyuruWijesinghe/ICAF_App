package com.devcrawlers.conference.management.util;

import java.util.List;

public class IdGenerator {
	
	public static int generateUserIDs(List<Integer> arrayList) {

		int id;
		int next = arrayList.size();
		next++;
		id = next;
		if (arrayList.contains(id)) {
			next++;
			id = next;
		}
		return id;
	}

}
