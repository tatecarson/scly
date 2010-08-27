/*

	RhythmicCell.sc
	
	Let's leave the Lilypond measure  representation ("\time 4/8") to another class...

	Use:
	a = RhythmicCell([4, [1, 1, [1, [1, 1, 1, 1]], 1, 1]])
	a.asLySequence	

	Internal or with 1-deep Arrays:
	a = RhythmicCell([4, [1, 1, 1, 1, 1]])
	a.size
	a.tree
	a.adjustedList
	a.adjustedLy
	a.grupetto
	a.lyGrupetto
	a.sum
	a.asLispString
	a.asLyString

	TODO:
	Make it more elegant...
*/

RhythmicCell {

	classvar noteNotationScale, noteDurationScale, durationDict, eightNomeScale, measureScaleLily, measureDict;
	var  <>size, <>tree, <tuplet, <>arrayTree, <>pitchStream, <tupletString;

	*new { arg arrayTree, pitchStream;
		^super.new.init(arrayTree, pitchStream);
	}

	// inicialization -----------------------------------

	init { arg arrayTree= [2, [1, 1, 1]], stream;
		#size, tree = arrayTree;
		pitchStream = stream;
	}

	asLispString {
		^this.tree.asLisp;
	}

	sum {
		^tree.sum	
	}

// 	asTimeSigString {
// 		^measureDict.findKeyForValue(size.round(0.5))
// 	}

	adjustedList {
		var factor, adjustedSum;
		factor = 1;
		adjustedSum = this.sum;
		
		
		while( 	
			{ adjustedSum < (size*8) },
			
			{ 
				adjustedSum = adjustedSum * 2; 
				factor = factor * 2; 
			}
		);
		^tree * factor;
		
	}

	adjustedLy {
		^this.adjustedList.collect({|i|
			durationDict.findKeyForValue(i)
		});
	}
	
	grupetto {
		var x;
		x = [
			(this.adjustedList.sum / (gcd((size * 8),this.adjustedList.sum))),
			((size * 8) / (gcd((size * 8), this.adjustedList.sum)))

		];
		^x;
	
	}
	
	////////
// 	a = RhythmicCell.new([4, [1, 1, 1, 1, 1, 1]])
// 	a.size
// 	a.tree
// 	a.adjustedList
// 	a.adjustedLy
// 	a.grupetto
	
	//	a.deepCollect(2, {|item| item.isArray == false}).postln;
	lyGrupetto {
	
		^("times " ++ this.grupetto[1].asString ++ "/" ++  this.grupetto[0].asString + " { ").asString;
	}



 	asLyString { 
		var outputString, thisFlat, thisFlatAdjusted, thisSemiAdjusted;
		outputString = String.new;
		outputString = outputString ++ this.lyGrupetto;
		
		this.adjustedLy.do({|i|
			outputString = outputString ++ "c'" ++ i ++ " ";
		});

		^outputString;
 	}

	asLySequence {
		var outputString, thisTree, tempTree, tempTreeAdjusted, tempTree2;
		thisTree = this.tree;
		tempTree = Array.new;
		outputString = String.new;

		thisTree.deepCollect(2, {|item| 
			if(item.isArray == false, {tempTree = tempTree.add(item)})});	
		

		outputString = outputString ++ "\n \t" ++ RhythmicCell([size, tempTree]).lyGrupetto ++ " "; 
		//"outputString ---->  ".post; outputString.postln;
		//"tempTree----> ".post; tempTree.postln;

		tempTreeAdjusted = RhythmicCell([size, tempTree]).adjustedList;
		
		//"tempTreeAdjusted---> ".post; tempTreeAdjusted.postln;

		thisTree.do( 
			{|i, counter|
				////////////////////////
				case
				////////////////////////
				{i.isArray == false} {
					//"NUMERO".postln;
					outputString = outputString ++ "c'" ++ tempTreeAdjusted[counter].asString ++ " ";
					//tempTreeAdjusted[counter].postln;
				}
				///////////////////////
				{i.isArray == true} {
					//"ARRAY".postln;
					//i.postln;
					//RhythmicCell.new(thisTree.at(counter)).asLyString.postln;
					outputString = outputString ++ "\n \t \t" ++ RhythmicCell.new(thisTree.at(counter)).asLyString ++ " } \n \t";
				};
				///////////////////////
			}
		);	
		^(outputString ++ " } ");
	
	}
	
	*initClass {

		noteNotationScale = #["64" , "64." , "32" , "32." , "16" , "16." , "8" , "8." , "4" , "4." , "2" , "2." , "1"];
		noteDurationScale = [1, 1.5, 2, 3, 4, 6, 8, 12, 16, 24, 32, 48, 64, 96];
	
		durationDict = Dictionary[
			/*
				This was noteDurationScale and noteDurationScale
			*/
			"64" -> 1, 
			"64." -> 1.5, 
			"32" -> 2, 
			"32." -> 3, 
			"16" -> 4, 
			"16." -> 6, 
			"8" -> 8, 
			"8." -> 12, 
			"4" -> 16, 
			"4." -> 24, 
			"2" -> 32, 
			"2." -> 48, 
			"1" -> 64,
			"1." -> 96
		];

		eightNomeScale = (0.5, 1 .. 16); 
		measureScaleLily = ["1/16", "1/8", "3/16", "2/8", "5/16", "3/8", "7/16", "4/8", "9/16", "5/8", "11/16", "6/8", "13/16", "7/8", "15/16", "8/8", "17/16", "9/8", "19/16", "10/8", "21/16", "11/8", "25/16", "12/8", "27/16", "13/8", "29/16", "14/8", "31/16", "15/8", "33/16", "16/8"];


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



