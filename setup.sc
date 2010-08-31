/*
	Configuration Options stored in a Dictionary

	Use:
	LyConfig.options[\output]

	LyConfig.options[\output] = "...another folder..."

*/

LyConfig {

	classvar <>options ;
 	
	*initClass {

		options = Dictionary[
			
			/*--- Set to the one directory you wish all scly generate files to be saved. ---*/	
			\output -> "~/scwork/ly-output",

			/*--- List of directories where SCLy will look for LilyPond templates.---*/
			\template -> "~/scwork/scly/LyTemplates/templateA.ly",

			/*--- Default accidental spelling.---*/
			\accidental_spelling -> \mixed,

			/*--- List of LilyPond files that SCLy will '\include' in all generated *.ly files.---*/
			\lilypond_includes -> nil ,

			/*---  PDF viewer to use to view generated PDF files.
				When nil your environment should know how to open PDFs. ---*/
			\pdf_viewer -> "orakular",
			
			/*--- MIDI player to play MIDI files. 
				When nil your environment should know how to open MIDIs.  ---*/
			\midi_player -> "timidity"
			
		];	
	}	
}