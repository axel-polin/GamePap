/**
 * skatolo Behavior 
 * ControlBehavior is an abstract class that can be extended using your 
 * custom control behaviors. What is a control behavior? Control Behaviors
 * allow you to automate and dynamically change the state or value of a
 * controller. One behavior per controller is currently supported. i case you
 * need to use more that one bahavior, the implementation has to happen
 * on your side - inside your control behavior.
 *
 * find a list of public methods available for the ControlBehavior Controller 
 * at the bottom of this sketch.
 *
 * by Andreas Schlegel, 2012
 * www.sojamo.de/libraries/skatolo
 *
 */

import skatolo.*;


Skatolo skatolo;

int myColorBackground = color(0, 0, 0);

public int sliderValue = 100;

void setup() {
  size(400, 400);
  noStroke();

  skatolo = new Skatolo(this);
  skatolo.addSlider("sliderValue")
     .setRange(0,255)
     .setValue(128)
     .setPosition(100, 50 + height/2)
     .setSize(40, 100);
     
  skatolo.addSlider("slider")
     .setRange(100, 255)
     .setValue(128)
     .setPosition(100, 50)
     .setSize(100, 40);
     

  skatolo.addBang("bang")
     .setPosition(40, 50 + height/2)
     .setSize(40, 40);
     
  // add a custom ControlBehavior to controller bang,
  // class TimerEvent is included in this sketch at the bottom
  // and extends abstract class ControlBehavior.
  skatolo.getController("bang").setBehavior(new TimedEvent());

  // use an anonymous class of type ControlBehavior.
  skatolo.getController("slider").setBehavior(new ControlBehavior() {
    float a = 0;
    public void update() { 
      setValue(sin(a += 0.1) * 50  + 150);
    }
  }
  );
}

void draw() {
  background(myColorBackground);
  fill(sliderValue);
  rect(0, 0, width, height/2);
}

void slider(float theColor) {
  myColorBackground = color(theColor);
  println("# a slider event. setting background to "+theColor);
}

public void bang() {
  println("# an event received from controller bang.");
  // a bang will set the value of controller sliderValue
  // to a random number between 0 and 255.
  skatolo.getController("sliderValue").setValue(random(0, 255));
}

// custom ControlBehavior
class TimedEvent extends ControlBehavior {
  long myTime;
  int interval = 200;

  public TimedEvent() { 
    reset();
  }
  void reset() { 
    myTime = millis() + interval;
  }

  public void update() {
    if (millis()>myTime) { 
      setValue(1); 
      reset();
    }
  }
}




/*
a list of all methods available for the ControlBehavior Controller
use skatolo.printPublicMethodsFor(ControlBehavior.class);
to print the following list into the console.

You can find further details about class ControlBehavior in the javadoc.

Format:
ClassName : returnType methodName(parameter type)


skatolo.ControlBehavior : Controller controller() 
skatolo.ControlBehavior : boolean isActive() 
skatolo.ControlBehavior : void setActive(boolean) 
skatolo.ControlBehavior : void setValue(float) 
java.lang.Object : String toString() 
java.lang.Object : boolean equals(Object) 


*/

