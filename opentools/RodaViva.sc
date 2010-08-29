/*
	RodaViva.sc
	Rotação utilizando Ambisoncs

	Uso:
	
	
	{ 
	
	    RodaViva.ar( 
	        Saw.ar(700, mul: 0.3), // dente-de-serra 
	        MouseX.kr(-1, 1.0))  // mouse controla posição

	}.scope(4)


*/

RodaViva { 
	
	*ar { arg soundIn, pos;

		var w, x, y,lf, rf, rr, lr;
		#w, x, y = PanB2.ar(soundIn, pos, 0.3);
		#lf, rf, rr, lr = DecodeB2.ar(4, w, x, y);
		^[lf, rf, lr, rr]		

	}
}
