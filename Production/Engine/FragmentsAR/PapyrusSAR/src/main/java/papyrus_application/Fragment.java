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

import graphic_module.DocumentObject;
import processing.core.PVector;

public class Fragment implements Cloneable {
	protected String name;

	protected String fileName;
	protected String maskFileName;

	protected String verso_FileName;
	protected String verso_MaskFileName;

	// info be used for compound
	protected float posX;
	protected float posY;
	protected float scale;
	protected float angle;

	public Fragment(String name, String fileName, String maskFileName) {
		this.name = name;

		this.fileName = fileName;
		this.maskFileName = maskFileName;

		this.verso_FileName = null;
		this.verso_MaskFileName = null;
	}

	public Fragment(String name, String fileName, String maskFileName, String verso_FileName,
			String verso_MaskFileName) {
		this.name = name;

		this.fileName = fileName;
		this.maskFileName = maskFileName;

		this.verso_FileName = verso_FileName;
		this.verso_MaskFileName = verso_MaskFileName;
	}

	public String getMaskFileName() {
		return maskFileName;
	}

	public float getScale() {
		return scale;
	}

	public String getFileName() {
		return fileName;
	}

	public float getAngle() {
		return angle;
	}

	public String getVerso_FileName() {
		return verso_FileName;
	}

	public String getVerso_MaskFileName() {
		return verso_MaskFileName;
	}

	public String getName() {
		return name;
	}

	public float getPosY() {
		return posY;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosXPosYFromOffsetCompound(PVector compoundOffset, PVector fragmentObjOffset, float fragmentObjAngle,
			float fragmentObjScale) {
		posX = fragmentObjOffset.x - compoundOffset.x;
		posY = fragmentObjOffset.y - compoundOffset.y;

		angle = fragmentObjAngle;
		scale = fragmentObjScale;
	}

	public Fragment clone() throws CloneNotSupportedException {
		Fragment cloneObj = (Fragment) super.clone();

		return cloneObj;
	}

}
