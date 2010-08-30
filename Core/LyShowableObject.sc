/*

	LyShowableObject.sc
	
	This is for all objects that can be placed in a score


*/






	// ------------------------------
	// WRITE 
	
	// writeLy {
	// 	/*
	// 		Write the string to a temporary ly file
	// 	*/ 
	// 	var file, fWrite, outputFile;		
	// 	outputFile =  LyConfig.options[\output] ++ this.filename;
	// 	fWrite = File(LyConfig.options[\output] ++ this.filename ++ ".ly", "w");
	// 	fWrite.write(this.musicString);
	// 	fWrite.close;
	// 	("/Applications/LilyPond.app/Contents/Resources/bin/lilypond -o" ++ LyConfig.options[\output] ++ this.filename ++ " " ++ LyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;

	// }

	// editLy {
	// 	("open " ++ LyConfig.options[\output] ++ this.filename ++ ".ly").unixCmd;
	// }

	// openPdf {
	// 	("open " ++ LyConfig.options[\output] ++ this.filename ++ ".pdf").unixCmd;
	// }