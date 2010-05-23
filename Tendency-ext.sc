/*
	TendencyMask.sc
   
	A user interface to Josh Parmenter's Tendency.sc

	USAGE:
	a = TendencyMask.new(50)
	a.gui
	a.n_(30)
	a.make
	a.plot

	Created by Bernardo Barros 
*/

+ Tendency {

 	gui {

		/////// GUI Start /////////

		var win, view1, view2, swapData, config, urView, data1, data2; 
		data1 = 1.0.dup(50); 
		data2 = 0.0.dup(50);
		win = GUI.window.new("Tendency Mask", Rect(200 , 450, 680, 520)); 
		GUI.button.new(win, Rect(0,0, 80,20)).states_([["Botton"],["Top"]]).action_{|v| swapData.(v.value)}; 
		urView = {GUI.multiSliderView.new(win, Rect(10, 24, 653, 480))};
		// General Configurations for the two MultiSliderView:
		config = { 
			view1.drawLines_(true); 
			view1.strokeColor_(Color.red);
			view2.drawLines_(true); 
			view2.strokeColor_(Color.blue);
		}; 

		// Function that has a if control
		// depends of the button state
		swapData = { arg dofocusOn; 
			
			// hacked from examples folder
			if( 
				dofocusOn == 0, 		
				{ 	
					// if dofocusOn == 0
					view1.remove; 
					view2.remove; 
					view1 = urView.value; 
					view2 = urView.value;
					config.value;
					view1.value_(data1);  
					view2.value_(data2);
					"view2".postln;
					view2.action_({|sl| 
						data2 = sl.value;
						this.parX = Env(data2, 1.dup(data2.size - 1).normalizeSum);

					}); 
				}, { 
					// if dofocusOn == 1
					view1.remove; 
					view2.remove; 					
					view2 = urView.value;  
					view1 = urView.value;					
					config.value; 						 
					view2.value_(data2); 
					view1.value_(data1); 					
					"view1".postln;						 
					view1.action_({|sl| 
						data1 = sl.value;
						this.parY = Env(data1, 1.dup(data1.size - 1).normalizeSum);
					}); 					 
				} 
			); 			
		}; 
		swapData.value(0); 
		win.front; 
		///// GUI End /////////////
 	}
}