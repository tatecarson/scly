/*
	Use:
	5.5.asTimeSig // -> a TimeSig
	5.5.asTimeSigArray // -> [ 11, 16 ]
	5.5.asLispTimeSig // -> ( 11 16 )

*/

+ Number {

	asTimeSigArray { 
		// output an array representing a time signature
		var timeSig, thisNumber;
		timeSig = [
			[1, 16], 
			[1, 8], 
			[3, 16], 
			[2, 8], 
			[5, 16], 
			[3, 8],
			[7, 16], 
			[4, 8], 
			[9, 16],
			[5, 8], 
			[11, 16], 
			[6, 8], 
			[13, 16], 
			[7, 8], 
			[15, 16], 
			[8, 8], 
			[17, 16], 
			[9, 8], 
			[19, 16], 
			[10, 8], 
			[21, 16], 
			[11, 8],
			[23, 16], 
			[12, 8], 
			[25, 16], 
			[13, 8], 
			[27, 16], 
			[14, 8]
		];
		
		thisNumber = this.round(0.5).clip(0.5, 14)
		^timeSig[(thisNumber * 2) -1];
	}

	asTimeSig {
		var x;
		x = this.asTimeSigArray;
		^TimeSig.new(x[0], x[1]); 

	}

	asLispTimeSig { 
		// output string with lisp-like syntax representing a time signature
		^this.asTimeSigArray.asLisp;
	}

	brown { arg step=0.1, n=100;	
		^Pbrown(0.0, this, step, inf).asStream.nextN(n)
	}
				// to make a gendy-like profile
	//1.0.gendy(5, 0.1, 10)	gendy { arg points = 5, step=0.1, n=100, add=0;		^(({this.brown(step, n)}.dup(points)).flop + add)	}
	
}