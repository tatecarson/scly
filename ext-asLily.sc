+ SimpleNumber {
	
	asLilyPitch { 
		var index, pitch, notes, octave, pitchList, octaveList, octaveIndex, noteList;
		pitchList = ["c","cis","d","dis","e","f","fis", "g", "gis","a", "ais", "b"];					
		octaveList =  [",,,,",",,,",",,",",", " ","'","''","'''", "''''"];			
		octaveIndex = (this / 12.0).floor;
		pitch = pitchList.at((this.floor % 12));
		if( (this - this.floor).round(0.5) == 0.5, {pitch = pitch ++ "ih"});		
		octaveIndex = (this/12).floor;
		octave = octaveList.at(octaveIndex);
		if( this==0, {pitch = "s"; octave=""});
		^(pitch ++ octave);
	}
	
	asLilyMeasure {
		var eightNomeScale, measureScaleLily, measureIndex, measuresOutputList;
		eightNomeScale = (0.5, 1 .. 16); 
		measureScaleLily =  ["1/16", "1/8", "3/16", "2/8", "5/16", "3/8", "7/16", "4/8", "9/16", "5/8", "11/16", "6/8", "13/16", "7/8", "15/16", "8/8", "17/16", "9/8", "19/16", "10/8", "21/16", "11/8", "25/16", "12/8", "27/16", "13/8", "29/16", "14/8", "31/16", "15/8", "33/16", "16/8"];
		measureIndex = ((this * 2 - 1)).round;
		measuresOutputList = measureScaleLily[measureIndex];
		^measuresOutputList;
	}

}

+ SequenceableCollection {

	asLilyPitch 		{^this.collect({|x| x.asLilyNote})}

	asLilyMeasure 	{^this.collect({|x| x.asLilyMeasure})}

}



