/*******************************************************************************
 * Copyright (C) 2018 hungpham.
 * All rights reserved. This program and the accompanying materials are belonged to the MorphoBoid group of Image and Sound team in the LaBRI (UMR 5800) and the Ausonius Institute (UMR 5607)
 * 
 * http://morphoboid.labri.fr/projects.html
 * 
 * Contributors:
 *     hungpham - initial implementation of simulation of papyrus fragments assembly in 2D space. Version: 1.0
 ******************************************************************************/
import processing.core.*;
import fr.inria.papart.procam.*;

import tech.lity.rea.svgextended.*;
import org.bytedeco.javacpp.*;
import org.reflections.*;
import toxi.geom.*;
import org.openni.*;

import tech.lity.rea.skatolo.*;
import tech.lity.rea.skatolo.events.*;
import tech.lity.rea.skatolo.gui.controllers.*;
import fr.inria.papart.utils.MathUtils;
import graphic_module.Const;
import graphic_module.ScreenObject;
import fr.inria.papart.utils.*;
import fr.inria.papart.multitouch.*;
import fr.inria.papart.multitouch.detection.*;
import fr.inria.papart.multitouch.tracking.*;
import java.io.File;

import tech.lity.rea.colorconverter.*;
import Jama.Matrix;
import com.mkobos.pca_transform.*;

@SuppressWarnings("serial")
public class Papyrus extends PApplet {
	Papart papart;
	TouchDetectionDepth fingerDetection;
	ScreenObject screenObject;
	PImage background_Img;

	@Override
	public void setup() {
		papart = Papart.projection(this);
		fingerDetection = papart.loadTouchInput().initHandDetection();
		papart.startTracking();

		screenObject = new ScreenObject(fingerDetection, Const.Common.SCREENOBJECT_X, Const.Common.SCREENOBJECT_Y, Const.Common.INTERACTION_WIDTH_MILLIMETER, Const.Common.INTERACTION_HEIGHT_MILLIMETER);
		
		background_Img = loadImage(Const.Common.BACKGROUND_IMG_LINK);
		background_Img.resize(Const.Common.PROJECTION_WIDTH, Const.Common.PROJECTION_HEIGHT);
	}

	@Override
	public void settings() {
		// the application will be rendered in full screen, and using a 3Dengine.
		fullScreen(P3D, 1);
	}

	@Override
	public void draw() {
		background(background_Img);

	}

	public void fileSelected_Compound(File selection) {
		if (selection == null) {
			println("Window was closed or the user hit cancel.");
		} else {
			println("User selected file for Compounds" + selection.getAbsolutePath());
			try {
				screenObject.readInputFile_Compound(selection.getAbsolutePath());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void runThread_ReadFragmentInput() {
		screenObject.readInputFile(Const.Common.WORKSPACE_FOLDER_LINK + Const.InputFileConsts.INPUT_FILE_NAME);
	}

	//For open a dialog to select fragment input file.
	/*
	public void fileSelected(File selection) {
		if (selection == null) {
			println("Window was closed or the user hit cancel.");
		} else {
			println("User selected file for Fragments" + selection.getAbsolutePath());
			screenObject.readInputFile(selection.getAbsolutePath());
		}
	}
	*/

	
	//For open a dialog to select folder to save compound.
	/*
	public void folderSelected_Compound(File selection) {
		if (selection == null) {
			println("Window was closed or the user hit cancel.");
		} else {
			println("User selected folder for Compounds" + selection.getAbsolutePath());
			screenObject.saveOutputFile_Compound(selection.getAbsolutePath());
		}
	}
	*/

	/**
	 * @param passedArgs the command line arguments
	 */
	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { Papyrus.class.getName() };
		PApplet.main(appletArgs);
	}
}
