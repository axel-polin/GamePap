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

public final class Const {
	private Const() {}
	
	//Group InputFileConst for constants related to file was input
    public static final class InputFileConsts {
        private InputFileConsts() {} // prevent instantiation

        public static final String INPUT_FILE_NAME = "/Fragment_Input.xml";
        public static final String FOLDER_NODE= "Folder";
        public static final String PATH_ATTR = "path";

        public static final String FOLDER_NAME_ATTR = "folderName";
        public static final String FRAGMENT_NODE = "Fragment";
        public static final String POSX_ATTR = "posX";
        public static final String POSY_ATTR = "posY";
        public static final String ANGLE_ATTR = "angle";
        public static final String SCALE_ATTR = "scale";
        public static final String FILE_NAME_ATTR = "fileName";
        public static final String MASK_NAME_ATTR = "maskName";
        public static final String VERSO_FILE_NAME_ATTR = "versoFileName";
        public static final String VERSO_MASK_NAME_ATTR = "versoMaskName";
        public static final String COMPOUND_NODE = "Compound";
    	public static final String NAME_ATTR= "name";
    }
    
    public static final class OutputFileConsts {
    	private OutputFileConsts() {}
    	
    	public static final String XML_DOCUMENT_START = "<?xml version=\"1.0\"?>\n" + 
    			"<Document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
    			"xsi:noNamespaceSchemaLocation=\"FragmentInput.xsd\">";
    	public static final String XML_DOCUMENT_END = "</Document>";
    	public static final String FOLDER_NODE_START= "<Folder ";
    	public static final String FOLDER_NODE_END= " /> ";
    	public static final String COMPOUND_NODE_START= "<Compound ";
    	public static final String COMPOUND_NODE_END= "</Compound>";
    	public static final String FRAGMENT_NODE_START= "<Fragment ";
    	public static final String FRAGMENT_NODE_END= "</Fragment>";
    	
    }
    
    public static final class Common{
    	private Common() {} 
    	
    	public static final String BACKGROUND_IMG_LINK= "./Images/wood_desk_background.jpg";
    	public static final int PROJECTION_WIDTH = 1280;
    	public static final int PROJECTION_HEIGHT = 800;
    	public static final int INTERACTION_WIDTH_MILLIMETER = 800;
    	public static final int INTERACTION_HEIGHT_MILLIMETER = 400;
    	public static final int SCREENOBJECT_X = -400;
    	public static final int SCREENOBJECT_Y = -200;
    	public static final String BACKGROUND_INTERACTION_IMG_LINK= "./Images/blueprint_background.jpg";
    	public static final String WORKSPACE_FOLDER_LINK= "./Workspace";
    }
    
    public static final class Menu{
    	private Menu() {} 
    	
    	public static final String SELECT_BTN = "Select";
    	public static final String DECREASE_BTN = "Decrease";
    	public static final String INCREASE_BTN = "Increase";
    	public static final String ZOOMOUT_BTN = "ZoomOut";
    	public static final String ZOOMIN_BTN = "ZoomIn";
    	public static final String FLIP_BTN = "Flip";
    	public static final String OPEN_BTN = "Open";
    	public static final String SAVE_BTN = "Save";
    	public static final String RESELECT_BTN = "ReSelect";
    	public static final String MULSELECT_BTN = "MultipleSelect";
    	public static final String CREATE_COMPOUND_BTN = "CreateCompound";
    	public static final String UPDATE_COMPOUND_BTN = "UpdateCompound";
    	public static final String REMOVE_COMPOUND_BTN = "RemoveCompound";
    	public static final String OPEN_COMPOUND_BTN = "OpenCompound";
    	public static final String SAVE_COMPOUND_BTN = "SaveCompound";
    	
    	public static final String SELECT_IMG_LINK = "./Images/off-toggle.png";
    	public static final String SELECT_SEL_IMG_LINK = "./Images/on-toggle.png";
    	public static final String OPEN_IMG_LINK = "./Images/open-btn.png";
    	public static final String OPEN_SEL_IMG_LINK = "./Images/open-btn-selected.png";
      	public static final String SAVE_IMG_LINK = "./Images/save-btn.png";
    	public static final String SAVE_SEL_IMG_LINK = "./Images/save-btn-selected.png";
    	
    	public static final String LEFT_DECREASE_IMG_LINK = "./Images/left_decrease.png";
    	public static final String LEFT_DECREASE_SEL_IMG_LINK = "./Images/left_decrease_selected.png";
    	public static final String RIGHT_INCREASE_IMG_LINK = "./Images/right_increase.png";
    	public static final String RIGHT_INCREASE_SEL_IMG_LINK = "./Images/right_increase_selected.png";
    	public static final String ZOOM_IN_IMG_LINK = "./Images/zoom_in.png";
    	public static final String ZOOM_IN_SEL_IMG_LINK = "./Images/zoom_in_selected.png";
    	public static final String ZOOM_OUT_IMG_LINK = "./Images/zoom_out.png";
    	public static final String ZOOM_OUT_SEL_IMG_LINK = "./Images/zoom_out_selected.png";
    	public static final String FLIP_IMG_LINK = "./Images/flip-btn.png";
    	public static final String FLIP_SEL_IMG_LINK = "./Images/flip-btn-selected.png";
       	public static final String COMMON_IMG_LINK = "./Images/common-btn.png";
    	public static final String COMMON_SEL_IMG_LINK = "./Images/common-btn-selected.png";
       	public static final String UPDATE_IMG_LINK = "./Images/update-btn.png";
    	public static final String UPDATE_SEL_IMG_LINK = "./Images/update-btn-selected.png";
    	
    	public static final float INFO_OFFSET_X = 660;
    	public static final float INFO_OFFSET_Y = 10;
    	public static final float INFO_WIDTH = 120;
    	public static final float INFO_HEIGHT = 180;
    	public static final float INFO_RADIAN = 10;
    	public static final float INFO_TEXT_PADDING = 10;
    	public static final float INFO_TEXT_OFFSET_X = INFO_OFFSET_X + INFO_TEXT_PADDING;
    	public static final float INFO_TEXT_OFFSET_Y = INFO_OFFSET_Y + INFO_TEXT_PADDING;
    	public static final float INFO_TEXT_WIDTH = INFO_WIDTH - INFO_TEXT_PADDING *2;
    	public static final float INFO_TEXT_HEIGHT = INFO_HEIGHT - INFO_TEXT_PADDING *2;
    	
       	public static final float MENU_REC_1_OFFSET_X = 180;
    	public static final float MENU_REC_1_OFFSET_Y = 330;
    	public static final float MENU_REC_1_WIDTH = 380;
    	public static final float MENU_REC_1_HEIGHT = 60;
    	public static final float MENU_REC_1_RADIAN = 10;
    	
       	public static final float MENU_REC_2_OFFSET_X = 460;
    	public static final float MENU_REC_2_OFFSET_Y = 265;
    	public static final float MENU_REC_2_WIDTH = 100;
    	public static final float MENU_REC_2_HEIGHT = MENU_REC_1_HEIGHT;
    	public static final float MENU_REC_2_RADIAN = MENU_REC_1_RADIAN;
    	
    	public static final float MENU_SHADOW = 5;
    	public static final float GROUP_TEXT_PAD = 10;
    	
    	public static final float MULSELECT_BTN_X = MENU_REC_2_OFFSET_X + GROUP_TEXT_PAD;
    	public static final float MULSELECT_BTN_Y = MENU_REC_2_OFFSET_Y + GROUP_TEXT_PAD + 5;
    	public static final int MULSELECT_BTN_WIDTH = 44;
    	public static final int MULSELECT_BTN_HEIGHT = 27;
    	
    	public static final int DEFAULT_BTN_WIDTH = 30;
    	public static final float CREATE_COMPOUND_BTN_X = MULSELECT_BTN_X + MULSELECT_BTN_WIDTH + 5;
    	public static final float CREATE_COMPOUND_BTN_Y = MULSELECT_BTN_Y;
    	public static final String CREATE_COMPOUND_BTN_LABEL = "Create";
    	public static final float CREATE_COMPOUND_BTN_LABEL_X = CREATE_COMPOUND_BTN_X;
    	public static final float CREATE_COMPOUND_BTN_LABEL_Y = CREATE_COMPOUND_BTN_Y + DEFAULT_BTN_WIDTH + 5;
    	
    	public static final float SELECT_BTN_X = MENU_REC_1_OFFSET_X + GROUP_TEXT_PAD;
    	public static final float SELECT_BTN_Y = MENU_REC_1_OFFSET_Y + GROUP_TEXT_PAD + 5;
    	public static final float RESELECT_BTN_X = SELECT_BTN_X + MULSELECT_BTN_WIDTH + 5;
    	public static final float RESELECT_BTN_Y = SELECT_BTN_Y;
    	public static final String RESELECT_BTN_LABEL = "Change";
    	public static final float RESELECT_BTN_LABEL_X = RESELECT_BTN_X;
    	public static final float RESELECT_BTN_LABEL_Y = RESELECT_BTN_Y + DEFAULT_BTN_WIDTH + 5;
    	
    	public static final float LINE_1_X = RESELECT_BTN_LABEL_X + DEFAULT_BTN_WIDTH + 5;
    	public static final float LINE_1_Y1 = MENU_REC_1_OFFSET_Y;
    	public static final float LINE_1_Y2 = MENU_REC_1_OFFSET_Y + MENU_REC_1_HEIGHT;
    	
    	public static final String SINGLE_SELECTION_LABEL = "SINGLE SELECTION";
    	public static final String FOR_FRAGMENT_LABEL = "FOR FRAGMENT";
    	public static final String FOR_COMPOUND_LABEL = "FOR COMPOUND";
    	public static final String MULTI_SELECTION_LABEL = "MULTI SELECTION";
    	
    	public static final float GROUP_FOR_FRAGMENT_X = LINE_1_X + GROUP_TEXT_PAD;
    	
    	public static final float DECREASE_BTN_X = GROUP_FOR_FRAGMENT_X;
    	public static final float DECREASE_BTN_Y = MENU_REC_1_OFFSET_Y + 15;
    	public static final float INCREASE_BTN_X = DECREASE_BTN_X + DEFAULT_BTN_WIDTH + 5;
    	public static final float ZOOMOUT_BTN_X = INCREASE_BTN_X + DEFAULT_BTN_WIDTH + 5;
    	public static final float ZOOMIN_BTN_X = ZOOMOUT_BTN_X + DEFAULT_BTN_WIDTH + 5;
    	public static final float FLIP_BTN_X = ZOOMIN_BTN_X + DEFAULT_BTN_WIDTH + 5;
    	
    	public static final float LINE_2_X = FLIP_BTN_X + DEFAULT_BTN_WIDTH + 5;
    	public static final float GROUP_FOR_COMPOUND_X = LINE_2_X + GROUP_TEXT_PAD;
    	public static final float UPDATE_COMPOUND_BTN_X = GROUP_FOR_COMPOUND_X;
    	public static final float UPDATE_COMPOUND_BTN_Y = MENU_REC_1_OFFSET_Y + + GROUP_TEXT_PAD + 5;
    	public static final float UPDATE_COMPOUND_BTN_WIDTH = 45;
    	public static final float UPDATE_COMPOUND_BTN_HEIGHT = 30;
    	public static final String UPDATE_COMPOUND_BTN_LABEL = "Update";
    	public static final float UPDATE_COMPOUND_BTN_LABEL_X = UPDATE_COMPOUND_BTN_X + 5;
    	public static final float UPDATE_COMPOUND_BTN_LABEL_Y = UPDATE_COMPOUND_BTN_Y + UPDATE_COMPOUND_BTN_HEIGHT + 5;
    	
    	public static final float REMOVE_COMPOUND_BTN_X = UPDATE_COMPOUND_BTN_X + UPDATE_COMPOUND_BTN_WIDTH + 5;
    	public static final float REMOVE_COMPOUND_BTN_Y = UPDATE_COMPOUND_BTN_Y;
    	public static final String REMOVE_COMPOUND_BTN_LABEL = "Remove";
    	public static final float REMOVE_COMPOUND_BTN_LABEL_X = REMOVE_COMPOUND_BTN_X;
    	public static final float REMOVE_COMPOUND_BTN_LABEL_Y = UPDATE_COMPOUND_BTN_LABEL_Y;
    	
    	public static final float OPEN_COMPOUND_BTN_X = MENU_REC_1_OFFSET_X + MENU_REC_1_WIDTH + 25;
    	public static final float SAVE_COMPOUND_BTN_X = OPEN_COMPOUND_BTN_X;
    }
    
    public static final class ListBox{
    	private ListBox() {} 
    	
    	public static final float OFFSET_LISTBOX_X = 10;
    	public static final float OFFSET_LISTBOX_Y = 10;
    	public static final float WIDTH = 140;
    	public static final float HEIGHT = 320;
    	public static final float RADIAN = 10;
    	public static final float ITEM_WIDTH = 130;
    	public static final float ITEM_HEIGHT = 30;
    	public static final float ITEM_RADIAN = 3;
    	public static final float ITEM_PADDING = 5;
    	public static final float ITEM_THUMBNAIL_WIDTH = 20;
    	public static final float ITEM_THUMBNAIL_HEIGHT = 20;
    	public static final float ITEM_THUMBNAIL_PADDING = 5;
    	public static final float ITEM_TEXT_WIDTH = 70;
    	public static final float ITEM_TEXT_HEIGHT = 30;
    	public static final float DISPLAY_TOGGLE_INIT_X = ITEM_WIDTH - 10;
    	public static final float DISPLAY_TOGGLE_INIT_Y = OFFSET_LISTBOX_Y + ITEM_PADDING + 5;
    	public static final String DISPLAY_TOGGLE_BTN = "displayToggle_";
    	
    	public static final String NEXT_BTN = "Next";
    	public static final String BACK_BTN = "Back";
    	public static final int NEXT_BTN_WIDTH =30;
    	public static final int NEXT_BTN_HEIGHT =30;
    	public static final float NEXT_BTN_OFFSET_X = OFFSET_LISTBOX_X + WIDTH - NEXT_BTN_WIDTH - 10;
    	public static final float NEXT_BTN_OFFSET_Y = OFFSET_LISTBOX_Y + HEIGHT - NEXT_BTN_HEIGHT - 10;
    	public static final float BACK_BTN_OFFSET_X = NEXT_BTN_OFFSET_X - NEXT_BTN_WIDTH - 5;
    	public static final float BACK_BTN_OFFSET_Y = OFFSET_LISTBOX_Y + HEIGHT - NEXT_BTN_HEIGHT - 10;
    	public static final float INDEX_OFFSET_X = OFFSET_LISTBOX_X + 25;
    	public static final float INDEX_OFFSET_Y = OFFSET_LISTBOX_Y + HEIGHT - NEXT_BTN_HEIGHT - 10;
    	public static final int INDEX_WIDTH = 50;
   
    	public static final String DISPLAY_TOGGLE_IMG_LINK = "./Images/check-btn.png";
    	public static final String DISPLAY_TOGGLE_SEL_IMG_LINK = "./Images/check-btn-selected.png";
    	public static final String NEXT_BTN_IMG_LINK = "./Images/next-btn.png";
    	public static final String NEXT_BTN_SEL_IMG_LINK = "./Images/next-btn-selected.png";
       	public static final String BACK_BTN_IMG_LINK = "./Images/back-btn.png";
    	public static final String BACK_BTN_SEL_IMG_LINK = "./Images/back-btn-selected.png";
    }
    
    public static final class CompoundListBox{
    	public static final float OFFSET_LISTBOX_X = 660;
    	public static final float OFFSET_LISTBOX_Y = 200;
    	
    	public static final float WIDTH = 120;
    	public static final float HEIGHT = 180;
    	public static final float RADIAN = 10;
    	
    	public static final float ITEM_WIDTH = 110;
    	public static final float ITEM_HEIGHT = 22;
    	public static final float ITEM_RADIAN = 3;
    	public static final float ITEM_PADDING = 5;
    	public static final float ITEM_TEXT_WIDTH = 80;
    	public static final float ITEM_TEXT_HEIGHT = 20;
    	
    	public static final String NEXT_BTN = "CPNext";
    	public static final String BACK_BTN = "CPBack";
    	public static final int NEXT_BTN_WIDTH =30;
    	public static final int NEXT_BTN_HEIGHT =30;
    	public static final float NEXT_BTN_OFFSET_X = OFFSET_LISTBOX_X + WIDTH - NEXT_BTN_WIDTH - 10;
    	public static final float NEXT_BTN_OFFSET_Y = OFFSET_LISTBOX_Y + HEIGHT - NEXT_BTN_HEIGHT - 10;
    	public static final float BACK_BTN_OFFSET_X = NEXT_BTN_OFFSET_X - NEXT_BTN_WIDTH - 5;
    	public static final float BACK_BTN_OFFSET_Y = OFFSET_LISTBOX_Y + HEIGHT - NEXT_BTN_HEIGHT - 10;
    	public static final float INDEX_OFFSET_X = OFFSET_LISTBOX_X + 15;
    	public static final float INDEX_OFFSET_Y = OFFSET_LISTBOX_Y + HEIGHT - NEXT_BTN_HEIGHT - 10;
    	public static final int INDEX_WIDTH = 50;
    	
    	public static final int DISPLAY_TOGGLE_WIDTH = 20;
    	public static final float DISPLAY_TOGGLE_INIT_X = OFFSET_LISTBOX_X + ITEM_PADDING;
    	public static final float DISPLAY_TOGGLE_INIT_Y = OFFSET_LISTBOX_Y + ITEM_PADDING;
    	public static final String DISPLAY_TOGGLE_BTN = "displayToggle_Compound_";
    	public static final int START_COMPOUND_ID = 500;
    }
}
