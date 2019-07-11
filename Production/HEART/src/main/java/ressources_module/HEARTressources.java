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
package ressources_module;

public class VPEngineModel {
	
	public static final int PROJECTION_WIDTH = 1280;
	public static final int PROJECTION_HEIGHT = 800;
	public static final int INTERACTION_AREA_WIDTH = 800;
	public static final int INTERACTION_AREA_HEIGHT = 400;
	public static final int SCREENOBJECT_X = -400;
    public static final int SCREENOBJECT_Y = -200;
	
	public class Levels { // This class contains all levels datas
		
		public class TittleScreen { // One class for one level which contain the data for one level.
			
			public static final int NUMBER_BUTTONS = 3;
			public static final int BUTTONS_X = 100;
			public static final int BUTTON_Y_OFFSET = 50;
			public static final String TITTLE = "";
			public static final String BUTTON_STARTGAME_IMAGE_SEL = "./Assets/Buttons/TittleScreenNoSel2.png";
			public static final String BUTTON_STARTGAME_IMAGE_NOSEL = "./Assets/Buttons/TittleScreenSel2.png";
					
			public static final String BACKGROUND = "./Assets/Backgrounds/TittleScreen.jpg";
					
			
		}	
	}
}

