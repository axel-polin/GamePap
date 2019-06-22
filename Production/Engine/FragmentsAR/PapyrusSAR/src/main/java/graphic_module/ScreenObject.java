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
import papyrus_application.Compound;
import papyrus_application.Fragment;
import processing.core.*;
import processing.data.XML;
import processing.opengl.PGraphicsOpenGL;
import tech.lity.rea.skatolo.Skatolo;
import tech.lity.rea.skatolo.events.ControlEvent;
import tech.lity.rea.skatolo.gui.controllers.HoverToggle;

public class ScreenObject extends TableScreen {
	private TouchDetectionDepth fingerDetection;
	private Skatolo skatolo;
	private List<CompoundObject> compoundObj_List;
	private List<FragmentObject> fragmentObj_List;

	private List<String> listBoxItems;
	private List<String> listBoxCompoundItems;

	private String infoText;
	private boolean isLoading;

	private int state = 0;

	private final int DEFAULT_STATE = 0;
	private final int ROTATE_STATE = 1;
	private final int ZOOM_STATE = 2;
	private final int MULTIPLE_SELECT_STATE = 3;
	private final int UPDATE_COMPOUND_STATE = 4;
	private final int ROTATE_DEGREE = 10;

	private int selectedId = -1;

	PImage background;

	// For loading screen
	float endX = 50;
	float delay = 0;

	// For paging of fragments list box
	int numberOfItems = 7;
	int pageIndex = 0;
	int numberOfPages = 1;
	int start_Paging;
	int end_Paging;

	// For paging of compounds list box
	int cp_numberOfItems = 4;
	int cp_pageIndex = 0;
	int cp_numberOfPages = 1;
	int cp_start_Paging;
	int cp_end_Paging;

	// For checking select single fragment or compounds
	boolean isSelectedCompound = false;

	// For select multiple fragments
	private List<String> selectedInnerFragments = new ArrayList<String>();
	private List<String> selectedUpdatedFragments = new ArrayList<String>();

	HoverToggle updateButton;

	public ScreenObject(TouchDetectionDepth fingerDetection, float x, float y, float width, float height) {
		super(x, y, width, height);

		this.fingerDetection = fingerDetection;

		compoundObj_List = new ArrayList<CompoundObject>();
		fragmentObj_List = new ArrayList<FragmentObject>();
		listBoxItems = new ArrayList<String>();
		listBoxCompoundItems = new ArrayList<String>();
	}

	@Override
	public void setup() {

		background = parent.loadImage(Const.Common.BACKGROUND_INTERACTION_IMG_LINK);
		background.resize(Const.Common.INTERACTION_WIDTH_MILLIMETER, Const.Common.INTERACTION_HEIGHT_MILLIMETER);

		skatolo = new Skatolo(parent, this);
		skatolo.getMousePointer().disable();

		// Manual draw required with off screens.
		skatolo.setAutoDraw(false);

		initializeMenu();
	}

	private void initializeMenu() {
		PImage[] select_imgs = { parent.loadImage(Const.Menu.SELECT_IMG_LINK),
				parent.loadImage(Const.Menu.SELECT_SEL_IMG_LINK), parent.loadImage(Const.Menu.SELECT_SEL_IMG_LINK) };
		PImage[] open_imgs = { parent.loadImage(Const.Menu.OPEN_IMG_LINK),
				parent.loadImage(Const.Menu.OPEN_SEL_IMG_LINK), parent.loadImage(Const.Menu.OPEN_SEL_IMG_LINK) };
		PImage[] save_imgs = { parent.loadImage(Const.Menu.SAVE_IMG_LINK),
				parent.loadImage(Const.Menu.SAVE_SEL_IMG_LINK), parent.loadImage(Const.Menu.SAVE_SEL_IMG_LINK) };

		PImage[] left_decrease_imgs = { parent.loadImage(Const.Menu.LEFT_DECREASE_IMG_LINK),
				parent.loadImage(Const.Menu.LEFT_DECREASE_SEL_IMG_LINK),
				parent.loadImage(Const.Menu.LEFT_DECREASE_SEL_IMG_LINK) };
		PImage[] right_increase_imgs = { parent.loadImage(Const.Menu.RIGHT_INCREASE_IMG_LINK),
				parent.loadImage(Const.Menu.RIGHT_INCREASE_SEL_IMG_LINK),
				parent.loadImage(Const.Menu.RIGHT_INCREASE_SEL_IMG_LINK) };
		PImage[] zoom_in_imgs = { parent.loadImage(Const.Menu.ZOOM_IN_IMG_LINK),
				parent.loadImage(Const.Menu.ZOOM_IN_SEL_IMG_LINK), parent.loadImage(Const.Menu.ZOOM_IN_SEL_IMG_LINK) };
		PImage[] zoom_out_imgs = { parent.loadImage(Const.Menu.ZOOM_OUT_IMG_LINK),
				parent.loadImage(Const.Menu.ZOOM_OUT_SEL_IMG_LINK),
				parent.loadImage(Const.Menu.ZOOM_OUT_SEL_IMG_LINK) };
		PImage[] flip_imgs = { parent.loadImage(Const.Menu.FLIP_IMG_LINK),
				parent.loadImage(Const.Menu.FLIP_SEL_IMG_LINK), parent.loadImage(Const.Menu.FLIP_SEL_IMG_LINK) };
		PImage[] common_btn_imgs = { parent.loadImage(Const.Menu.COMMON_IMG_LINK),
				parent.loadImage(Const.Menu.COMMON_SEL_IMG_LINK), parent.loadImage(Const.Menu.COMMON_SEL_IMG_LINK) };
		PImage[] update_btn_imgs = { parent.loadImage(Const.Menu.UPDATE_IMG_LINK),
				parent.loadImage(Const.Menu.UPDATE_SEL_IMG_LINK), parent.loadImage(Const.Menu.UPDATE_SEL_IMG_LINK) };

		PImage[] next_btn_imgs = { parent.loadImage(Const.ListBox.NEXT_BTN_IMG_LINK),
				parent.loadImage(Const.ListBox.NEXT_BTN_SEL_IMG_LINK),
				parent.loadImage(Const.ListBox.NEXT_BTN_SEL_IMG_LINK) };
		PImage[] back_btn_imgs = { parent.loadImage(Const.ListBox.BACK_BTN_IMG_LINK),
				parent.loadImage(Const.ListBox.BACK_BTN_SEL_IMG_LINK),
				parent.loadImage(Const.ListBox.BACK_BTN_SEL_IMG_LINK) };

		skatolo.addHoverToggle(Const.Menu.SELECT_BTN).setPosition(Const.Menu.SELECT_BTN_X, Const.Menu.SELECT_BTN_Y)
				.setImages(select_imgs).setSize(Const.Menu.MULSELECT_BTN_WIDTH, Const.Menu.MULSELECT_BTN_HEIGHT)
				.getCaptionLabel().setColor(color(0));
		skatolo.addHoverButton(Const.Menu.RESELECT_BTN)
				.setPosition(Const.Menu.RESELECT_BTN_X, Const.Menu.RESELECT_BTN_Y).setImages(common_btn_imgs)
				.setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);

		skatolo.addHoverButton(Const.Menu.DECREASE_BTN)
				.setPosition(Const.Menu.DECREASE_BTN_X, Const.Menu.DECREASE_BTN_Y).setImages(left_decrease_imgs)
				.setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);
		skatolo.addHoverButton(Const.Menu.INCREASE_BTN)
				.setPosition(Const.Menu.INCREASE_BTN_X, Const.Menu.DECREASE_BTN_Y).setImages(right_increase_imgs)
				.setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);
		skatolo.addHoverButton(Const.Menu.ZOOMOUT_BTN).setPosition(Const.Menu.ZOOMOUT_BTN_X, Const.Menu.DECREASE_BTN_Y)
				.setImages(zoom_out_imgs).setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);
		skatolo.addHoverButton(Const.Menu.ZOOMIN_BTN).setPosition(Const.Menu.ZOOMIN_BTN_X, Const.Menu.DECREASE_BTN_Y)
				.setImages(zoom_in_imgs).setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);
		skatolo.addHoverButton(Const.Menu.FLIP_BTN).setPosition(Const.Menu.FLIP_BTN_X, Const.Menu.DECREASE_BTN_Y)
				.setImages(flip_imgs).setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);

		skatolo.addHoverButton(Const.Menu.OPEN_BTN).setPosition(80, 350).setImages(open_imgs).setSize(70, 40);
		skatolo.addHoverButton(Const.Menu.OPEN_COMPOUND_BTN).setPosition(Const.Menu.OPEN_COMPOUND_BTN_X, 310)
				.setImages(open_imgs).setSize(70, 40);
		skatolo.addHoverButton(Const.Menu.SAVE_COMPOUND_BTN).setPosition(Const.Menu.SAVE_COMPOUND_BTN_X, 350)
				.setImages(save_imgs).setSize(70, 40);

		skatolo.addHoverButton(Const.ListBox.NEXT_BTN)
				.setPosition(Const.ListBox.NEXT_BTN_OFFSET_X, Const.ListBox.NEXT_BTN_OFFSET_Y).setImages(next_btn_imgs)
				.setSize(Const.ListBox.NEXT_BTN_WIDTH, Const.ListBox.NEXT_BTN_HEIGHT);
		skatolo.addHoverButton(Const.ListBox.BACK_BTN)
				.setPosition(Const.ListBox.BACK_BTN_OFFSET_X, Const.ListBox.BACK_BTN_OFFSET_Y).setImages(back_btn_imgs)
				.setSize(Const.ListBox.NEXT_BTN_WIDTH, Const.ListBox.NEXT_BTN_HEIGHT);

		infoText = "Please select 'open' button to import data.";

		skatolo.addHoverToggle(Const.Menu.MULSELECT_BTN)
				.setPosition(Const.Menu.MULSELECT_BTN_X, Const.Menu.MULSELECT_BTN_Y).setImages(select_imgs)
				.setSize(Const.Menu.MULSELECT_BTN_WIDTH, Const.Menu.MULSELECT_BTN_HEIGHT).getCaptionLabel()
				.setColor(color(0));
		skatolo.addHoverButton(Const.Menu.CREATE_COMPOUND_BTN)
				.setPosition(Const.Menu.CREATE_COMPOUND_BTN_X, Const.Menu.MULSELECT_BTN_Y).setImages(common_btn_imgs)
				.setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);

		updateButton = skatolo.addHoverToggle(Const.Menu.UPDATE_COMPOUND_BTN);
		updateButton.setPosition(Const.Menu.UPDATE_COMPOUND_BTN_X, Const.Menu.UPDATE_COMPOUND_BTN_Y)
				.setImages(update_btn_imgs).setSize(45, 30).getCaptionLabel().setColor(color(0));

		skatolo.addHoverButton(Const.Menu.REMOVE_COMPOUND_BTN)
				.setPosition(Const.Menu.REMOVE_COMPOUND_BTN_X, Const.Menu.REMOVE_COMPOUND_BTN_Y)
				.setImages(common_btn_imgs).setSize(Const.Menu.DEFAULT_BTN_WIDTH, Const.Menu.DEFAULT_BTN_WIDTH);

		skatolo.addHoverButton(Const.CompoundListBox.NEXT_BTN)
				.setPosition(Const.CompoundListBox.NEXT_BTN_OFFSET_X, Const.CompoundListBox.NEXT_BTN_OFFSET_Y)
				.setImages(next_btn_imgs)
				.setSize(Const.CompoundListBox.NEXT_BTN_WIDTH, Const.CompoundListBox.NEXT_BTN_HEIGHT);
		skatolo.addHoverButton(Const.CompoundListBox.BACK_BTN)
				.setPosition(Const.CompoundListBox.BACK_BTN_OFFSET_X, Const.CompoundListBox.BACK_BTN_OFFSET_Y)
				.setImages(back_btn_imgs)
				.setSize(Const.CompoundListBox.NEXT_BTN_WIDTH, Const.CompoundListBox.NEXT_BTN_HEIGHT);
	}

	private void initializeButtonsOnListBox() {
		// Clear all buttons on list box
		clearButtonsOnListBox();

		PImage[] display_toggle_imgs = { parent.loadImage(Const.ListBox.DISPLAY_TOGGLE_IMG_LINK),
				parent.loadImage(Const.ListBox.DISPLAY_TOGGLE_SEL_IMG_LINK),
				parent.loadImage(Const.ListBox.DISPLAY_TOGGLE_SEL_IMG_LINK) };

		float itemY = Const.ListBox.DISPLAY_TOGGLE_INIT_Y;

		calculateStartEndPaging();
		for (int i = start_Paging; i < end_Paging; i++) {

			// Find corresponding fragment
			for (int j = 0; j < fragmentObj_List.size(); j++) {
				FragmentObject fragmentObject = fragmentObj_List.get(j);
				if (fragmentObject.getFragmentName() == listBoxItems.get(i)) {
					boolean isDisplay = fragmentObject.isDisplay();
					skatolo.addHoverToggle(Const.ListBox.DISPLAY_TOGGLE_BTN + i)
							.setPosition(Const.ListBox.DISPLAY_TOGGLE_INIT_X, itemY).setImages(display_toggle_imgs)
							.setSize(25, 25).setId(i).setValue(isDisplay);
					itemY += Const.ListBox.ITEM_HEIGHT + Const.ListBox.ITEM_PADDING;
				}
			}

		}
	}

	private void initializeButtonsOnListBoxCompound() {
		// Clear all buttons on list box compound
		clearButtonsOnListBoxCompound();

		PImage[] display_toggle_imgs = { parent.loadImage(Const.ListBox.DISPLAY_TOGGLE_IMG_LINK),
				parent.loadImage(Const.ListBox.DISPLAY_TOGGLE_SEL_IMG_LINK),
				parent.loadImage(Const.ListBox.DISPLAY_TOGGLE_SEL_IMG_LINK) };

		float itemY = Const.CompoundListBox.DISPLAY_TOGGLE_INIT_Y;

		calculateStartEndPaging_CP();

		for (int i = cp_start_Paging; i < cp_end_Paging; i++) {

			// Find corresponding compound
			for (int j = 0; j < compoundObj_List.size(); j++) {
				CompoundObject compoundObj = compoundObj_List.get(j);
				if (compoundObj.getName() == listBoxCompoundItems.get(i)) {
					boolean isDisplay = compoundObj.isDisplay();
					skatolo.addHoverToggle(Const.CompoundListBox.DISPLAY_TOGGLE_BTN + i)
							.setPosition(
									Const.CompoundListBox.DISPLAY_TOGGLE_INIT_X + Const.CompoundListBox.ITEM_PADDING,
									itemY)
							.setImages(display_toggle_imgs)
							.setSize(Const.CompoundListBox.DISPLAY_TOGGLE_WIDTH,
									Const.CompoundListBox.DISPLAY_TOGGLE_WIDTH)
							.setId(Const.CompoundListBox.START_COMPOUND_ID + i).setValue(isDisplay);
					itemY += Const.CompoundListBox.ITEM_HEIGHT + Const.CompoundListBox.ITEM_PADDING;
				}
			}

		}
	}

	private void clearButtonsOnListBox() {
		for (int i = 0; i < listBoxItems.size(); i++) {
			skatolo.remove(Const.ListBox.DISPLAY_TOGGLE_BTN + i);
		}
	}

	private void clearButtonsOnListBoxCompound() {
		for (int i = 0; i < listBoxCompoundItems.size(); i++) {
			skatolo.remove(Const.CompoundListBox.DISPLAY_TOGGLE_BTN + i);
		}
	}

	private void CreateCompound() throws CloneNotSupportedException {
		createOneCompound(fragmentObj_List, null);
	}

	private void createOneCompound(List<FragmentObject> fragmentObjList, String compoundName)
			throws CloneNotSupportedException {
		if (selectedInnerFragments.size() != 0) {
			if (compoundName == null) {
				// String uniqueID = UUID.randomUUID().toString();
				DateFormat dateFormat = new SimpleDateFormat("HHmmss");
				Date date = new Date();
				String uniqueID = dateFormat.format(date);
				compoundName = "CP" + uniqueID;
			}

			// Create a CompoundObject and clone FragmentObjects to add into innerFragments
			// of the CompoundObject
			CompoundObject compoundObj = new CompoundObject(compoundName);
			for (int i = 0; i < selectedInnerFragments.size(); i++) {
				for (int j = 0; j < fragmentObjList.size(); j++) {
					if (fragmentObjList.get(j).getFragmentName() == selectedInnerFragments.get(i)) {
						FragmentObject fragmentObj = fragmentObjList.get(j);
						compoundObj.addFragment((FragmentObject) fragmentObj.clone());
					}
				}
			}

			Compound compound = createCompoundInPapyrus_Application(compoundObj, fragmentObjList, false);

			// Attach the Compound to the CompoundObject above
			compoundObj.setCompound(compound);

			compoundObj_List.add(compoundObj);
			listBoxCompoundItems.add(compoundName);

			// Calculate number of pages for paging of compound list box
			handleNumsOfPagesPagingOfCompoundLB();

			// Create Skatalo toggle button for each compound item in list box
			initializeButtonsOnListBoxCompound();
		}
	}

	private void handleNumsOfPagesPagingOfCompoundLB() {
		if (listBoxCompoundItems.size() != 0) {
			if ((listBoxCompoundItems.size() % cp_numberOfItems) != 0) {
				cp_numberOfPages = listBoxCompoundItems.size() / cp_numberOfItems + 1;
			} else {
				cp_numberOfPages = listBoxCompoundItems.size() / cp_numberOfItems;
			}
			cp_pageIndex = 0;
		}

	}

	private Compound createCompoundInPapyrus_Application(CompoundObject compoundObj,
			List<FragmentObject> inputFragmentObj_List, boolean forUpdateCompound) throws CloneNotSupportedException {
		// Create a Compound and create Fragments from FragmentObjects, then calculate
		// posX, posY of them,
		// and add into Compound.
		Compound compound = new Compound();

		if (forUpdateCompound) {
			for (int l = 0; l < selectedUpdatedFragments.size(); l++) {
				for (int k = 0; k < compoundObj.innerFragments.size(); k++) {
					if (((FragmentObject) compoundObj.innerFragments.get(k))
							.getFragmentName() == selectedUpdatedFragments.get(l)) {
						FragmentObject fragmentObj = (FragmentObject) compoundObj.innerFragments.get(k);

						Fragment fragment = fragmentObj.getFragment();
						fragment.setPosXPosYFromOffsetCompound(compoundObj.getOffset(), fragmentObj.getOffset(),
								fragmentObj.angle.z, fragmentObj.scale);

						compound.addFragment(fragment);
					}
				}
			}

			for (int i = 0; i < selectedInnerFragments.size(); i++) {
				for (int j = 0; j < inputFragmentObj_List.size(); j++) {
					if (inputFragmentObj_List.get(j).getFragmentName() == selectedInnerFragments.get(i)
							&& !checkExistedInselectedInnerFragments(selectedInnerFragments.get(i),
									selectedUpdatedFragments)) {
						FragmentObject fragmentObj = inputFragmentObj_List.get(j);

						Fragment fragment = fragmentObj.getFragment();
						fragment.setPosXPosYFromOffsetCompound(compoundObj.getOffset(), fragmentObj.getOffset(),
								fragmentObj.angle.z, fragmentObj.scale);

						compound.addFragment(fragment);
					}
				}
			}

		} else {
			for (int i = 0; i < selectedInnerFragments.size(); i++) {
				for (int j = 0; j < inputFragmentObj_List.size(); j++) {
					if (inputFragmentObj_List.get(j).getFragmentName() == selectedInnerFragments.get(i)) {
						FragmentObject fragmentObj = inputFragmentObj_List.get(j);

						Fragment fragment = fragmentObj.getFragment();
						fragment.setPosXPosYFromOffsetCompound(compoundObj.getOffset(), fragmentObj.getOffset(),
								fragmentObj.angle.z, fragmentObj.scale);

						compound.addFragment(fragment);
					}
				}
			}
		}

		return compound;
	}

	private void UpdateCompound(boolean isPressed) throws CloneNotSupportedException {

		// Only works when user selected one compound in 'SINGLE SELECTION' mode
		if (selectedId != -1) {
			if (isSelectedCompound) {
				if (isPressed) {
					state = UPDATE_COMPOUND_STATE;
					selectedInnerFragments = new ArrayList<String>();
					selectedUpdatedFragments = new ArrayList<String>();

					// Add name of selected inner fragments of the compound into
					// 'selectedInnerFragments' array first
					CompoundObject compoundObj = compoundObj_List.get(selectedId);
					for (int i = 0; i < compoundObj.innerFragments.size(); i++) {
						FragmentObject fragmentObj = (FragmentObject) compoundObj.innerFragments.get(i);
						String fragmentObjName = fragmentObj.getFragmentName();

						if (!checkExistedInselectedInnerFragments(fragmentObjName, selectedUpdatedFragments))
							selectedUpdatedFragments.add(fragmentObjName);
					}

				} else {
					state = ROTATE_STATE;

					// Merge selectedUpdatedFragments into selectedInnerFragments
					if (selectedUpdatedFragments.size() != 0) {
						for (int j = 0; j < selectedUpdatedFragments.size(); j++) {
							if (!checkExistedInselectedInnerFragments(selectedUpdatedFragments.get(j),
									selectedInnerFragments))
								selectedInnerFragments.add(selectedUpdatedFragments.get(j));
						}
					}

					if (selectedInnerFragments.size() == 0) {
						// In case user did not select any fragments, the compound will be removed
						removeCompoundObj();
					} else {
						// Update the compoundObject
						CompoundObject compoundObj = compoundObj_List.get(selectedId);

						// if innerFragment does not exist in selectedInnerFragments, the compoundObject
						// will remove it
						List<DocumentObject> innerFragments = new ArrayList<DocumentObject>();
						List<String> tempList = new ArrayList<String>();
						for (int j = 0; j < selectedInnerFragments.size(); j++) {
							for (int i = 0; i < compoundObj.innerFragments.size(); i++) {
								FragmentObject innerFragmentObj = (FragmentObject) compoundObj.innerFragments.get(i);
								String name = innerFragmentObj.getFragmentName();

								if (selectedInnerFragments.get(j) == name) {
									// Add innerFragment of the compound first
									innerFragments.add(innerFragmentObj.clone());
								} else {
									// Add the name into temporary list
									if (!checkExistedInselectedInnerFragments(selectedInnerFragments.get(j), tempList))
										tempList.add(selectedInnerFragments.get(j));
								}
							}
						}

						for (int i = 0; i < compoundObj.innerFragments.size(); i++) {
							FragmentObject innerFragmentObj = (FragmentObject) compoundObj.innerFragments.get(i);
							String name = innerFragmentObj.getFragmentName();

							if (checkExistedInselectedInnerFragments(name, tempList))
								tempList.remove(name);

						}

						// Add new fragments from fragmentObj_List
						for (int k = 0; k < fragmentObj_List.size(); k++) {
							for (int j = 0; j < tempList.size(); j++) {
								if (fragmentObj_List.get(k).getFragmentName() == tempList.get(j)) {
									fragmentObj_List.get(k).setSelected(false);

									FragmentObject fragmentObj = (FragmentObject) fragmentObj_List.get(k).clone();
									fragmentObj.setSelected(true);
									fragmentObj.setInsideCompound(true);

									innerFragments.add((FragmentObject) fragmentObj);
								}
							}
						}

						compoundObj.innerFragments = innerFragments;
						compoundObj.caculateOffsetWidthHeight();

						// Create compound in package papyrus_application
						Compound compound = createCompoundInPapyrus_Application(compoundObj, fragmentObj_List, true);

						// Attach the Compound to the CompoundObject above
						compoundObj.setCompound(compound);

						// Calculate number of pages for paging of compound list box
						handleNumsOfPagesPagingOfCompoundLB();

						// Create Skatalo toggle button for each compound item in list box
						initializeButtonsOnListBoxCompound();
					}
				}
			}
		}
	}

	private void RemoveCompound() {
		removeCompoundObj();
	}

	private void removeCompoundObj() {
		if (selectedId != -1) {
			if (isSelectedCompound) {

				String name = compoundObj_List.get(selectedId).getName();

				// Remove buttons in list box compound
				clearButtonsOnListBoxCompound();
				listBoxCompoundItems.remove(name);

				// Remove compound in compoundObj_List
				compoundObj_List.remove(selectedId);

				// Calculate number of pages for paging of compound list box
				handleNumsOfPagesPagingOfCompoundLB();

				// Initialize compound list box
				initializeButtonsOnListBoxCompound();

				// For reselect other fragment or compound
				reSelectFragment_Compound();
			}
		}
	}

	private void MultipleSelect(boolean isPressed) {
		if (isPressed) {
			state = MULTIPLE_SELECT_STATE;
		} else {
			// Remove selection on selected fragments
			if (selectedInnerFragments.size() != 0) {
				for (int j = 0; j < selectedInnerFragments.size(); j++) {
					for (int i = 0; i < fragmentObj_List.size(); i++) {

						if (fragmentObj_List.get(i).getFragmentName() == selectedInnerFragments.get(j)) {
							fragmentObj_List.get(i).setSelected(false);
						}
					}
				}

			}
			state = DEFAULT_STATE;
		}
		selectedInnerFragments = new ArrayList<String>();
	}

	private void Select(boolean isPressed) {

		if (isPressed) {
			state = ROTATE_STATE;

		} else {
			// Remove fragment was selected before when user click on the button again
			if (selectedId != -1) {
				if (!isSelectedCompound) {
					fragmentObj_List.get(selectedId).setSelected(false);
				} else {
					compoundObj_List.get(selectedId).setSelected(false);
					;
				}

				state = DEFAULT_STATE;
			}
		}

		selectedId = -1;
		isSelectedCompound = false;
		updateButton.setValue(false);
	}

	private void CPNext() {
		// Next of paging of compounds list box
		if (cp_pageIndex >= cp_numberOfPages - 1)
			return;
		cp_pageIndex += 1;
		initializeButtonsOnListBoxCompound();
	}

	private void CPBack() {
		// Back of paging of compounds list box
		if (cp_pageIndex <= 0)
			return;
		cp_pageIndex -= 1;
		initializeButtonsOnListBoxCompound();
	}

	private void Next() {
		// Next of paging
		if (pageIndex >= numberOfPages - 1)
			return;
		pageIndex += 1;
		initializeButtonsOnListBox();
	}

	private void Back() {
		// Back of paging
		if (pageIndex <= 0)
			return;
		pageIndex -= 1;
		initializeButtonsOnListBox();
	}

	private void Open() {
		// open dialog to select input file to load Fragment List
		//parent.selectInput("Select a file to process:", "fileSelected");
		parent.thread("runThread_ReadFragmentInput");
	}

	private void OpenCompound() {
		// Open dialog to select file to load Compound List
		parent.selectInput("Select a file to process:", "fileSelected_Compound");
	}

	private void SaveCompound() {
		// Open dialog to select folder to save Compound list
		// For open a dialog:  parent.selectFolder("Select a folder to process:", "folderSelected_Compound");
		saveOutputFile_Compound(Const.Common.WORKSPACE_FOLDER_LINK);
	}

	private void Increase() {
		if (selectedId != -1) {
			float angle = PApplet.radians(ROTATE_DEGREE);
			if (!isSelectedCompound) {
				FragmentObject fragmentObj = fragmentObj_List.get(selectedId);
				fragmentObj.rotateByAngle(angle);
			}
		}
	}

	private void Decrease() {
		if (selectedId != -1) {
			float angle = PApplet.radians(-ROTATE_DEGREE);
			if (!isSelectedCompound) {
				FragmentObject fragmentObj = fragmentObj_List.get(selectedId);
				fragmentObj.rotateByAngle(angle);
			}
		}
	}

	private void ZoomIn() {
		if (selectedId != -1) {
			if (!isSelectedCompound) {
				FragmentObject fragmentObj = fragmentObj_List.get(selectedId);
				fragmentObj.zoomInOut(true);
			}
		}
	}

	private void ZoomOut() {
		if (selectedId != -1) {
			if (!isSelectedCompound) {
				FragmentObject fragmentObj = fragmentObj_List.get(selectedId);
				fragmentObj.zoomInOut(false);
			}
		}
	}

	private void Flip() {
		if (selectedId != -1) {
			if (!isSelectedCompound) {
				FragmentObject fragmentObj = fragmentObj_List.get(selectedId);
				if (fragmentObj.hasVersoImg())
					fragmentObj.setVerso(!fragmentObj.isVerso());
			}
		}
	}

	private void ReSelect() {
		reSelectFragment_Compound();
		updateButton.setValue(false);
	}

	private void reSelectFragment_Compound() {
		if (selectedId != -1) {
			fragmentObj_List.get(selectedId).setSelected(false);
			selectedId = -1;
			isSelectedCompound = false;
		}
	}

	public String createCompoundListXML() {
		String content = Const.OutputFileConsts.XML_DOCUMENT_START;

		for (int i = 0; i < compoundObj_List.size(); i++) {
			CompoundObject compoundObj = compoundObj_List.get(i);

			content += "\t" + Const.OutputFileConsts.COMPOUND_NODE_START + Const.InputFileConsts.NAME_ATTR + "=\""
					+ compoundObj.getName() + "\">\n";
			Compound compound = compoundObj.getCompound();
			for (int j = 0; j < compound.getFragmentListSize(); j++) {
				Fragment fragment = compound.getFragmentListByIndex(j);

				String folderName = fragment.getName();
				String fileName = fragment.getFileName();
				String maskName = fragment.getMaskFileName();
				String versoFileName = fragment.getVerso_FileName();
				String versoMaskName = fragment.getVerso_MaskFileName();
				String angle = Float.toString(fragment.getAngle());
				String scale = Float.toString(fragment.getScale());
				String posX = Float.toString(fragment.getPosX());
				String posY = Float.toString(fragment.getPosY());

				String workspace_Path = Const.Common.WORKSPACE_FOLDER_LINK;
				
				content += "\t\t" + Const.OutputFileConsts.FRAGMENT_NODE_START + Const.InputFileConsts.FOLDER_NAME_ATTR
						+ "=\"" + folderName + "\" " 
						+ Const.InputFileConsts.FILE_NAME_ATTR + "=\"" + fileName.replace(workspace_Path, "") + "\" "
						+ Const.InputFileConsts.MASK_NAME_ATTR + "=\"" + maskName.replace(workspace_Path, "") + "\" "
						+ Const.InputFileConsts.VERSO_FILE_NAME_ATTR + "=\"" + versoFileName.replace(workspace_Path, "") + "\" "
						+ Const.InputFileConsts.VERSO_MASK_NAME_ATTR + "=\"" + versoMaskName.replace(workspace_Path, "") + "\" "
						+ Const.InputFileConsts.ANGLE_ATTR + "=\"" + angle + "\" " + Const.InputFileConsts.SCALE_ATTR
						+ "=\"" + scale + "\" " + Const.InputFileConsts.POSX_ATTR + "=\"" + posX + "\" "
						+ Const.InputFileConsts.POSY_ATTR + "=\"" + posY + "\" " + ">"
						+ Const.OutputFileConsts.FRAGMENT_NODE_END + "\n";

			}
			content += "\t" + Const.OutputFileConsts.COMPOUND_NODE_END + "\n";
		}

		content += Const.OutputFileConsts.XML_DOCUMENT_END;

		return content;
	}

	public void readInputFile_Compound(String inputFile) throws CloneNotSupportedException {
		setLoading(true);

		selectedId = -1;
		state = DEFAULT_STATE;

		float default_posX = 20;
		float default_posY = 20;
		int delta = 20;

		XML xml = parent.loadXML(inputFile);

		for (int i = 0; i < compoundObj_List.size(); i++) {
			System.out.println("Compound: " + compoundObj_List.get(i).getName());
		}

		XML[] compounds = xml.getChildren(Const.InputFileConsts.COMPOUND_NODE);
		for (int i = 0; i < compounds.length; i++) {
			List<FragmentObject> tempFragmentObj_List = new ArrayList<FragmentObject>();
			String name = compounds[i].getString(Const.InputFileConsts.NAME_ATTR);
			XML[] fragments = compounds[i].getChildren(Const.InputFileConsts.FRAGMENT_NODE);

			// Check if the compound was existed in compoundObj_List, do not add it
			if (!checkExistedInCompoundObjList(name)) {
				for (int j = 0; j < fragments.length; j++) {
					FragmentObject fragmentObject = createOneFragmentFromFile(default_posX, default_posY, fragments[j]);
					fragmentObject.setDisplay(true);
					tempFragmentObj_List.add(fragmentObject);

					selectedInnerFragments.add(fragmentObject.getFragmentName());
				}

				createOneCompound(tempFragmentObj_List, name);

				default_posX += delta;
				default_posY += delta;
			}

			selectedInnerFragments = new ArrayList<String>();
		}

		setLoading(false);
	}

	public void readInputFile(String inputfileName) {
		setLoading(true);

		// Reset to initial value of the application when open the file.
		clearButtonsOnListBox();
		fragmentObj_List.clear();
		listBoxItems.clear();

		selectedId = -1;
		state = DEFAULT_STATE;

		float default_posX = 50;
		float default_posY = 50;
		int delta = 20;

		XML xml = parent.loadXML(inputfileName);

		XML[] fragments = xml.getChildren(Const.InputFileConsts.FRAGMENT_NODE);

		for (int i = 0; i < fragments.length; i++) {

			FragmentObject fragmentObject = createOneFragmentFromFile(default_posX, default_posY, fragments[i]);
			fragmentObj_List.add(fragmentObject);
			listBoxItems.add(fragmentObject.getFragmentName());

			default_posX += delta;
			default_posY += delta;
		}

		// Calculate number of pages for paging
		numberOfPages = listBoxItems.size() / numberOfItems + 1;
		pageIndex = 0;

		// Create Skatalo toggle button for each fragment item in list box
		initializeButtonsOnListBox();

		setLoading(false);
	}

	public FragmentObject createOneFragmentFromFile(float default_posX, float default_posY, XML fragmentXML) {
		float posX = default_posX;
		float posY = default_posY;
		String versoFileName = null;
		String versoMaskName = null;
		float angle;
		float scale;

		String workspace_Path = Const.Common.WORKSPACE_FOLDER_LINK;
		String folderName = fragmentXML.getString(Const.InputFileConsts.FOLDER_NAME_ATTR);
		String fileName = workspace_Path + fragmentXML.getString(Const.InputFileConsts.FILE_NAME_ATTR);
		String maskName = workspace_Path + fragmentXML.getString(Const.InputFileConsts.MASK_NAME_ATTR);

		if (fragmentXML.getString(Const.InputFileConsts.POSX_ATTR) != null) {
			posX = default_posX + Float.parseFloat(fragmentXML.getString(Const.InputFileConsts.POSX_ATTR));
		}

		if (fragmentXML.getString(Const.InputFileConsts.POSY_ATTR) != null) {
			posY = default_posY + Float.parseFloat(fragmentXML.getString(Const.InputFileConsts.POSY_ATTR));
		}

		if (fragmentXML.getString(Const.InputFileConsts.VERSO_FILE_NAME_ATTR) != null) {
			versoFileName = workspace_Path + fragmentXML.getString(Const.InputFileConsts.VERSO_FILE_NAME_ATTR);
		}
		if (fragmentXML.getString(Const.InputFileConsts.VERSO_MASK_NAME_ATTR) != null) {
			versoMaskName = workspace_Path + fragmentXML.getString(Const.InputFileConsts.VERSO_MASK_NAME_ATTR);
		}

		FragmentObject fragmentObject = null;
		if (versoFileName != null && versoMaskName != null) {
			fragmentObject = new FragmentObject(folderName, fileName, maskName, versoFileName, versoMaskName, posX,
					posY, 156, 234);
		} else {
			fragmentObject = new FragmentObject(folderName, fileName, maskName, posX, posY, 156, 234);
		}

		fragmentObject.loadImage(parent);

		if (fragmentXML.getString(Const.InputFileConsts.ANGLE_ATTR) != null) {
			angle = Float.parseFloat(fragmentXML.getString(Const.InputFileConsts.ANGLE_ATTR));
			fragmentObject.setAngleZ(angle);
		}

		if (fragmentXML.getString(Const.InputFileConsts.SCALE_ATTR) != null) {
			scale = Float.parseFloat(fragmentXML.getString(Const.InputFileConsts.SCALE_ATTR));
			fragmentObject.setScale(scale);
		}

		return fragmentObject;
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

		// Draw area to display info or borders, etc..
		drawOtherGraphs(g);

		// Draw list box to show fragment list
		drawListBox(g, fingerTouchs);

		// Draw Compound list box to show compound list
		drawCompoundListBox(g, fingerTouchs);

		// Draw the interface controllers of Skatolo
		skatolo.draw(g);

		// Draw Fragments and anything related to papyrus
		drawPapyrusFragments(g, fingerTouchs);

		// Draw finger touch for debugs
		drawFingerTouchs(g, fingerTouchs);

		// Draw loading screen
		if (isLoading) {
			drawLoadingScreen(g, fingerTouchs);
		}
	}

	private void drawFingerTouchs(PGraphicsOpenGL g, TouchList fingerTouchs) {
		for (Touch t : fingerTouchs) {
			g.fill(102, 255, 255);
			g.ellipse(t.position.x, t.position.y, 10, 10);
			g.noFill();
		}
	}

	// function controlEvent will be invoked with every value change
	// in any registered controller
	public void controlEvent(ControlEvent theEvent) {
		
		boolean isDisplay = theEvent.getController().getValue() == 1.00f ? true : false;
		int listBoxItem_ID = theEvent.getId();

		if (listBoxItem_ID != -1) {
			// Check event from fragment or compound list box item
			if (listBoxItem_ID < Const.CompoundListBox.START_COMPOUND_ID) {
				// Find corresponding fragment to set display or hidden on it
				String fragmentName = listBoxItems.get(listBoxItem_ID);
				for (int i = 0; i < fragmentObj_List.size(); i++) {
					if (fragmentObj_List.get(i).getFragmentName() == fragmentName) {
						fragmentObj_List.get(i).setDisplay(isDisplay);
					}
				}
			} else {
				// Find corresponding compound to set display or hidden on it
				int realID = listBoxItem_ID - Const.CompoundListBox.START_COMPOUND_ID;
				String compoundName = listBoxCompoundItems.get(realID);
				for (int i = 0; i < compoundObj_List.size(); i++) {
					if (compoundObj_List.get(i).getName() == compoundName) {
						compoundObj_List.get(i).setDisplay(isDisplay);
					}
				}
			}
		}
	}

	private void drawLoadingScreen(PGraphicsOpenGL g, TouchList fingerTouchs) {
		float width = 800;
		float height = 390;

		strokeWeight(3);
		rect(0, 0, 800, 400);
		filter(PApplet.BLUR, 4);

		// Draw bar
		fill(0, 0, 0, 100);
		noStroke();
		rect(210, height / 2 + 10, 410, 50, 10, 10, 10, 10);// Bar Shadow

		fill(255);
		stroke(0);
		strokeWeight(6);
		strokeJoin(PApplet.BEVEL);
		rect(200, height / 2, 410, 50, 10, 10, 10, 10);// Bar Outline

		// Draw progress
		fill(6, 203, 53);
		stroke(255);
		strokeWeight(3);

		for (int x = 205; x <= endX; x = x + 40) {
			rect(x, height / 2 + 5, 40, 40);
		}

		if (this.parent.millis() - delay > 150) {
			endX = endX + 40;
			delay = this.parent.millis(); // creates a delay in the loop of drawing the progress bar
		}

		if (endX >= 610) {
			endX = 0; // once the green filler reaches the end of the bar, it resets.
		}

		// Draw text loading
		g.textSize(20);
		g.fill(0);
		g.text("Loading...", width / 2 - 50, height / 2 - 50, 200, 200);
	}

	public void saveOutputFile_Compound(String outputFolder) {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
		Date date = new Date();

		String content = createCompoundListXML();
		PrintWriter output = parent.createWriter(outputFolder + "/" + "Compounds_" + dateFormat.format(date) + ".xml");
		output.println(content);
		output.flush();
		output.close();

		System.out.println("Compound list was saved.");
	}

	private void drawCompoundListBox(PGraphicsOpenGL g, TouchList fingerTouchs) {
		float[] compoundLB_Offset = { Const.CompoundListBox.OFFSET_LISTBOX_X, Const.CompoundListBox.OFFSET_LISTBOX_Y };

		g.noFill();
		g.stroke(50);
		g.strokeWeight(2);
		g.rect(compoundLB_Offset[0], compoundLB_Offset[1], Const.CompoundListBox.WIDTH, Const.CompoundListBox.HEIGHT,
				Const.CompoundListBox.RADIAN, Const.CompoundListBox.RADIAN, Const.CompoundListBox.RADIAN,
				Const.CompoundListBox.RADIAN);

		// Draw page index
		g.textSize(13);
		g.fill(0);
		String indexText = "#" + Integer.toString(cp_pageIndex + 1);
		g.text(indexText, Const.CompoundListBox.INDEX_OFFSET_X, Const.CompoundListBox.INDEX_OFFSET_Y,
				Const.CompoundListBox.INDEX_WIDTH, Const.CompoundListBox.INDEX_WIDTH);

		float[] item_Offset = { compoundLB_Offset[0] + Const.CompoundListBox.ITEM_PADDING,
				compoundLB_Offset[1] + Const.CompoundListBox.ITEM_PADDING };

		g.strokeWeight(1);
		calculateStartEndPaging_CP();

		for (int i = cp_start_Paging; i < cp_end_Paging; i++) {
			g.fill(250);
			g.rect(item_Offset[0], item_Offset[1], Const.CompoundListBox.ITEM_WIDTH, Const.CompoundListBox.ITEM_HEIGHT,
					Const.CompoundListBox.ITEM_RADIAN, Const.CompoundListBox.ITEM_RADIAN,
					Const.CompoundListBox.ITEM_RADIAN, Const.CompoundListBox.ITEM_RADIAN);

			// Find corresponding compound
			for (int j = 0; j < compoundObj_List.size(); j++) {
				CompoundObject compoundObj = compoundObj_List.get(j);
				if (compoundObj.getName() == listBoxCompoundItems.get(i)) {
					g.fill(0);
					g.textSize(10);
					String compoundName = compoundObj.getName();
					g.text(compoundName,
							item_Offset[0] + Const.CompoundListBox.ITEM_PADDING * 2
									+ Const.CompoundListBox.DISPLAY_TOGGLE_WIDTH,
							item_Offset[1] + Const.CompoundListBox.ITEM_PADDING, Const.CompoundListBox.ITEM_TEXT_WIDTH,
							Const.CompoundListBox.ITEM_TEXT_HEIGHT);
					break;
				}

			}

			g.textSize(15);
			item_Offset[1] += Const.CompoundListBox.ITEM_HEIGHT + Const.CompoundListBox.ITEM_PADDING;
		}
		g.noFill();

	}

	private void drawListBox(PGraphicsOpenGL g, TouchList fingerTouchs) {
		float[] listBox_Offset = { Const.ListBox.OFFSET_LISTBOX_X, Const.ListBox.OFFSET_LISTBOX_Y };

		g.stroke(50);
		g.strokeWeight(2);
		g.rect(listBox_Offset[0], listBox_Offset[1], Const.ListBox.WIDTH, Const.ListBox.HEIGHT, Const.ListBox.RADIAN,
				Const.ListBox.RADIAN, Const.ListBox.RADIAN, Const.ListBox.RADIAN);

		// Draw page index
		g.textSize(15);
		g.fill(0);
		String indexText = "#" + Integer.toString(pageIndex + 1);
		g.text(indexText, Const.ListBox.INDEX_OFFSET_X, Const.ListBox.INDEX_OFFSET_Y, Const.ListBox.INDEX_WIDTH,
				Const.ListBox.INDEX_WIDTH);
		float[] item_Offset = { listBox_Offset[0] + Const.ListBox.ITEM_PADDING,
				listBox_Offset[1] + Const.ListBox.ITEM_PADDING };
		g.noFill();

		// Draw items in list box
		g.strokeWeight(1);
		calculateStartEndPaging();

		for (int i = start_Paging; i < end_Paging; i++) {
			g.fill(250);
			g.rect(item_Offset[0], item_Offset[1], Const.ListBox.ITEM_WIDTH, Const.ListBox.ITEM_HEIGHT,
					Const.ListBox.ITEM_RADIAN, Const.ListBox.ITEM_RADIAN, Const.ListBox.ITEM_RADIAN,
					Const.ListBox.ITEM_RADIAN);

			// Find corresponding fragment
			for (int j = 0; j < fragmentObj_List.size(); j++) {
				FragmentObject fragmentObject = fragmentObj_List.get(j);
				if (fragmentObject.getFragmentName() == listBoxItems.get(i)) {
					PImage thumbnail = fragmentObject.getBoundingImg();
					image(thumbnail, item_Offset[0] + Const.ListBox.ITEM_THUMBNAIL_PADDING,
							item_Offset[1] + Const.ListBox.ITEM_THUMBNAIL_PADDING, Const.ListBox.ITEM_THUMBNAIL_WIDTH,
							Const.ListBox.ITEM_THUMBNAIL_HEIGHT);

					g.fill(0);
					String fragmentName = fragmentObject.getFragmentName();
					g.text(fragmentName,
							item_Offset[0] + 2 * Const.ListBox.ITEM_THUMBNAIL_PADDING
									+ Const.ListBox.ITEM_THUMBNAIL_WIDTH,
							item_Offset[1] + Const.ListBox.ITEM_THUMBNAIL_PADDING, Const.ListBox.ITEM_TEXT_WIDTH,
							Const.ListBox.ITEM_TEXT_HEIGHT);

					break;
				}

			}

			item_Offset[1] += Const.ListBox.ITEM_HEIGHT + Const.ListBox.ITEM_PADDING;
		}
	}

	private void calculateStartEndPaging() {
		start_Paging = pageIndex * numberOfItems;

		if (pageIndex == listBoxItems.size() / numberOfItems) {
			end_Paging = listBoxItems.size();
		} else {
			end_Paging = start_Paging + numberOfItems;
		}
	}

	private void calculateStartEndPaging_CP() {
		cp_start_Paging = cp_pageIndex * cp_numberOfItems;

		if (cp_pageIndex == listBoxCompoundItems.size() / cp_numberOfItems) {
			cp_end_Paging = listBoxCompoundItems.size();
		} else {
			cp_end_Paging = cp_start_Paging + cp_numberOfItems;
		}
	}

	private void drawPapyrusFragments(PGraphicsOpenGL g, TouchList fingerTouchs) {
		if (fragmentObj_List.size() != 0 || compoundObj_List.size() != 0) {
			noFill();
			String tempText = "";

			switch (state) {
			case ROTATE_STATE:
				;
			case ZOOM_STATE:
				// Get id of fragment which was selected
				getSelectedFragment_Compound(fingerTouchs);
				// Checking the fragment was selected or not, and then rotating or zooming
				movingAndRotatingFragment_Compound(fingerTouchs);
				break;
			case MULTIPLE_SELECT_STATE:
				// Get name of fragments which were selected, all will be stored in a string
				// array `selectedInnerfragments`
				getMultipleFragments(fingerTouchs);
				break;
			case UPDATE_COMPOUND_STATE:
				// Get name of fragments which were selected and name of inner fragments were
				// inside the compound,
				// all will be stored in a string array `selectedInnerfragments`
				getFragmentsForCompound(fingerTouchs);
				break;
			case DEFAULT_STATE:
				// For moving fragments or compounds when they were tapped
				// Check whether fragments or Compounds are tapped or not
				movingTappedFragment_Compound(g, fingerTouchs);
				break;
			}

			// Draw fragments
			for (int i = 0; i < fragmentObj_List.size(); i++) {
				FragmentObject fragmentObject = fragmentObj_List.get(i);
				tempText += fragmentObject.drawObject(g, fingerTouchs);
			}

			// Draw compounds
			for (int i = 0; i < compoundObj_List.size(); i++) {
				CompoundObject compoundObject = compoundObj_List.get(i);
				tempText += compoundObject.drawObject(g, fingerTouchs);
			}

			setInfoText(tempText);
		}
	}

	private void drawOtherGraphs(PGraphicsOpenGL g) {
		// Draw region to show info
		g.stroke(50);
		g.strokeWeight(2);
		g.fill(102, 255, 255);
		g.rect(Const.Menu.INFO_OFFSET_X, Const.Menu.INFO_OFFSET_Y, Const.Menu.INFO_WIDTH, Const.Menu.INFO_HEIGHT,
				Const.Menu.INFO_RADIAN, Const.Menu.INFO_RADIAN, Const.Menu.INFO_RADIAN, Const.Menu.INFO_RADIAN);

		g.textSize(10);
		g.fill(50);
		g.text(infoText, Const.Menu.INFO_TEXT_OFFSET_X, Const.Menu.INFO_TEXT_OFFSET_Y, Const.Menu.INFO_TEXT_WIDTH,
				Const.Menu.INFO_TEXT_HEIGHT);

		// Shadow of menu
		fill(0, 0, 0, 100);
		noStroke();
		g.rect(Const.Menu.MENU_REC_1_OFFSET_X + Const.Menu.MENU_SHADOW,
				Const.Menu.MENU_REC_1_OFFSET_Y + Const.Menu.MENU_SHADOW, Const.Menu.MENU_REC_1_WIDTH,
				Const.Menu.MENU_REC_1_HEIGHT, Const.Menu.MENU_REC_1_RADIAN, Const.Menu.MENU_REC_1_RADIAN,
				Const.Menu.MENU_REC_1_RADIAN, Const.Menu.MENU_REC_1_RADIAN);
		g.rect(Const.Menu.MENU_REC_2_OFFSET_X + Const.Menu.MENU_SHADOW,
				Const.Menu.MENU_REC_2_OFFSET_Y + Const.Menu.MENU_SHADOW, Const.Menu.MENU_REC_2_WIDTH,
				Const.Menu.MENU_REC_2_HEIGHT, Const.Menu.MENU_REC_2_RADIAN, Const.Menu.MENU_REC_2_RADIAN,
				Const.Menu.MENU_REC_2_RADIAN, Const.Menu.MENU_REC_2_RADIAN);

		// Border of menu
		g.fill(255);
		g.stroke(50);
		g.strokeWeight(2);
		g.rect(Const.Menu.MENU_REC_1_OFFSET_X, Const.Menu.MENU_REC_1_OFFSET_Y, Const.Menu.MENU_REC_1_WIDTH,
				Const.Menu.MENU_REC_1_HEIGHT, Const.Menu.MENU_REC_1_RADIAN, Const.Menu.MENU_REC_1_RADIAN,
				Const.Menu.MENU_REC_1_RADIAN, Const.Menu.MENU_REC_1_RADIAN);
		g.rect(Const.Menu.MENU_REC_2_OFFSET_X, Const.Menu.MENU_REC_2_OFFSET_Y, Const.Menu.MENU_REC_2_WIDTH,
				Const.Menu.MENU_REC_2_HEIGHT, Const.Menu.MENU_REC_2_RADIAN, Const.Menu.MENU_REC_2_RADIAN,
				Const.Menu.MENU_REC_2_RADIAN, Const.Menu.MENU_REC_2_RADIAN);

		// Draw text
		g.fill(0, 102, 153);
		g.textSize(8);
		g.text(Const.Menu.SINGLE_SELECTION_LABEL, Const.Menu.MENU_REC_1_OFFSET_X + Const.Menu.GROUP_TEXT_PAD,
				Const.Menu.MENU_REC_1_OFFSET_Y + Const.Menu.GROUP_TEXT_PAD);
		g.text(Const.Menu.FOR_FRAGMENT_LABEL, Const.Menu.GROUP_FOR_FRAGMENT_X,
				Const.Menu.MENU_REC_1_OFFSET_Y + Const.Menu.GROUP_TEXT_PAD);
		g.text(Const.Menu.FOR_COMPOUND_LABEL, Const.Menu.GROUP_FOR_COMPOUND_X,
				Const.Menu.MENU_REC_1_OFFSET_Y + Const.Menu.GROUP_TEXT_PAD);
		g.text(Const.Menu.MULTI_SELECTION_LABEL, Const.Menu.MENU_REC_2_OFFSET_X + Const.Menu.GROUP_TEXT_PAD,
				Const.Menu.MENU_REC_2_OFFSET_Y + Const.Menu.GROUP_TEXT_PAD);

		// Draw vertical line
		g.line(Const.Menu.LINE_1_X, Const.Menu.LINE_1_Y1, Const.Menu.LINE_1_X, Const.Menu.LINE_1_Y2);
		g.line(Const.Menu.LINE_2_X, Const.Menu.LINE_1_Y1, Const.Menu.LINE_2_X, Const.Menu.LINE_1_Y2);
		g.textSize(8);
		g.fill(50);
		g.text(Const.Menu.RESELECT_BTN_LABEL, Const.Menu.RESELECT_BTN_LABEL_X, Const.Menu.RESELECT_BTN_LABEL_Y);
		g.text(Const.Menu.CREATE_COMPOUND_BTN_LABEL, Const.Menu.CREATE_COMPOUND_BTN_LABEL_X,
				Const.Menu.CREATE_COMPOUND_BTN_LABEL_Y);
		g.text(Const.Menu.UPDATE_COMPOUND_BTN_LABEL, Const.Menu.UPDATE_COMPOUND_BTN_LABEL_X,
				Const.Menu.UPDATE_COMPOUND_BTN_LABEL_Y);
		g.text(Const.Menu.REMOVE_COMPOUND_BTN_LABEL, Const.Menu.REMOVE_COMPOUND_BTN_LABEL_X,
				Const.Menu.REMOVE_COMPOUND_BTN_LABEL_Y);

		g.noFill();
	}

	private void getFragmentsForCompound(TouchList fingerTouchs) {
		if (selectedId != -1 && isSelectedCompound) {
			// Update fragments be inside Compound
			getMultipleInnerFragments(fingerTouchs);

			// Get fragments in Fragment Object list
			getMultipleFragments(fingerTouchs);
		}
	}

	private boolean checkExistedInCompoundObjList(String compoundName) {
		boolean isDuplicated = false;

		for (int i = 0; i < compoundObj_List.size(); i++) {
			CompoundObject compoundObj = compoundObj_List.get(i);
			String compoundObj_Name = new String(compoundObj.getName());
			String inputCompoundName = new String(compoundName);

			if (inputCompoundName.equals(compoundObj_Name)) {
				isDuplicated = true;
			}
		}

		return isDuplicated;
	}

	private boolean checkExistedInselectedInnerFragments(String fragmentObjectName, List<String> selectedFragments) {
		boolean isDuplicated = false;

		for (int j = 0; j < selectedFragments.size(); j++) {
			if (fragmentObjectName == selectedFragments.get(j))
				isDuplicated = true;
		}

		return isDuplicated;
	}

	private void movingAndRotatingFragment_Compound(TouchList fingerTouchs) {
		if (selectedId != -1) {
			if (!isSelectedCompound) {
				FragmentObject fragmentObj = fragmentObj_List.get(selectedId);
				// fragmentObj.handlingTappingCirclesAndRotating(fingerTouchs);
				fragmentObj.handlingForMovingAndRotating(fingerTouchs);
			} else {
				CompoundObject compoundObj = compoundObj_List.get(selectedId);
				compoundObj.checkCompoundIsTapped(fingerTouchs, new ArrayList<Touch>());
			}
		}
	}

	private void movingTappedFragment_Compound(PGraphicsOpenGL g, TouchList fingerTouchs) {
		List<Touch> used_Points = new ArrayList<Touch>();

		// Moving compounds
		for (int i = compoundObj_List.size() - 1; i >= 0; i--) {

			CompoundObject compoundObj = compoundObj_List.get(i);
			boolean isTapped = compoundObj.checkCompoundIsTapped(fingerTouchs, used_Points);

			if (isTapped) {
				for (int l = 0; l < compoundObj.innerFragments.size(); l++) {
					FragmentObject fragmentObj = (FragmentObject) compoundObj.innerFragments.get(l);

					// Add touch points were used into used points array
					for (int k = 0; k < fragmentObj.touch_Points.size(); k++) {
						boolean isDuplicated = false;
						for (int j = 0; j < used_Points.size(); j++) {
							if (used_Points.get(j).id == fragmentObj.touch_Points.get(k).id) {
								isDuplicated = true;
							}
						}
						if (!isDuplicated)
							used_Points.add(fragmentObj.touch_Points.get(k));
					}
				}

				// Move compounds to be on top
				compoundObj_List.remove(compoundObj);
				compoundObj_List.add(compoundObj);

			}
		}

		// Moving fragments
		for (int i = fragmentObj_List.size() - 1; i >= 0; i--) {

			FragmentObject fragmentObj = fragmentObj_List.get(i);
			boolean isTapped = fragmentObj.checkFragmentIsTapped(fingerTouchs, used_Points);

			if (isTapped) {

				// Add touch points were used into used points array
				for (int k = 0; k < fragmentObj.touch_Points.size(); k++) {
					boolean isDuplicated = false;
					for (int j = 0; j < used_Points.size(); j++) {
						if (used_Points.get(j).id == fragmentObj.touch_Points.get(k).id) {
							isDuplicated = true;
						}
					}
					if (!isDuplicated)
						used_Points.add(fragmentObj.touch_Points.get(k));
				}

				// Move fragment to be on top
				fragmentObj_List.remove(fragmentObj);
				fragmentObj_List.add(fragmentObj);
			}
		}
	}

	private void getSelectedFragment_Compound(TouchList fingerTouchs) {
		// Check the fragment or compound is selected
		if (selectedId == -1) {
			for (int j = compoundObj_List.size() - 1; j >= 0; j--) {
				CompoundObject compoundObject = compoundObj_List.get(j);
				if (compoundObject.checkCompoundIsSelected(fingerTouchs)) {
					selectedId = j;
					isSelectedCompound = true;
					compoundObject.setTapped(false);

					return;
				}
			}

			for (int i = fragmentObj_List.size() - 1; i >= 0; i--) {
				FragmentObject fragmentObject = fragmentObj_List.get(i);
				fragmentObject.setSelected(false);
				if (fragmentObject.isDisplay() && fragmentObject.checkFragmentIsSelected(fingerTouchs)) {
					selectedId = i;
					fragmentObject.setTapped(false);

					return;
				}
			}
		}
	}

	private void getMultipleInnerFragments(TouchList fingerTouchs) {
		// Get current selected compound
		CompoundObject compoundObj = compoundObj_List.get(selectedId);

		for (int i = 0; i < compoundObj.innerFragments.size(); i++) {
			FragmentObject fragmentObject = (FragmentObject) compoundObj.innerFragments.get(i);
			String fragmentObjectName = fragmentObject.getFragmentName();

			if (fragmentObject.checkFragmentIsSelected(fingerTouchs)) {

				// Add into selectedInnerFragments in the way which will avoid duplicated names
				if (selectedUpdatedFragments.size() == 0) {
					selectedUpdatedFragments.add(fragmentObjectName);
				} else {
					if (!checkExistedInselectedInnerFragments(fragmentObjectName, selectedUpdatedFragments))
						selectedUpdatedFragments.add(fragmentObjectName);
				}
			} else {
				selectedUpdatedFragments.remove(fragmentObjectName);
			}

		}
	}

	private void getMultipleFragments(TouchList fingerTouchs) {

		for (int i = fragmentObj_List.size() - 1; i >= 0; i--) {
			FragmentObject fragmentObject = fragmentObj_List.get(i);
			String fragmentObjectName = fragmentObject.getFragmentName();

			if (fragmentObject.isDisplay()) {

				if (fragmentObject.checkFragmentIsSelected(fingerTouchs)) {
					// Add into selectedInnerFragments in the way which will avoid duplicated names
					if (selectedInnerFragments.size() == 0) {
						selectedInnerFragments.add(fragmentObjectName);
					} else {
						if (!checkExistedInselectedInnerFragments(fragmentObjectName, selectedInnerFragments))
							selectedInnerFragments.add(fragmentObjectName);
					}
				} else {
					selectedInnerFragments.remove(fragmentObjectName);
				}

			}
		}
	}

	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}

	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

}
