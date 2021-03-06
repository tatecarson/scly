/*
	LilyNote.sc

	Pitch representation with Lilypond suport
	
	Use:
	a = LilyNote.new(-11)
	a.string 
	a.pitch 
	a.octave
	a.notenumber = 10	
	a.lily = "cis'"
	a.string 
	a.qt
	a.cps = 440
	a.string 
	a.lily = "cis'"
	a.string 
	a.notenumber


	// Salvar e abrir ly pdf midi
	a = LilyNote.new(30)
	a.filename = "teste2"
	a.musicString
	a.writeLy	
	a.openPdf	
	a.editLy
	
	// Harmonic Series:
	a = LilyNote(-5)
	a = a.makeHarmonicSeries
	a.notenumber
	a.writeLy
	a.openPdf

	// Articulations:

	a = LilyNote(5)
	a.putAfterNote("-.")
	a.afterNote
	a.putAfterNote("->")
	a.afterNote	
	a.putBeforeNote("\\red ")
	a.beforeNote

	Created by Bernardo Barros on 2010-05-12.
*/

LilyNote {
	
	classvar <pitchList, <octaveList, <pitchDict, <octDict, <afterNoteDict, <beforeNoteDict;
	var <notenumber, <>duration, qt;
	var <pitch, <octave, <artic, <>afterNote, <>beforeNote;
	var <>filename = "supercollider", <>folder="~/scwork/scly/output/";
	
	*new {|notenumber|
		^super.new.init(notenumber);
		}
		
	init {arg thisnotenumber;
		this.notenumber_(thisnotenumber);
		this.afterNote = [];
		this.beforeNote = [];
		}

 	qt {

		^notenumber.frac.round(0.5) == (0.5)
 	}

	notenumber_ {arg newnotenumber;
		
		var index, octIndex, notenumberFloor, noteNumberFloor;
		
		notenumber = newnotenumber;
		notenumberFloor = notenumber.floor;
		pitch = pitchList.at((notenumberFloor % 12));		
		((notenumber - notenumberFloor).round(0.5) == (0.5)).if({
			pitch = pitch ++ "ih"
		});
		octIndex = ((notenumber+60)/12).floor;
		octave = octaveList.at(octIndex);
	
		}
	
	string {

		// return a string with with pitch and octave
		^pitch ++ octave;
		}
		
	putAfterNote { arg newArticulation;

		if(
			afterNote.includes(newArticulation) == false,
			{afterNote = afterNote.add(newArticulation)},
			{"This Array already contains this string".warn}
		)
	}

	putBeforeNote { arg newArticulation;

		if(
			beforeNote.includes(newArticulation) == false,
			{beforeNote = beforeNote.add(newArticulation)},
			{"This Array already contains this string".warn}
		)
	}

	// transposition
	t { arg value;
		this.notenumber = this.notenumber + value;
	
	}

 	lily_ { arg newLilyString;
		
// 			Change the pitch with a new string in Lilypond Format
// 			Have to separate pitchclass and octave data
// 			c' = 0
		
		var newPitchString, newOctString;
		newPitchString = "";
		newOctString = "";

		newLilyString.do({|i| 
			var string = i.asString;
			case		
			{(string != "'").and(string != ",")}
			{newPitchString = newPitchString ++ string}
			
			{(string == "'").or(string == ",")}
			{newOctString = newOctString ++ string};
		});	

		this.notenumber = pitchDict[newPitchString] + (octDict[newOctString] * 12);

	}

 	cps_ { arg newCps;
		this.notenumber = (newCps.cpsmidi - 60).round(0.5)
	}

	musicString {
		^"{ " ++  this.string ++ "}"
	}


	== { arg otherNote; 
// 		Are these tow Notes equal?
		if(
			otherNote.class != Note,
			{"This is not a Note Object".warn}
		);
		
		^this.notenumber == otherNote.notenumber; 
	
	} 

	makeHarmonicSeries { arg lo=0, high=15, roundNotes=0.5;
		var outputArray, thisCps, numberOfNotes;
		thisCps = (this.notenumber + 60).midicps;
		numberOfNotes = high-lo;
		outputArray = Array.new;
		(lo..high).do({|i|
			outputArray = outputArray.add(
				thisCps + (thisCps * i)
			)
		});

		^PitchSequence(outputArray.cpsmidi.round(roundNotes) - 60)
	
	}

	// ------------------------------
	// WRITE 
	// TODO: Move this method to superclass LilyShowableObject

	writeLy {
		/*
			Write the string to a temporary ly file
		*/ 
		var file, fWrite, outputFile;		
		outputFile =  LilyConfig.options[\output] ++ this.filename;
		fWrite = File(LilyConfig.options[\output] ++ this.filename ++ ".ly", "w");
		fWrite.write(this.musicString);
		fWrite.close;
		("lilypond -o" ++ LyConfig.options[\output] ++ this.filename ++ " " ++ LilyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;

	}

	editLy {
		("open " ++ LilyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;
	}

	openPdf {
		("open " ++ LilyConfig.options[\output] ++ this.filename ++ ".pdf").unixCmd;
	}

	

	// classvar

	*initClass {
	
		pitchList = ["c","cis","d","dis","e","f","fis", "g", "gis","a", "ais", "b"];
		
		octaveList =  [",,,,",",,,",",,",",", " ","'","''","'''", "''''"];

		pitchDict = Dictionary[

			"c" -> 0,
			"cih" -> 0.5,
			"cis" -> 1,
			"des" -> 1,
			"cisih" -> 1.5,
			"deh" -> 1.5,
			"d" -> 2,
			"dih" -> 2.5,
			"eeseh" -> 2.5,
			"dis" -> 3,
			"ees" -> 3,
			"disih" -> 3.5,
			"eeh" -> 3.5,
			"e" -> 4,
			"eis" -> 4.5,
			"feh" -> 4.5,
			"f" -> 5,
			"fih" -> 5.5,
			"geseh" -> 5.5,
			"fis" -> 6,
			"ges" -> 6,
			"fisih" -> 6.5,
			"geh" -> 6.5,
			"g" -> 7,
			"gih" -> 7.5,
			"aeseh" -> 7.5,
			"gis" -> 8,
			"aes" -> 8,
			"gisih" -> 8.5,
			"aeh" -> 8.5,
			"a" -> 9,
			"aih" -> 9.5, 
			"beseh" -> 9.5, 
			"ais" -> 10,
			"bes" -> 10,
			"aisih" -> 10.5,
			"beh" -> 10.5,
			"b" -> 11,
			"bih" -> 11.5,
			"ceh" -> 11.5
		];
	
		octDict = Dictionary[

			",,," -> -4,
			",," -> -3,
			"," -> -2,
			"" -> -1,
			"'" -> 0,
			"''" -> 1,
			"'''" -> 2,
			"''''" -> 3
		];
	

// 		afterNoteDict = Dictionary[
// 			\staccato -> "-.",
// 			\accent -> "->",
// 			\tenuto -> "--",
// 			\marcato -> "^",
// 			\portato -> "-_"
// 		];
		
// 		beforeNoteDict = Dictionary[
// 			\red -> "\\once \\override NoteHead #'color = #red",
// 			\blue -> "\\once \\override NoteHead #'color = #blue",
// 			\green -> "\\once \\override NoteHead #'color = #green",
// 			\harmonic -> "\\once \\override Staff.NoteHead  #'style = #'harmonic",
// 			\harmonicBlack -> "\\once \\override Staff.NoteHead  #'style = #'harmonic-black",
// 			\harmonicMixed -> "\\once \\override Staff.NoteHead  #'style = #'harmonic-mixed",
// 			\cross -> "\\once \\override Staff.NoteHead  #'style = #'cross", 
// 			\xCircle -> "\\once \\override Staff.NoteHead  #'style = #'xcircle",
// 			\triangle -> "\\once \\override Staff.NoteHead  #'style = #'triangle",
// 			\slash -> "\\once \\override Staff.NoteHead  #'style = #'slash"
// 		];
		
		
	}
	
}