
+ SequenceableCollection {



	shake { arg deviation;
		^this.collect({|i|
			i.gaussian(deviation)
		
		})
	
	}
	// Already works:
	// [1, 2, 3, 4].gaussian
	// [1, 2, 3, 4].cauchy
	// [1, 2, 3, 4].logistic
	// [1, 2, 3, 4, 5].poisson + 1

	betarand {  arg val=1, prob1=1, prob2=1;
		^this.collect({|i| 
			i.betarand(val, prob1, prob2)
		})

	}

	weibull { arg spread, shape=1;
		^this.collect({|i| 
			i.weibull(spread, shape)
		})
	}
	
	// Usage:
	// [1, 2, 1, 1, [2, [1, 1, 1, 1,1]], 3, 1, 1].rotateTree(2)
	// -> [ 1, 1, 1, 2, [ 1, [ 1, 2, 1, 1, 1 ] ], 1, 1, 3 ]

	rotateTree { arg nRotations = 1 ;
		var newTree;
		newTree = this.flat.rotate(nRotations).bubble;
		^newTree.reshapeLike(this);
		
	}

	asLisp { 
		// output a 'string' with lisplike syntax
		var lispLike = "( ";
		this.do({ arg item;
			if( item.isNumber,
				{lispLike = lispLike ++ item.asString ++ " "},
				{lispLike = lispLike ++ item.asLisp ++ " "}
			)
		});
		^(lispLike ++ ")") 
	}
	
	hasArray { 
		^this.any{|i| i.isKindOf(SequenceableCollection)}
	}
	

	extractFlat {
		var out; 
		out = Array.new;
		this.do({|i|
			if(
				i.isArray,
				{out = out.add(i[0])},
				{out = out.add(i)}
			)
		});
		^out;
		}

	otherSum {
		var otherSum=0;
		this.do({|i|
			
			if(
				i.isArray == false,
				{otherSum = otherSum + i},
				{otherSum = otherSum + i[0]}
			)
			
		});
		^otherSum;
	}
	
	
}