/*******************************************************************************
 * Copyright (C) 2018 hungpham.
 * All rights reserved. This program and the accompanying materials are belonged to the MorphoBoid group of Image and Sound team in the LaBRI (UMR 5800) and the Ausonius Institute (UMR 5607)
 * 
 * http://morphoboid.labri.fr/projects.html
 * 
 * Contributors:
 *     hungpham - initial implementation of simulation of papyrus fragments assembly in 2D space. Version: 1.0
 ******************************************************************************/
package papyrus_application;

import java.util.ArrayList;
import java.util.List;

public class Compound {
	private List<Fragment> fragmentList;

	public Compound() {
		fragmentList = new ArrayList<Fragment>();
	}
	
	public void addFragment(Fragment fragment) {
		fragmentList.add(fragment);
	}

	public void removeFragment(Fragment fragment) {
		fragmentList.remove(fragment);
	}

	public List<Fragment> getFragmentList() {
		return fragmentList;
	}
	
	public int getFragmentListSize() {
		return fragmentList.size();
	}
	
	public Fragment getFragmentListByIndex(int i) {
		return fragmentList.get(i);
	}	
	
}
