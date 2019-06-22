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

import java.util.List;

import fr.inria.papart.multitouch.Touch;
import fr.inria.papart.multitouch.TouchList;
import processing.core.PMatrix3D;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;

public abstract class DocumentObject implements Cloneable {

	/** Width of this object */
	protected float width;

	/** Height of this object */
	protected float height;

	/** Offset of the outer object. In screen coordinates. */
	protected PVector offset = new PVector();

	/** Transformation center, to rotate and scale around. In screen coordinates. */
	protected PVector transCenter = new PVector();

	/** Rotation angle */
	protected PVector angle = new PVector(0, 0, 0);

	/** Scaling factor */
	protected float scale = 1;

	/** Outer transformation matrix. */
	protected PMatrix3D matrix = new PMatrix3D();
	
	protected boolean isDisplay = false;
	
	/**
	 * Store info when inner object (fragment) was tapped
	 */

	protected boolean tapped;
	
	protected boolean selected = false;

	public DocumentObject(float offsetX, float offsetY, float width, float height) {
		this.width = width;
		this.height = height;

		offset.x = offsetX;
		offset.y = offsetY;

		transCenter.x = width / 2;
		transCenter.y = height / 2;

		calculateMatrix();
	}

	protected void calculateMatrix() {
		PMatrix3D invMatrix = new PMatrix3D();
		invMatrix.apply(matrix);
		invMatrix.invert();

		float originalCenterX = invMatrix.multX(transCenter.x, transCenter.y);
		float originalCenterY = invMatrix.multY(transCenter.x, transCenter.y);

		matrix = new PMatrix3D();
		matrix.translate(transCenter.x, transCenter.y);
		matrix.scale(scale);
		matrix.rotateZ(angle.z);
		matrix.translate(-originalCenterX, -originalCenterY);
	}

	public void rotate(float angle) {
		this.angle.z += angle;
		calculateMatrix();
	}

	public void scale(float scale) {
		this.scale *= scale;
		calculateMatrix();
	}
	
	public void setScale(float scale) {
		this.scale = scale;
		calculateMatrix();
	}
	
	public void setAngleZ(float angle) {
		this.angle.z = angle;
		calculateMatrix();
	}
	
	public void addOffset(float dx, float dy) {
		offset.x += dx;
		offset.y += dy;

		calculateMatrix();
	}

	public boolean isHit(float checkX, float checkY) {
		float[] check = getObjectFromScreenPosition(checkX, checkY);
		return (check[0] > 0 && check[0] < 0 + width && check[1] > 0 && check[1] < 0 + height);
	}

	public float[] getObjectFromScreenPosition(float x, float y) {
		return getTransformedPosition(x, y, true);
	}

	private float[] getTransformedPosition(float x, float y, boolean inverse) {
		if (inverse) {
			x -= offset.x;
			y -= offset.y;
		}

		float[] xyz = new float[3];
		PMatrix3D m = new PMatrix3D();
		m.apply(matrix);
		if (inverse) {
			m.invert();
		}
		m.mult(new float[] { x, y, 0 }, xyz);

		if (!inverse) {
			xyz[0] += offset.x;
			xyz[1] += offset.y;
		}

		return xyz;
	}

	public void setTransCenter(float x, float y) {
		transCenter.x = x - offset.x;
		transCenter.y = y - offset.y;
	}
	
	public PVector getOffset() {
		return offset;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void setTapped(boolean tapped) {
		this.tapped = tapped;
	}

	public boolean isTapped() {
		return tapped;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public String drawObject(PGraphicsOpenGL g, TouchList fingerTouchs) {
		return "";
	}
	
	protected boolean checkUsedTouchPoint(List<Touch> used_Points, Touch t) {
		boolean isUsed = false;

		for (int i = 0; i < used_Points.size(); i++) {
			if (t.id == used_Points.get(i).id) {
				isUsed = true;
			}
		}

		return isUsed;
	}
	
    public DocumentObject clone() throws CloneNotSupportedException{
    	DocumentObject cloneObj = (DocumentObject) super.clone();
    	cloneObj.offset = this.offset.copy();
    	cloneObj.transCenter =  this.transCenter.copy();
    	cloneObj.angle=  this.angle.copy();
    	
    	return cloneObj;
    }
    
	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	public void rotateByAngle(float angle) {
		rotate(angle);
	}
}
