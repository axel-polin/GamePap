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

public class ListofCompound {
	private List<Compound> compoundList;
	
	public ListofCompound() {
		compoundList = new ArrayList<Compound>();
	}
	
	public void add(Compound blending) {
		compoundList.add(blending);
	}

	public void remove(Compound blending) {
		compoundList.remove(blending);
	}

	public List<Compound> getBlendingList() {
		return compoundList;
	}
	
	public int getSize() {
		return compoundList.size();
	}
	
	public Compound getByIndex(int i) {
		return compoundList.get(i);
	}
}
