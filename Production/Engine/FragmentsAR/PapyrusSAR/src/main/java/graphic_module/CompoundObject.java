/*******************************************************************************
 * Copyright (C) 2018 hungpham.
 * All rights reserved. This program and the accompanying materials are belonged to the MorphoBoid group of Image and Sound team in the LaBRI (UMR 5800) and the Ausonius Institute (UMR 5607)
 * 
 * http://morphoboid.labri.fr/projects.html
 * 
 * Contributors:
 *     hungpham - initial implementation of simulation of papyrus fragments assembly in 2D space. Version: 1.0
 ******************************************************************************/
package graphic_module;

import java.util.ArrayList;
import java.util.List;

import fr.inria.papart.multitouch.Touch;
import fr.inria.papart.multitouch.TouchList;
import papyrus_application.Compound;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;

public class CompoundObject extends DocumentObject {

	private Compound compound;

	protected final static float INITIAL_WITH = 20f;
	protected final static float INITIAL_HEIGHT = 20f;
	protected final static float INITIAL_X = 0f;
	protected final static float INITIAL_Y = 0f;

	private String name;

	public List<DocumentObject> innerFragments;

	public CompoundObject(String name) {

		super(INITIAL_X, INITIAL_Y, INITIAL_WITH, INITIAL_HEIGHT);
		this.name = name;
		this.isDisplay = true;
		innerFragments = new ArrayList<DocumentObject>();
	}

	public CompoundObject(float offsetX, float offsetY, float width, float height) {
		super(offsetX, offsetY, width, height);

		innerFragments = new ArrayList<DocumentObject>();
	}

	public void addFragment(FragmentObject fragmentObj) {
		fragmentObj.setSelected(false);
		fragmentObj.setInsideCompound(true);
		innerFragments.add(fragmentObj);
		caculateOffsetWidthHeight();
	}

	public void removeFragment(FragmentObject fragmentObj) {
		innerFragments.remove(fragmentObj);
		caculateOffsetWidthHeight();
	}

	public String getName() {
		return name;
	}

	/** Calculate offset, width, height from offsets for inner fragment */
	public void caculateOffsetWidthHeight() {
		float minX = INITIAL_X;
		float minY = INITIAL_Y;
		float maxX = INITIAL_WITH;
		float maxY = INITIAL_HEIGHT;

		for (int i = 0; i < innerFragments.size(); i++) {
			FragmentObject fragmentObj = (FragmentObject) innerFragments.get(i);
			PVector innerOffset = fragmentObj.getOffset();
			float top_right_Point_X = innerOffset.x + fragmentObj.getWidth();
			float bottom_Left_Point_Y = innerOffset.y + fragmentObj.getHeight();

			// Find a offset point for blending && find width and height for blending
			if (i != 0) {
				minX = innerOffset.x < minX ? innerOffset.x : minX;
				minY = innerOffset.y < minY ? innerOffset.y : minY;

				maxX = top_right_Point_X > maxX ? top_right_Point_X : maxX;
				maxY = bottom_Left_Point_Y > maxY ? bottom_Left_Point_Y : maxY;

			} else {
				minX = innerOffset.x;
				minY = innerOffset.y;

				maxX = top_right_Point_X;
				maxY = bottom_Left_Point_Y;
			}
		}

		offset.x = minX;
		offset.y = minY;

		width = maxX - offset.x;
		height = maxY - offset.y;
	}

	@Override
	public String drawObject(PGraphicsOpenGL g, TouchList fingerTouchs) {
		String infoText = "";

		// Only draw with isDisplay attribute is true
		if (isDisplay) {
			for (int i = 0; i < innerFragments.size(); i++) {
				FragmentObject fragmentObj = (FragmentObject) innerFragments.get(i);
				fragmentObj.drawObject(g, fingerTouchs);
			}
		}

		if (tapped) {
			infoText = getInfo();
		}

//		g.stroke(255,0,0);
//		g.strokeWeight(2);
//		g.rect(offset.x, offset.y, width, height);

		return infoText;
	}

	private String getInfo() {
		String result = "";

		String fragments = "";
		for (int i = 0; i < innerFragments.size(); i++) {
			FragmentObject fragmentObj = (FragmentObject) innerFragments.get(i);
			fragments += " - " + fragmentObj.getFragmentName() + "\n";
		}

		result += "# " + name + "\n" + " Inner fragments: " + "\n" + fragments + "\n";

		return result;
	}

	protected boolean checkCompoundIsTapped(TouchList fingerTouchs, List<Touch> used_Points) {
		boolean temp = false;
		int tappedFragmentIndex = -1;
		float dX = 0f;
		float dY = 0f;
		for (int i = 0; i < innerFragments.size(); i++) {
			FragmentObject fragmentObj = (FragmentObject) innerFragments.get(i);
			boolean isTapped = fragmentObj.checkFragmentIsTapped(fingerTouchs, used_Points);

			if (isTapped) {
				temp = true;
				tappedFragmentIndex = i;
				dX = fragmentObj.distanceX;
				dY = fragmentObj.distanceY;
				break;
			}
		}

		// Add moving offset for remaining innerFragments
		if (temp) {
			for (int i = 0; i < innerFragments.size(); i++) {
				FragmentObject fragmentObj = (FragmentObject) innerFragments.get(i);
				if (i != tappedFragmentIndex) {
					// fragmentObj.setTapped(true);
					fragmentObj.addOffset(dX, dY);
				} else {
					fragmentObj.distanceX = 0f;
					fragmentObj.distanceY = 0f;
				}
			}
		}

		setTapped(temp);

		return tapped;
	}

	private void setSelectedInnerFragments(boolean isSelected) {
		for (int i = 0; i < innerFragments.size(); i++) {
			innerFragments.get(i).setSelected(isSelected);
		}
	}

	protected boolean checkCompoundIsSelected(TouchList fingerTouchs) {
		setSelected(false);
		boolean temp = false;

		for (int i = 0; i < innerFragments.size(); i++) {
			FragmentObject fragmentObj = (FragmentObject) innerFragments.get(i);

			if (fragmentObj.checkFragmentIsSelected(fingerTouchs)) {
				temp = true;
				break;
			}
		}

		setSelected(temp);
		return temp;
	}

	@Override
	public void setSelected(boolean selected) {

		this.selected = selected;
		setSelectedInnerFragments(selected);
	}

	@Override
	public void rotateByAngle(float angle) {
		for (int i = 0; i < innerFragments.size(); i++) {
			FragmentObject fragmentObj = (FragmentObject) innerFragments.get(i);
			fragmentObj.rotateByAngle(angle);
		}
	}

	public Compound getCompound() {
		return compound;
	}

	public void setCompound(Compound compound) {
		this.compound = compound;
	}

}
