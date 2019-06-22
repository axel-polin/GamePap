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
import papyrus_application.Fragment;
import processing.opengl.PGraphicsOpenGL;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class FragmentObject extends DocumentObject{

	private Fragment fragment;

	private PImage primaryImg;
	private PImage maskImg;
	private PImage boundingImg;

	private PImage verso_Img;
	private PImage verso_MaskImg;
	private PImage verso_BoundingImg;

	private boolean isVerso = false;

	protected List<Touch> touch_Points = new ArrayList<Touch>();


	protected float oldX;
	protected float oldY;

	protected float oldAngle;
	protected Touch destPoint;
	
	protected boolean isInsideCompound = false;

	/** 
	 * Store moving distance for other fragments get these info
	 */
	public float distanceX;
	public float distanceY;
	public float storage_angle;

	private int preTappedTouchId = -1;
	
	/**
	 * 4 points for 4 circles of fragment were used for rotating
	 */
	protected PVector point_1 = new PVector(0, 0);
	protected PVector point_2 = new PVector(width, 0);
	protected PVector point_3 = new PVector(0, height);
	protected PVector point_4 = new PVector(width, height);

	protected int tappCir_TouchId = -1;

	protected final float CIRCLE_RADIUS = 20f;

	public FragmentObject(String name, String fileName, String maskFileName, float offsetX, float offsetY, float width,
			float height) {
		super(offsetX, offsetY, width, height);
		fragment = new Fragment(name, fileName, maskFileName);
	}

	public FragmentObject(String name, String fileName, String maskFileName, String verso_FileName, String verso_MaskFileName,
			float offsetX, float offsetY, float width, float height) {
		super(offsetX, offsetY, width, height);
		fragment = new Fragment(name, fileName, maskFileName, verso_FileName, verso_MaskFileName);
	}

	public boolean isVerso() {
		return isVerso;
	}

	public void setVerso(boolean isVerso) {
		this.isVerso = isVerso;
	}

	
	public boolean hasVersoImg() {
		if (verso_BoundingImg != null)
			return true;
		return false;
	}
	
	public PImage getBoundingImg() {
		return boundingImg;
	}
	
	public String getFragmentName() {
		return fragment.getName();
	}
	
	public void setInsideCompound(boolean isInsideCompound) {
		this.isInsideCompound = isInsideCompound;
	}
	
	@Override
	public String drawObject(PGraphicsOpenGL g, TouchList fingerTouchs) {
		String infoText = "";
		
		// In the case fragment was hidden
		if(!isDisplay) {
			return infoText;
		}
		
		//Use for visualizing border of fragment		
		//g.strokeWeight(2);
		//g.rect(offset.x, offset.y, width, height);
		
		g.pushMatrix();
		g.translate(offset.x, offset.y);
		g.applyMatrix(matrix);
		/// ===Begin drawing for fragment

		// Highlight fragments was tapped
		if (tapped) {
			g.tint(0, 255, 0);
			infoText =  getInfo();
		} else {
			g.noTint();
		}

		// Change border color of fragment was selected
		if (selected && !isInsideCompound) {
			int redColor = 0;
			int greenColor = 250;
			int blueColor = 0;
			
			// Draw border of fragment
			g.strokeWeight(2);
			g.stroke(redColor, greenColor, blueColor);
			g.rect(0, 0, width, height);


			// draw 4 circles for rotating
			g.fill(redColor, greenColor, blueColor);
			g.ellipse(point_1.x, point_1.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
			g.ellipse(point_2.x, point_2.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
			g.ellipse(point_3.x, point_3.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
			g.ellipse(point_4.x, point_4.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
			
			g.noFill();
			infoText =  getInfo();
		} else {
			g.stroke(100);
		}
		
		// Used for display fragments which belonged a compound
		if(isInsideCompound) {
			int redColor = 255;
			int greenColor = 245;
			int blueColor = 170;
			int weight = 1;
			
			if(selected) {
				redColor = 0;
				greenColor = 255;
				blueColor = 0;
				weight = 2;
				
			}
			
			g.strokeWeight(weight);
			g.stroke(redColor, greenColor, blueColor);
			g.rect(0, 0, width, height);
			
			g.fill(redColor, greenColor, blueColor);
			
			if(selected) {
				g.ellipse(point_1.x, point_1.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
				g.ellipse(point_2.x, point_2.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
				g.ellipse(point_3.x, point_3.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
				g.ellipse(point_4.x, point_4.y, CIRCLE_RADIUS, CIRCLE_RADIUS);
			}
			
			g.stroke(100);
			
			g.noFill();
		}

		// Draw bounding image of fragment
		g.noFill();
		if (!isVerso) {
			g.image(boundingImg, 0, 0, width, height);
		} else {
			g.image(verso_BoundingImg, 0, 0, width, height);
		}

		// Draw transCenter point
		g.fill(255, 0, 0);
		g.ellipse(transCenter.x, transCenter.y, 10, 10);
		g.noFill();

		/// ===End drawing for fragment
		g.popMatrix();

		return infoText;
	}
	
	private String getInfo() {
		String result = "";
		
		result += "# " + fragment.getName() + "\n" 
				+ "  is back: " + isVerso + "\n"
				+ "  scale: " + scale + "\n"
				+ "  angle: " + angle.z + "\n"
				+ "  offset X: " + offset.x + "\n"
				+ "  offset Y: " + offset.y + "\n";
		return result;
	}


	private void updateForMoving(Touch t, boolean hasRotation) {
		float x = t.position.x;
		float y = t.position.y;
		float dx = x - oldX;
		float dy = y - oldY;

		// Using for moving compound
		distanceX = dx;
		distanceY = dy;
		
		if (hasRotation) {

			float[] convert_point_2nd = getObjectFromScreenPosition(t.position.x, t.position.y);
			PVector point_2nd = new PVector(convert_point_2nd[0], convert_point_2nd[1]);
			float newAngle = getAngleBetween(transCenter, point_2nd);

			float[] convert_point_1st = getObjectFromScreenPosition(oldX, oldY);
			PVector point_1st = new PVector(convert_point_1st[0], convert_point_1st[1]);
			float oldAngle = getAngleBetween(transCenter, point_1st);

			float angle = newAngle - oldAngle;
			rotate(angle);

			// Store destination point
			destPoint = t;
			storage_angle = angle - this.angle.z;
		}

		addOffset(dx, dy);

		oldX = x;
		oldY = y;
	}

	private void updateForRotating(Touch t) {
		if (t.id == tappCir_TouchId) {
			// if touch points have 2 points, the fragment was rotated by two fingers
			// setTransCenter(offset.x + width / 2, offset.y + height / 2);

			float[] convertTouchPoint = getObjectFromScreenPosition(t.position.x, t.position.y);
			PVector touchPoint = new PVector(convertTouchPoint[0], convertTouchPoint[1]);

			float newAngle = getAngleBetween(transCenter, touchPoint);
			float angle = newAngle - oldAngle;

			oldAngle = newAngle;
			rotate(angle);

		}
	}

	public void zoomInOut(boolean isZoomIn) {
		float rate = 0.1f;

		if (isZoomIn) {
			scale += rate;

			if (scale > 1.0f)
				scale = 1.0f;
		} else {
			scale -= rate;

			if (scale < 0.2f)
				scale = 0.2f;
		}
		calculateMatrix();
	}

	public void storeInfoForMoving(Touch t) {
		// If first point was added, storing info for moving
		oldX = t.position.x;
		oldY = t.position.y;
	}

	private void storeInfoForRotating(Touch t) {
		// if user selected one of 4 circles, it should store info for rotating
		float[] convertTouchPoint = getObjectFromScreenPosition(t.position.x, t.position.y);
		PVector touchPoint = new PVector(convertTouchPoint[0], convertTouchPoint[1]);

		oldAngle = getAngleBetween(transCenter, touchPoint);
		tappCir_TouchId = t.id;
	}


	protected boolean checkFragmentIsTapped(TouchList fingerTouchs, List<Touch> used_Points) {
		boolean temp = false;
		List<Touch> touch_Points_Temp = new ArrayList<Touch>();

		for (Touch t : fingerTouchs) {
			// Check touch point is not used for other fragment
			boolean isUsed = checkUsedTouchPoint(used_Points, t);

			// The fragment was tapped before, user continue doing some actions on fragment
			// such as rotating, moving etc..
			if (!isUsed && tapped) {
				// Check the touch id equal to old touch id, the fragment position was updated
				// for moving
				if (touch_Points.get(0) != null && t.id == touch_Points.get(0).id) {
					updateForMoving(t, false);
				}
			}

			// Checking the fragment was tapped by how many touch points, and then storing
			// the info for next actions
			if (!isUsed && checkPointInsideFragment(t)) {
				temp = true;

				if (touch_Points_Temp.size() == 0) {
					storeInfoForMoving(t);
				}

				touch_Points_Temp.add(t);
			}
		}

		setTapped(temp);
		if (temp == false) {
			oldX = 0;
			oldY = 0;
		}

		// Store info of touch points on the fragment
		touch_Points = new ArrayList<Touch>(touch_Points_Temp);

		return tapped;
	}
	
	public boolean checkFragmentIsSelected(TouchList fingerTouchs) {
		boolean temp = false;
		for (Touch t : fingerTouchs) {
			if (checkPointInsideFragment(t) && t.id != preTappedTouchId) {
				temp = true;
				preTappedTouchId = t.id;
			}
		}

		if(temp) {
			selected = !selected;
		}

		return selected;
	}

	protected void handlingForMovingAndRotating(TouchList fingerTouchs) {

		for (Touch t : fingerTouchs) {
			if (touch_Points.size() != 0) {
				// Check the touch id equal to old touch id, the fragment position was updated
				// for moving
				if (t.id == touch_Points.get(0).id) {
					updateForMoving(t, true);
				}
			}

			if (checkPointInsideFragment(t)) {
				storeInfoForMoving(t);
				touch_Points = new ArrayList<Touch>();
				touch_Points.add(t);
			}
		}

	}

	protected void handlingTappingCirclesAndRotating(TouchList fingerTouchs) {
		boolean temp = false;

		for (Touch t : fingerTouchs) {
			if (tappCir_TouchId != -1) {
				System.out.print("\nPRINT For Debugging");
				updateForRotating(t);
			}

			if (checkPointInsideCircle(t)) {
				System.out.print("\nPRINT For checkPointInsideCircle");
				temp = true;
				storeInfoForRotating(t);

			}
		}

		setTapped(temp);
	}

	public boolean checkPointInsideCircle(Touch t) {
		float[] convertPoint = getObjectFromScreenPosition(t.position.x, t.position.y);
		float dist_to_point1 = PApplet.dist(convertPoint[0], convertPoint[1], point_1.x, point_1.y);
		float dist_to_point2 = PApplet.dist(convertPoint[0], convertPoint[1], point_2.x, point_2.y);
		float dist_to_point3 = PApplet.dist(convertPoint[0], convertPoint[1], point_3.x, point_3.y);
		float dist_to_point4 = PApplet.dist(convertPoint[0], convertPoint[1], point_4.x, point_4.y);

		if ((dist_to_point1 <= CIRCLE_RADIUS) || (dist_to_point2 <= CIRCLE_RADIUS) || (dist_to_point3 <= CIRCLE_RADIUS)
				|| (dist_to_point4 <= CIRCLE_RADIUS)) {
			return true;
		}

		return false;
	}

	protected PImage getBoundingRect(boolean isVerso) {
		PImage temp_PriImg = primaryImg;
		PImage temp_MaskImg = maskImg;
		if (isVerso) {
			temp_PriImg = verso_Img;
			temp_MaskImg = verso_MaskImg;
		}

		int min_X = -1;
		int min_Y = -1;
		int max_X = -1;
		int max_Y = -1;

		for (int x = 0; x < temp_MaskImg.width; x++) {
			for (int y = 0; y < temp_MaskImg.height; y++) {
				int pixelColor = temp_MaskImg.pixels[x + y * temp_MaskImg.width];

				if (pixelColor == -1) {
					if (min_X == -1) {
						min_X = x;
						max_X = x;
						min_Y = y;
						max_Y = y;
					}

					if (x > max_X)
						max_X = x;

					if (y < min_Y)
						min_Y = y;

					if (y > max_Y)
						max_Y = y;
				}
			}
		}

		int width = max_X - min_X;
		int height = max_Y - min_Y;
		PImage result = temp_PriImg.get(min_X, min_Y, width, height);

		return result;
	}

	protected float getDistance(Touch point1, Touch point2) {
		return PApplet.dist(point1.position.x, point1.position.y, point2.position.x, point2.position.y);
	}

	protected float getAngleBetween(PVector point1, PVector point2) {
		float difY = point1.y - point2.y;
		float difX = point1.x - point2.x;

		float angle = PApplet.atan2(difY, difX);
		return angle;
	}

	public PImage getPrimaryImg() {
		return primaryImg;
	}

	public PImage getMaskImg() {
		return maskImg;
	}

	public boolean checkPointInsideFragment(Touch t) {
		return isHit(t.position.x, t.position.y);
	}

	public void loadImage(PApplet rootPApplet) {

		primaryImg = rootPApplet.loadImage(fragment.getFileName());
		maskImg = rootPApplet.loadImage(fragment.getMaskFileName());

		if (fragment.getVerso_FileName() != null)
			verso_Img = rootPApplet.loadImage(fragment.getVerso_FileName());
		if (fragment.getVerso_MaskFileName() != null)
			verso_MaskImg = rootPApplet.loadImage(fragment.getVerso_MaskFileName());

		int originalWidth = primaryImg.width;
		int originalHeight = primaryImg.height;

		// currently we're displaying the width and height of fragment is 156 and 234 on
		// screen
		int displayWidth = 156;
		int displayHeight = 234;

		// Reduce the resolution to improve performance loading because it's so high
		int loadingWidth = originalWidth / 6;
		int loadingHeight = originalHeight / 6;

		primaryImg.resize(loadingWidth, loadingHeight);
		primaryImg.loadPixels();

		maskImg.resize(loadingWidth, loadingHeight);
		maskImg.loadPixels();

		if (verso_Img != null) {
			verso_Img.resize(loadingWidth, loadingHeight);
			verso_Img.loadPixels();
		}

		if (verso_MaskImg != null) {
			verso_MaskImg.resize(loadingWidth, loadingHeight);
			verso_MaskImg.loadPixels();
		}

		boundingImg = getBoundingRect(false);
		if (verso_Img != null && verso_MaskImg != null)
			verso_BoundingImg = getBoundingRect(true);

		width = boundingImg.width;
		height = boundingImg.height;
		scale = (float) displayWidth / loadingWidth;
		transCenter.x = width / 2;
		transCenter.y = height / 2;

		set4PointsValue();
		calculateMatrix();
	}

	public void set4PointsValue() {
		point_1.set(0, 0);
		point_2.set(width, 0);
		point_3.set(0, height);
		point_4.set(width, height);
	}
	
	public Fragment getFragment() throws CloneNotSupportedException {
		return fragment.clone();
	}
    
}
