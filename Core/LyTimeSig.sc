/*

	USAGE:

	t = LyTimeSig.new(3, 4) 
	t.numBeats // -> 3
	t.noteValue // -> 4
	t.timeSigArray // -> [3, 4]
	t.asLisp // -> "( 3 4 )"
*/

LyTimeSig {

	var <>numBeats, <>noteValue, <lispString, <>timeSigArray; 


	*new {arg numBeats, noteValue;
		
		^super.new.initTimeSig(numBeats, noteValue);
	}

	initTimeSig {arg thisNumBeats, thisNoteValue;

		this.numBeats_(thisNumBeats);
		this.noteValue_(thisNoteValue);
		this.timeSigArray_([thisNumBeats, thisNoteValue]);
	}
	
	asLisp {

		^this.timeSigArray.asLisp;
	}

}
