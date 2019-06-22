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

/**
 * ControlListener is an interface that can be implemented by a custom class to
 * be notified when controller values change. To add a ControlListener to a
 * controller use Controller.addListner()
 * 
 * @see skatolo.Controller#addListener(ControlListener)
 * @see skatolo.CallbackListener
 * 
 * @example use/skatololistenerForSingleController
 */
public interface ControlListener {

	/**
	 * controlEvent is called by skatolo's ControlBroadcaster to inform
	 * available listeners about value changes. Use the CallbackListener to get
	 * informed when actions such as pressed, release, drag, etc are performed.
	 * 
	 * @see skatolo.CallbackListener
	 * @see skatolo.CallbackEvent
	 * @param theEvent ControlEvent
	 * @example skatololistener
	 */
	public void controlEvent(ControlEvent theEvent);

}
