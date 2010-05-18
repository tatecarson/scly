/*
	PitchSequence.sc
   
	PitchSequence (Notes and Chords) representation with Lilypond suport
	
	c = PitchSequence.new([10, 11, [5, 7], 3])
	c.notenumber
	c.pitchArray
	c.qt
	c.string
	c.musicString	
	c.writeLy
	c.editLy
	c.openPdf
	
*/

PitchSequence  {

	var <>filename = "supercollider";
	var <>notenumber, <>pitchArray;

	*new {|noteList|
		^super.new.init(noteList);
		}
		
	init {arg noteList;

		notenumber = Array.new;
		pitchArray = Array.new;

		noteList.do({arg thisNote;

			notenumber = notenumber.add(thisNote);

			case
			{thisNote.class == Array}
			{pitchArray = pitchArray.add(Chord.new(thisNote))}
			
			{(thisNote.class == Integer).or(thisNote.class == Float)}
			{pitchArray = pitchArray.add(Note.new(thisNote))};
		})
	}
	
 	qt { 
		// return an array with booleans
		^pitchArray.collect({|i| i.qt })
	}
		
	string {
		^this.pitchArray.collect({|i| i.string;})	
	}

	musicString {
		var out;
		out = "{ ";
		this.pitchArray.do({|i| out = out ++ i.string ++ " ";})
		^out ++ "}"
	}
	
	writeLy {
		/*
			Write the string to a temporary ly file
		*/ 
		var file, fWrite, outputFile, templateFile, templateString;
		templateFile = File(LyConfig.options[\template], "r");
		templateString = templateFile.readAllString; 
		templateFile.close;
		
		outputFile =  LyConfig.options[\output] ++ this.filename;
		fWrite = File(LyConfig.options[\output] ++ this.filename ++ ".ly", "w");
		fWrite.write(templateString ++ " \n \n \n " ++ this.musicString);
		fWrite.close;
		("/Applications/LilyPond.app/Contents/Resources/bin/lilypond -o" ++ LyConfig.options[\output] ++ this.filename ++ " " ++ LyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;
	
	}


	view  { 
		var fileName = this.filename;
		var folder = LyConfig.options[\output], factorSize = 3;
		
		var w, m, a4, size;
		a4 = [210, 297];
		size = factorSize * a4 ;
		w = SCWindow("score", Rect(100, 100, size[1], size[0] )).front;
		m = SCMovieView(w, Rect(0,0,size[1], size[0]));
		m.path_(folder ++ fileName ++ ".pdf");
		m.path_(folder ++ fileName ++ ".midi");
		m.start;
		
		
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