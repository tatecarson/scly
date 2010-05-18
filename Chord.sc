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
	

// 	== { arg otherNote; 
// // 		Are these tow Notes equal?
// 		if(
// 			otherNote.class != Note,
// 			{"This is not a Note Object".warn}
// 		);
		
// 		^this.notenumber == otherNote.notenumber; 
	
// 	} 
	
// 	+ { |aNote| ^Note(midi: aNote.asNote.midi + midi); }
// 	- { |aNote| ^Note(midi: midi - aNote.asNote.midi); }
// 	* { |aNumber| ^Note(midi: midi * aNumber); }
// 	/ { |aNumber| ^Note(midi: midi / aNumber); }
// 	min { |aNote| ^Note(midi: midi.min(aNote.asNote.midi) ); }
// 	max { |aNote| ^Note(midi: midi.max(aNote.asNote.midi) ); }
// 	wrap { arg lo = "C-2", hi = "G8"; ^Note(midi: midi.wrap(lo.asNote.midi, hi.asNote.midi) ) }
// 	clip { arg lo = "C-2", hi = "G8"; ^Note(midi: midi.clip(lo.asNote.midi, hi.asNote.midi) ) }
// 	rand { arg lo = "C-2", step = 1; lo = lo.asNote.midi; ^Note(midi: (midi - lo).rand.round(step) + lo); }
// 	*rand { arg lo = "C-2", hi = "G8", step = 1; ^hi.asNote.rand(lo, step); }
// 	round { |aNumber = 1| this.midi = midi.round(aNumber); ^this; }
	


	// classvar
// 	*initClass {	
// 	}
	
}