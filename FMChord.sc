

// ~fm = { arg carrier = 7, modulator = -4, index=9;
// 	// criar o acorde inicial de Gondwana
// 	// sol3 (392Hz) e sol#2 (207,65Hz)

// 	var add, diff;
// 	var carrierCps = (carrier + 60).midicps;
// 	var modulatorCps = (modulator + 60).midicps;

// 	add = index.collect {|i|
// 		carrierCps + ((i+1) * modulatorCps);	
// 	};

// 	diff = index.collect {|i|
// 		carrierCps - ((i+1) * modulatorCps);	
// 	};

// 	[(add.cpsmidi - 60).round(0.5), (diff.cpsmidi -60).round(0.5)]
// };
	
// ~fm.value

/*
	FMChord.sc
	SCLy project

	Creates a 'Frequency Modulation' Chord 
	Arguments: carrier, modulator, index
	
	Use:
	
	a = FMChord.new(7, -5, 9)
	a.index
	a.car
	a.mod	
	a.addChord
	a.diffChord	
	a.fmChord	
	

 Created by Bernardo Barros on 2010-05-19.
 Copyright 2010 All rights reserved.
*/


FMChord : Chord {

	var <car, <mod, <index;

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
		^Chord.new(this.addChord.noteArray ++ this.diffChord.noteArray);
	}

}