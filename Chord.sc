/*
	Chord.sc
   
	Chord representation with Lilypond suport
	
	c = Chord.new([10, 11])
	c.notenumber
	c.noteArray
	c.qt
	c.stringArray
	c.string
	c.musicString	
	c.writeLy
	c.editLy
	c.openPdf

	////////////////////
	// Set operations //
	////////////////////

	a = Chord.new([1, 2, 3])
	b = Chord.new([2, 3, 4])
	c = a - b
	c.notenumber


*/

Chord  {

	var <>filename = "supercollider";
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
		// return an array with booleans
		^noteArray.collect({|i| i.qt })
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
		/*
			Write the string to a temporary ly file
		*/ 
		var file, fWrite, outputFile;		
		outputFile =  LyConfig.options[\output] ++ this.filename;
		fWrite = File(LyConfig.options[\output] ++ this.filename ++ ".ly", "w");
		fWrite.write(this.musicString);
		fWrite.close;
		("/Applications/LilyPond.app/Contents/Resources/bin/lilypond -o" ++ LyConfig.options[\output] ++ this.filename ++ " " ++ LyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;
	
	}
	
	editLy {
		("open " ++ LyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;
	}
	
	openPdf {
		("open " ++ LyConfig.options[\output] ++ this.filename ++ ".pdf").unixCmd;
 	}
	

	////////////////////////////////////////
	////////// Set Operations //////////////
	////////////////////////////////////////

	// see Set Help File

	- { arg otherChord;
		^Chord(this.notenumber.asSet - otherChord.notenumber.asSet)
	}
	
	-- { arg otherChord;
		^Chord(this.notenumber.asSet -- otherChord.notenumber.asSet)
	}

	| { arg otherChord;
		^Chord(this.notenumber.asSet | otherChord.notenumber.asSet)
	}
	
	& { arg otherChord;
		^Chord(this.notenumber.asSet & otherChord.notenumber.asSet)
	}

	isSubsetOf { arg otherChord;
		^Chord(
			this.notenumber.asSet.isSubsetOf(otherChord.notenumber.asSet)
		)
	
	}

}