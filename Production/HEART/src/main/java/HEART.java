/* 
 * Axel Polin 2019
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 */

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
import fr.inria.papart.utils.*;
import fr.inria.papart.multitouch.*;
import fr.inria.papart.multitouch.detection.*;
import fr.inria.papart.multitouch.tracking.*;
import java.io.File;

import tech.lity.rea.colorconverter.*;
import Jama.Matrix;
import com.mkobos.pca_transform.*;

import ressources_module.HEARTressources;
import controller_module.HEARTcontroller;


@SuppressWarnings("serial")
public class VPEngine extends PApplet {
	Papart papart;
	TouchDetectionDepth fingerDetection;
	public VPEngineController vpEngineController;

	@Override
	public void setup() {
		papart = Papart.projection(this);
		fingerDetection = papart.loadTouchInput().initHandDetection();
		papart.startTracking();
		
		// Initialize Render and Controller.
		
		vpEngineController = new VPEngineController(this,fingerDetection);
		vpEngineView = new VPEngineView(...);
		
	    // Send Controller link to Render with vpEngineView.set
	    // And send Render link to controller with vpEngineController.set  
	    
		vpEngineView.set...
	}

	@Override
	public void settings() {
		// the application will be rendered in full screen, and using a 3Dengine.
		//fullScreen(P3D, 2);
		size(VPEngineModel.PROJECTION_WIDTH,VPEngineModel.PROJECTION_HEIGHT,"processing.opengl.PGraphics3D");
	}

	@Override
	public void draw() {
		//background(10);
		vpEngineController.levelManagerLoop();
	}

	/**
	 * @param passedArgs the command line arguments
	 */
	static public void main(String[] passedArgs) {
		String[] appletArgs = new String[] { VPEngine.class.getName() };
		PApplet.main(appletArgs);
	}
}

