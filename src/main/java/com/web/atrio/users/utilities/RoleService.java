package com.web.atrio.users.utilities;

import java.util.ArrayList;
import java.util.List;

import com.web.atrio.users.models.Account;

public class RoleService {

	public static String getAuthorityStringSeperatedByCommas(Account user) {
		return user.sendRolesToRoleService();
	}

	public static Account addRole(Account account, String role) {
		String roles = account.sendRolesToRoleService();
		String[] rolesList = roles.split(",");
		boolean contains = false;
		for(String roleTemp : rolesList){
			if(roleTemp.equals(role)){
				contains = true;
			}
		}
		if(!contains){
			if(roles != ""){
				roles = roles + "," + role;
			} else {
				roles = role;
			}
			account.setRolesByRoleService(roles);
		}
		return account;
	}

	public static void removeRole(Account account, String role) {
		String roles = account.sendRolesToRoleService();
		String[] rolesList = roles.split(",");
		String rolesTemp = "";
		for(String roleTemp : rolesList){
			if(!roleTemp.equals(role)){
				rolesTemp = rolesTemp + "," + role;
			}
		}
		account.setRolesByRoleService(rolesTemp);
	}

	public static List<String> getRoles(Account account) {
		String roles = account.sendRolesToRoleService();
		String[] rolesList = roles.split(",");
		ArrayList<String> rolesArrayList = new ArrayList<String>();
		
		for(String roleTemp : rolesList){
			rolesArrayList.add(roleTemp);
		}
		return rolesArrayList;
	}
}
