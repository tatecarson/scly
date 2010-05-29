/*
	PitchSequence.sc
   
	PitchSequence (Notes and Chords) representation with Lilypond suport
	
	c = PitchSequence.new([10, 11, [5, 7], 3])
	c.notenumber
	c.pitchArray
	c.qt
	c.string
	c.musicString

	c = PitchSequence.setString("c d e f")
	
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
		
	init {arg noteList=[0];

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
	
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	// enter a string with notes in Lilypond format

	setString { arg thisString;
	
	var output, tempString, noteArray;
	tempString = "";
	output = [];
	
	thisString.do({|i|
		i.postln;
		if( i == 32.asAscii,
		{
			output = output.add(tempString);
			tempString = "";
		},{
			tempString = tempString ++ i
		});
	});

	if( thisString.last != 32.asAscii,
		{output = output.add(tempString)}
	);

		// now output is an Array with notes in String Format
		// Note understands Strings... so we have to iterate
		// this Array and make a PitchSequence with Notes
		noteArray = []; // same size
		
		output.do({|i|
			noteArray = noteArray.add(Note.new(0).lily_(i).notenumber)
		});

		// Now return a new PitchSequence with this Notes
		//^noteArray
		this.init(noteArray)
		
	}
	////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////

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
	

	
}