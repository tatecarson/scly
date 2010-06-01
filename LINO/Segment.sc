
/*
   «Segment»
   «SCLy»

   Divides a time span in random smaller sizes with within a given range

	a = Segment.new(1, 6, 60) // segment 60 in fragments between 1 and 6
	a.list
	a.plot
	a.integrate


   Created by Bernardo Barros on 2010-05-21.
   Copyright 2010 __MyCompanyName__. All rights reserved.
*/


Segment {
	
	var <minValue, <maxValue, <dur, <segmentList;
	
	*new { arg minValue, maxValue, dur;
		^super.new.init(minValue, maxValue, dur);
	}

	init { arg thisMinVal, thisMaxVal, thisDur;
	
		minValue = thisMinVal; 
		maxValue = thisMaxVal;
		dur = thisDur;
	
		
	}

	list {
		
		var a, b, c;
		a = Array.new;
		b = 0.0;
		while { b < dur } 
		{
			c = min(rrand(minValue.asFloat, maxValue), 60 - b); 
			a = a.add(c);
			b = b + c;
		};
		^segmentList = a;
	}

	plot {

		segmentList.plot(discrete:true);
	}

	integrate {
		^segmentList.integrate
	
	}
}
