/*  

	LilyChord.sc
   
	a Chord score representation with Lilypond suport
	
	c = LilyChord.new([10, 11])
	c.notenumber
	c.noteArray
	c.qt
	c.stringArray
	c.string
	c.musicString	
	c.intervals
	c.writeLy
	c.editLy
	c.openPdf

	////////////////////
	// Set Operations //
	////////////////////

	A = LilyChord.New([1, 2, 3])
	B = LilyChord.New([2, 3, 4])
	c = a - b
	c.notenumber


*/

LilyChord  {

	var <>filename = "supercollider"; // <--- change this?
	var <>notenumber, <>noteArray;

	*new {|noteList|
		^super.new.init(noteList);
		}
		
	init {arg noteList;
		notenumber = Array.new;
		noteArray = Array.new;
		noteList.do({arg thisNote;
			notenumber = notenumber.add(thisNote);
			noteArray = noteArray.add(Note.new(thisNote));
		})
	}
	
 	qt { 
		// return an array with booleans (maybe useful)
		^noteArray.collect({|i| i.qt })
	}

	// returns a list of intervals
	intervals  { 	
		var intervals, notes;
		notes = this.notenumber;
		(notes.size - 1).do({arg i;
			intervals = intervals.add(notes[i + 1] - notes[i]);
		});
		^intervals;
	}


	stringArray {
		// return array of strings
		^this.noteArray.collect({|i| i.pitch ++ i.octave;})	
	}
		
	string {
		var musicStringOut;
		musicStringOut = "< ";
		this.stringArray.do({|i|
			musicStringOut = musicStringOut ++ i ++ " "
		});
		^musicStringOut ++ ">";
	}

	musicString {
		^"{ " ++  this.string ++ "}"
	}
	
	writeLy {

		/*  * *  * *  * *  * *  * *  * *  * *  * *  * * 

			Write the string to a temporary ly file

		 * *  * *  * *  * *  * *  * *  * *  * *  * *  * * */

		var file, fWrite, outputFile;		

		outputFile =  LilyConfig.options[\output] ++ this.filename;

		fWrite = File(LilyConfig.options[\output] ++ this.filename ++ ".ly", "w");

		fWrite.write(this.musicString);

		fWrite.close;

		/* * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * *

			call lilypond:

			* * ** * ** * ** * ** * ** * ** * ** * ** * ** * ** * */

		("lilypond -o" ++ LilyConfig.options[\output] ++ this.filename ++ " " ++ LilyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;
	
	}
	
	editLy {
		("open " ++ LilyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;
	}
	
	openPdf {
		("open " ++ LilyConfig.options[\output] ++ this.filename ++ ".pdf").unixCmd;
 	}
	

	////////////////////////////////////////
	////////// Set Operations //////////////
	////////////////////////////////////////

	// see Set Help File

	- { arg otherChord;
		^LilyChord(this.notenumber.asSet - otherChord.notenumber.asSet)
	}
	
	-- { arg otherChord;
		^LilyChord(this.notenumber.asSet -- otherChord.notenumber.asSet)
	}

	| { arg otherChord;
		^LilyChord(this.notenumber.asSet | otherChord.notenumber.asSet)
	}
	
	& { arg otherChord;
		^LilyChord(this.notenumber.asSet & otherChord.notenumber.asSet)
	}

	isSubsetOf { arg otherChord;
		^LilyChord(
			this.notenumber.asSet.isSubsetOf(otherChord.notenumber.asSet)
		)
	
	}



}