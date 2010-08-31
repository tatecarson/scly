/*
	FMChord.sc
	SCLy project

	Creates a 'Frequency Modulation' Chord 
	Arguments: carrier, modulator, index
	
	Use:
	
	a = FMChord.new(7, -5, 9)
	a.index
	a.car
	a.car = 4
	a.mod	
	a.addChord
	a.diffChord	
	a.fmChord	

	

 Created by Bernardo Barros on 2010-05-19.
 Copyright 2010 All rights reserved.
*/


FMChord : LyChord {

	var <>car, <>mod, <>index;

	*new {|car, mod, index|
		^super.new.init(car, mod, index);
		}
		
	init {arg thiCar = 7, thiMod = -4, thisIndex = 9;

		car = thiCar;
		mod = thiMod;
		index = thisIndex;
	}


	
	addChord {
		var addChordCps;
		addChordCps = index.collect {|i|
			(car+60).midicps + ((i+1) * (mod+60).midicps);	
		};
		^Chord.new(addChordCps.cpsmidi - 60);
	}


	diffChord {
		var diffChordCps;
		diffChordCps = index.collect {|i|
			(car+60).midicps - ((i+1) * (mod+60).midicps);	
		};
		^Chord.new(diffChordCps.cpsmidi - 60);
	}

	fmChord {	
		^Chord.new(
			(this.diffChord.notenumber) ++ (this.addChord.notenumber)
		);
	}

}