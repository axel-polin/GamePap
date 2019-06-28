/* Axel Polin 2019
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
 * 
 * This file is the main file of the view game engine component
 */
package view_module;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import fr.inria.papart.multitouch.SkatoloLink;
import fr.inria.papart.multitouch.Touch;
import fr.inria.papart.multitouch.TouchList;
import fr.inria.papart.multitouch.detection.TouchDetectionDepth;
import fr.inria.papart.procam.TableScreen;
import processing.core.*;
import processing.data.XML;
import processing.opengl.PGraphicsOpenGL;
import tech.lity.rea.skatolo.Skatolo;
import tech.lity.rea.skatolo.events.ControlEvent;
import tech.lity.rea.skatolo.gui.controllers.HoverToggle;

import controller_module.VPEngineController;


public class VPEngineView extends TableScreen {
	//PApplet parent;
	private TouchDetectionDepth fingerDetection;
	private Skatolo skatolo;
	private VPEngineController vpEngineController;
		
	public PImage background;
	
	HoverToggle updateButton;
	
	//public VPEngineView(PApplet parent, VPEngineController vpEngineController, TouchDetectionDepth fingerDetection, float x, float y, float width, float height){
	public VPEngineView(VPEngineController vpEngineController, TouchDetectionDepth fingerDetection, float x, float y, float width, float height){
		super(x, y, width, height);
		this.parent = parent;
		this.vpEngineController = vpEngineController;
		this.fingerDetection = fingerDetection;
		
	}
	
	@Override
	public void setup() {
		
	//	background = parent.loadImage(Const.Common.BACKGROUND_INTERACTION_IMG_LINK);
	//	background.resize(Const.Common.INTERACTION_WIDTH_MILLIMETER, Const.Common.INTERACTION_HEIGHT_MILLIMETER);

		skatolo = new Skatolo(parent, this);
		skatolo.getMousePointer().disable();

		// Manual draw required with off screens.
		skatolo.setAutoDraw(false);

		//initializeMenu();
	}
	
	@Override
	public void drawOnPaper() {
		PGraphicsOpenGL g = getGraphics();
		currentGraphics = g;
		parent.clear();

		noTint();
		
		image(background, 0, 0);
		
		updateTouch();

		TouchList allTouchs = new TouchList();

		TouchList fingerTouchs = getTouchListFrom(fingerDetection);
		allTouchs.addAll(fingerTouchs);

		// Feed the touch to Skatolo to activate buttons.
		SkatoloLink.updateTouch(allTouchs, skatolo);

		// Draw the interface controllers of Skatolo
		skatolo.draw(g);

		// Draw finger touch for debugs
		drawFingerTouchs(g, fingerTouchs);
	}

	private void drawFingerTouchs(PGraphicsOpenGL g, TouchList fingerTouchs) {
		for (Touch t : fingerTouchs) {
			g.fill(102, 255, 255);
			g.ellipse(t.position.x, t.position.y, 10, 10);
			g.noFill();
		}
	}

	/*public void initializeMenu() {
		PImage[] select_imgs = { parent.loadImage(Const.Menu.SELECT_IMG_LINK),
				parent.loadImage(Const.Menu.SELECT_SEL_IMG_LINK), parent.loadImage(Const.Menu.SELECT_SEL_IMG_LINK) };
		PImage[] open_imgs = { parent.loadImage(Const.Menu.OPEN_IMG_LINK),
				parent.loadImage(Const.Menu.OPEN_SEL_IMG_LINK), parent.loadImage(Const.Menu.OPEN_SEL_IMG_LINK) };

		skatolo.addHoverToggle(Const.Menu.SELECT_BTN).setPosition(Const.Menu.SELECT_BTN_X, Const.Menu.SELECT_BTN_Y)
				.setImages(select_imgs).setSize(Const.Menu.MULSELECT_BTN_WIDTH, Const.Menu.MULSELECT_BTN_HEIGHT)
				.getCaptionLabel().setColor(color(0));
		skatolo.addHoverButton(Const.Menu.RESELECT_BTN)
				.setPosition(Const.Menu.RESELECT_BTN_X, Const.Menu.RESELECT_BTN_Y).setImages(common_btn_imgs)
				.setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);

	}*/

	public void TittleScreen(PImage[] ButtonsLinks){
		
		PFont tittle = parent.createFont("./Assets/Fonts/liberation_sans/LiberationSans-Regular.ttf",20,true);
		
		PGraphicsOpenGL g = getGraphics();
		String StartBtnTag = "startGame";
		int StartBtnX = 100;
		int StartBtnY = 100;
		
		g.fill(0, 102, 153);
		g.textSize(20);
		parent.textFont(tittle);
		parent.text("Pei et l'évadée de la nuit",100,150);
		
		skatolo.addHoverToggle("startGame").setPosition(100,100)
				.setImages(ButtonsLinks).setSize(200,28)
				.getCaptionLabel().setColor(color(0));
				
		/*skatolo.addHoverToggle("chapterSelection").setPosition(150,100)
				.setImages(ButtonsLinks).setSize(30,30)
				.getCaptionLabel().setColor(color(0));
				
		skatolo.addHoverToggle("credits").setPosition(200,100)
				.setImages(ButtonsLinks).setSize(30,30)
				.getCaptionLabel().setColor(color(0));*/
	}
	
	private void startGame(){
		System.out.println("We are in startgame!");		
	}
	
	private void chapterSelection(){
	}
	
	private void credits(){
	}

}
