/*
	Sequence.sc
	

	Sequency is a class to represent a Sequency of music elements

	a = Sequence.new(notes, measures, tree)

	
	a = Sequence.new([0, 1, 2, 3, 4, 5, 6, 7, 8], [4, 3], [[1, 1, 1, 1, 1], [1, 1, 1]])
	a.notes;
	a.measures;
	a.tree;
	a.notes = [ 8, 7, 6, 5, 4, 3, 2, 1]
	a.notes;
	a.lilyNotes
	a.numberOfMeasures
	a.measures = [3, 4]
	a.asMeasureStringArray
	a.asPitchSequence
	
	a.lily
*/


Sequence {

	classvar measureDict;
	var  <>notes, <>measures, <>tree;
	

	*new { arg notes, measures, tree;
		^super.new.init(notes, measures, tree);
	}

	init { arg newNotes, newMeasures, newTree;
		notes = newNotes; 
		measures = newMeasures; 
		tree = newTree;
	}
	
	asPitchSequence {
		^PitchSequence.new(notes);
	}

	numberOfMeasures {
		^max(measures.size, tree.size);
	}
	
	asMeasureStringArray {
		^measures.collect({|i|
			measureDict.findKeyForValue(i)
		});
	}

	asLily {

		var materialOutputString, pitchStream;
		pitchStream = Pseq(notes, inf).asStream;
		
		this.numberOfMeasures.do({|i|	
			var measureRhythm, measure, measureProportion, measureTuplet;
			var measureTupletString, measureOutputString, measureNoteString;
			var thisSum, localTuplet, localLy, localString;
					

			
		 	if( // if it does not have secondary tuplets
				this.tree[i].hasArray == false,
				{
					measureRhythm = RhythmCell.new(measures[i], tree[i]);
					measure = measureRhythm.asTimeSigString; // this measure		
					measureProportion = measureRhythm.adjustedList; // this measure tree
					measureTuplet = measureRhythm.grupetto; // tuplet
					if( // if tuplet = 1/1, do nothing
						(measureTuplet[0]/measureTuplet[1]).asFraction==[1, 1],
						{ measureTupletString = "\t" },
						{measureTupletString = 92.asAscii ++ "times " ++ measureTuplet[1].asString ++ "/" ++ measureTuplet[0].asString;}
					);
					
					measureNoteString = " ";
					measureProportion.do({ arg i;
						measureNoteString = measureNoteString ++ (" "++(pitchStream.next + 60).asLilyPitch++i).asString});
					materialOutputString = materialOutputString ++ (" \\time " ++  measure ++ "\n \t " ++ measureTupletString ++  " {" ++ measureNoteString ++ " } \n");
				},
				// If it has secondary tuplets:
				{
					
					// if the measure as a whole need a tuplet??
					thisSum = this.tree[i].otherSum;
					measureRhythm = RhythmCell.new(measures[i], tree[i].extractFlat);
					measure = measureRhythm.asTimeSigString; // this measure // "measure" is the time signature string
					measureProportion = measureRhythm.adjustedList; // this measure tree
					measureTuplet = measureRhythm.grupetto; // tuplet
					
					if( // if tuplet = 1/1, do nothing
						(measureTuplet[0]/measureTuplet[1]).asFraction==[1, 1],
						{ measureTupletString = "\t" },
						{measureTupletString = 92.asAscii ++ "times " ++ measureTuplet[1].asString ++ "/" ++ measureTuplet[0].asString;}
					);
					
					measureNoteString = " ";
					
					// put Time Signature Indication Here
					materialOutputString = materialOutputString ++ " \\time " ++  measure ;
					
					tree[i].do({|j, h| // now check each item if it is a Array (tuplet) or just a note
						var secondaryCell;
						
						if( 
							j.isArray == false, // if item is array (tuplet) or not (note)
							{ // only one note here
								measureNoteString =  measureNoteString ++  (" "++(pitchStream.next + 60).asLilyPitch++ (measureProportion[h]).asString).asString;
								materialOutputString = materialOutputString ++ measureNoteString;	
							},
							//////////////////////////////////
							{ // this is a secondary tuplet
								"---->".postln;
								secondaryCell = RhythmCell.new(measureProportion[h].postln, j[1].postln);
								localTuplet = secondaryCell.grupetto;
								localLy = secondaryCell.adjustedLy;
								localString = "";
								
								// put tuplet
								localString = localString ++ "\n\t\t" ++ 92.asAscii ++ "times " ++ localTuplet[0].asString ++ "/" ++ localTuplet[1].asString ++ " { ";
								
								localLy.do({|k| 
									localString = localString ++ (pitchStream.next + 60).asLilyPitch ++ k.asString ++ " ";
								
								});
								
								//close tuplet
								localString = localString ++ " }" ++ "\n\t\t";
								
								// put this cell

								materialOutputString = materialOutputString + localString;
								
								
								
							
							
							}
						)
							
						
					});
					
					
				}
				/////////
			);		
		
		});
	
		^materialOutputString;
	}
	
	
	*initClass {

		measureDict = Dictionary[
			"1/16" -> 0.5, 
			"1/8" -> 1, 
			"3/16" -> 1.5, 
			"2/8" -> 2, 
			"5/16" -> 2.5, 
			"3/8" -> 3, 
			"7/16" -> 3.5, 
			"4/8" -> 4, 
			"9/16" -> 4.5, 
			"5/8" -> 5, 
			"11/16" -> 5.5, 
			"6/8" -> 6, 
			"13/16" -> 6.5, 
			"7/8" -> 7, 
			"15/16" -> 7.5, 
			"8/8" -> 8, 
			"17/16" -> 8.5, 
			"9/8" -> 9, 
			"19/16" -> 9.5, 
			"10/8" -> 10, 
			"21/16" -> 10.5, 
			"11/8" -> 11, 
			"25/16" -> 11.5, 
			"12/8" -> 12, 
			"27/16" -> 12.5, 
			"13/8" -> 13, 
			"29/16" -> 13.5, 
			"14/8" -> 14, 
			"31/16" -> 14.5, 
			"15/8" -> 15, 
			"33/16" -> 15.5, 
			"16/8" -> 16

		];


	}
	

}