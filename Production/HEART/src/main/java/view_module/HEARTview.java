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
	
	private Skatolo skatolo;
	//private VPEngineController vpEngineController;
		
	public PImage background;
	
	HoverToggle updateButton;
	
	public VPEngineView(VPEngineController vpEngineController, float x, float y, float width, float height){
		super(x, y, width, height);
		//this.parent = parent;
		//this.vpEngineController = vpEngineController;
		
	}
	
	public void setEngineCtrl(VPEngineController vpEngineController){
		this.vpEngineController = vpEngineController;
	}
	
	@Override
	public void setup() {
		
		skatolo = new Skatolo(parent, this);
		skatolo.getMousePointer().disable();

		// Manual draw required with off screens.
		skatolo.setAutoDraw(false);
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

	public void TittleScreen(PImage[] ButtonsLinks){
		
		PFont tittle = parent.createFont("./Assets/Fonts/liberation_sans/LiberationSans-Regular.ttf",20,true);
		
		//PGraphicsOpenGL g = getGraphics();
		String StartBtnTag = "startGame";
		int StartBtnX = 100;
		int StartBtnY = 100;
		
		fill(0, 102, 153);
		textSize(20);
		parent.textFont(tittle);
		parent.text("Pei et l'évadée de la nuit",100,150);
		
		createHoverButton("startGame",100,100,200,28);
		
	}
	
	private void createHoverButton(String name,int pos_x,int pops_y,PImage[] img,int width,int height){
		skatolo.addHoverButton(name).setPosition(x,y)
				.setImages(img).setSize(width,height)
				.getCaptionLabel();
	}
	
	private void createHoverToggle(String name,int pos_x,int pops_y,PImage[] img,int width,int height,int color){
			skatolo.addHoverToggle(name).setPosition(x,y)
				.setImages(img).setSize(width,height)
				.getCaptionLabel().setColor(color(color));
	}
	private void startGame(){
		System.out.println("We are in startgame!");		
	}
	
	private void chapterSelection(){
	}
	
	private void credits(){
	}

}
