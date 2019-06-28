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
package model_module;

public class VPEngineModel {
	
	public static final int ProjectionWidth = 1280;
	public static final int ProjectionHeight = 800;
	public static final int InteractionAreaWidth = 800;
	public static final int InteractionAreaHeight = 400;
	
	public class Levels { // This class contains all levels datas
		
		public class TittleScreen { // One class for one level which contain the data for one level.
	
			public static final String ButtonStartGameImageSEL = "./Assets/Buttons/TittleScreenNoSel2.png";
			public static final String ButtonStartGameImageNOSEL = "./Assets/Buttons/TittleScreenSel2.png";
			public static final String ButtonChapterSEL = "";
			public static final String ButtonChapterNOSEL = "";
			
			public static final String Background = "./Assets/Backgrounds/TittleScreen.jpg";
			
		}	
	}
}

