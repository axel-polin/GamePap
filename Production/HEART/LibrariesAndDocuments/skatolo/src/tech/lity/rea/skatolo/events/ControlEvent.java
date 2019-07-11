/* 
 *  skatolo is a processing gui library.
 * 
 * Copyright (C)  2017 by RealityTechSASU
 * Copyright (C)  2015-2016 by Jeremy Laviole
 * Copyright (C)  2006-2012 by Andreas Schlegel
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 * 
 * 
 */
package tech.lity.rea.skatolo.events;

import tech.lity.rea.skatolo.gui.ControllerInterface;
import tech.lity.rea.skatolo.gui.Controller;
import tech.lity.rea.skatolo.gui.group.ControllerGroup;
import tech.lity.rea.skatolo.gui.group.Tab;
import tech.lity.rea.skatolo.gui.group.ControlGroup;

/**
 * skatolo is a processing gui library.
 *
 *  2006-2012 by Andreas Schlegel
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 *
 * @author 		Andreas Schlegel (http://www.sojamo.de)
 * @modified	##date##
 * @version		##version##
 *
 */

/**
 * A controlEvent is sent to a PApplet or a ControlListener whenever a controller value has changed.
 * Events can also be sent when a tab is activated, but by default tab events are disabled and have
 * to be enabled with {@link Tab} Tab.activateEvent(). for detailed information see the tab
 * documentation.
 * 
 * @example use/skatolocontrolEvent
 */
public class ControlEvent {

	public static int UNDEFINDED = -1;
	public static int CONTROLLER = 0;
	public static int TAB = 1;
	public static int GROUP = 2;

	protected final ControllerInterface<?> _myController;

	protected boolean isTab;
	protected boolean isController;
	protected boolean isGroup;

	protected int myAction;

	/**
	 * 
	 * @param theController Controller
	 */
	public ControlEvent(Controller<?> theController) {
		_myController = theController;
		isTab = false;
		isController = true;
		isGroup = false;
	}

	/**
	 * @exclude
	 * @param theController Controller
	 */
	public ControlEvent(Tab theController) {
		_myController = theController;
		isTab = true;
		isGroup = false;
		isController = false;
	}

	/**
	 * @exclude
	 * @param theController Controller
	 */
	public ControlEvent(ControllerGroup<?> theController) {
		_myController = theController;
		isTab = false;
		isGroup = true;
		isController = false;
	}

	public float getValue() {
		return _myController.getValue();
	}

	public String getStringValue() {
		return ((Controller<?>) _myController).getStringValue();
	}

	/**
	 * Returns a float array, applies to e.g. Range.
	 * 
	 * @return float[]
	 */
	public float[] getArrayValue() {
		return _myController.getArrayValue();
	}

	/**
	 * Returns a float value at a particular index from a controller's array value. No error
	 * checking available here, will throw ArrayIndexOutOfBOundsException in case of unavailable
	 * index.
	 * 
	 * @param theIndex
	 * @return
	 */
	public float getArrayValue(int theIndex) {
		return _myController.getArrayValue()[theIndex];
	}

	/**
	 * Returns the instance of the controller sending the ControlEvent.
	 * 
	 * @return Controller
	 */
	public Controller<?> getController() {
		return ((Controller<?>) _myController);
	}

	/**
	 * Returns the tab that triggered the ControlEvent
	 * 
	 * @return Tab Tab
	 */
	public Tab getTab() {
		return (Tab) _myController;
	}

	/**
	 * Returns the group that evoked the ControlEvent
	 * 
	 * @return ControlGroup
	 */
	public ControlGroup<?> getGroup() {
		return (ControlGroup<?>) _myController;
	}

	/**
	 * Gets the text of the controller's label that has evoked the event.
	 * 
	 * @return String
	 */
	public String getLabel() {
		return ((Controller<?>) _myController).getLabel();
	}

	/**
	 * Checks if the ControlEvent was triggered by a tab
	 * 
	 * @see skatolo.Tab
	 * @return boolean
	 */
	public boolean isTab() {
		return isTab;
	}

	/**
	 * Checks if the ControlEvent was triggered by a controller
	 * 
	 * @see skatolo.Controller
	 * @return boolean
	 */
	public boolean isController() {
		return isController;
	}

	/**
	 * Checks if the ControlEvent was triggered by a ControlGroup
	 * 
	 * @see skatolo.ControllerGroup
	 * @return boolean
	 */
	public boolean isGroup() {
		return isGroup;
	}

	/**
	 * returns the controller's name
	 * 
	 * @return String
	 */
	public String getName() {
		return _myController.getName();
	}

	/**
	 * Returns the controller's id, if an id has not been set before the default value -1 will be
	 * returned.
	 * 
	 * @return
	 */
	public int getId() {
		return _myController.getId();
	}

	/**
	 * @return int returned is skatolo.CONTROLLER, or skatolo.TAB, or skatolo.GROUP
	 */
	public int getType() {
		if (isController) {
			return CONTROLLER;
		} else if (isTab) {
			return TAB;
		} else if (isGroup) {
			return GROUP;
		}
		return -1;
	}

	/**
	 * Checks if the ControlEvent originates from a specific Controller or ControllerGroup.
	 * 
	 * @param theController
	 * @return boolean
	 */
	public boolean isFrom(ControllerInterface<?> theController) {
		return _myController.equals(theController);
	}

	/**
	 * checks if the ControlEvent originates from a specific Controller or ControllerGroup
	 * identifiable by name.
	 * 
	 * @param theController
	 * @return boolean
	 */

	public boolean isFrom(String theControllerName) {
		return _myController.getName().equals(theControllerName);
	}

	public boolean isAssignableFrom(Class<?> c) {
		return _myController.getClass().isAssignableFrom(c);
	}

	public String toString() {
		return "[ ControlEvent from:"+_myController.getClass().getSimpleName()+" value:"+getValue()+" name:"+getName()+" ]";
	}
}
