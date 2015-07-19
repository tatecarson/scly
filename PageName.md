# SCLy #

## Discussion group ##
http://groups.google.com.br/group/scly-toolkit

## Introduction ##

Algorithm Composition Toolkit based on SuperCollider programming language and LilyPond automatic music typesetting. SCLy is a system for algorithmic composition. It tries to explore the full range of possibilities of SuperCollider? and Lilypond, with built-in methods and functions but at the same time leaving space for the user to build his/her own processes in any kind of way.

## Classes so far: ##

### Note: note representation ###

#### Use: ####
```
	a = Note.new(-11)
	a.string 
	a.pitch 
	a.octave
	a.notenumber = 10
	a.string 
	a.qt
	a.cps = 440
	a.string 
	a.lily = "cis'"
	a.string 
	a.notenumber
```

##### Save/Edit/Open ly/pdf/midi: #####

```
	a = Note.new(30)
	a.filename = "teste2"
	a.musicString
	a.writeLy	
	a.openPdf	
	a.editLy
```

#### Harmonic Series: ####

```
	a = Note(-5)
	a = a.makeHarmonicSeries
	a.notenumber
	a.writeLy
	a.openPdf
```

### Chord: a chord representation/manipulation ###

```
	c = Chord.new([10, 11])
	c.notenumber
	c.noteArray
	c.qt
	c.stringArray
	c.string
	c.musicString	
	c.writeLy
	c.editLy
	c.openPdf
```

#### Set operations methods: ####

```
	a = Chord.new([1, 2, 3])
	b = Chord.new([2, 3, 4])
	c = a - b
	c.notenumber
```

### FMChord: Creates a 'Frequency Modulation' Chord ###
Arguments: carrier, modulator, index

#### Use: ####
```
	a = FMChord.new(7, -5, 9)
	a.index
	a.car
	a.car = 4
	a.mod	
	a.addChord
	a.diffChord	
	a.fmChord	
```

> ### PitchSequence: Note/Chord sequenceble collection representation ###

#### Use: ####

```
	c = PitchSequence.new([10, 11, [5, 7], 3])
	c.notenumber
	c.pitchArray
	c.qt
	c.string
	c.musicString	
	c.writeLy
	c.editLy
	c.openPdf
```

### TimeSpan ###
Simple class to work with time span durations.
Can handle duration and in number of quavers with tempo indication.

#### Use: ####

```
	t = TimeSpan.new(25); // 25 seconds
	u = TimeSpan.new(20); 
	t = t + 2;
	t = t + u;
	t.duration
	t.asTimeString
	t.numNotes(120); // How many Eighth Notes in 25 seconds with tempo = 120 ?
	t.duration_(60);
	t.tempo_(60);
	t.numNotes
```

### FMChord ###
Creates a 'Frequency Modulation' Chord
Arguments: carrier, modulator, index

#### Use: ####
```
	a = FMChord.new(7, -5, 9)
	a.index
	a.car
	a.car = 4
	a.mod	
	a.addChord
	a.diffChord	
	a.fmChord	
```

### RhythmicCell ###

#### Use: ####

```
	a = RhythmicCell([4, [1, 1, [1, [1, 1, 1, 1]], 1, 1]])
	a.asLySequence	
```

#### **Internal** or with 1-deep Arrays ####
```
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
```

### Dyn ###
Dynamics represantation

#### Use: ####

```
	a = Dyn(0.9)
	a.scDyn
	a.vol_(0.2)
	a.str_(\mf)
	a.scale
	a.scale = [\p, \mp, \mf, \f, \ff]
	a.size
	a.scList
	a.showDict
	a.scDynList
	a.asString
	a.string_(\mp)
	a.scDyn
```

### LispTalk ###

Transfer data to LIPS-like envoriments using a temporary file.
PathName.tmp (/tmp/) is the default folder
"supercollider" is the default filename

#### Methods: ####
> .new // input the Array
> .string  // return a string with Lisp format
> .scArray // return array
> .writeString // write a temporary file with string, default filename is supercollider

#### Use: ####
```
	a = LispTalk.new([1, 1, 1, 1])
	a.scArray  // -> [ 1, 1, 1, 1 ]
	a.string // -> ( 1 1 1 1 )
	a.writeString // write "supercollider" in "/tmp/"
	a.writeString(filename: \otherKey) // write to the file "otherKey" 
```

### Sequence ###

Sequency is a class to represent a Sequency of music elements

```
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
```


### DynSeq ###

> Sequenceble Collection of Dyn (Dynamics) Instances
> See Dyn.sc

> Default:
> Scale = [\ppp, \pp, \p, \mp, \mf, \f, \ff, \fff]
> Values: 0.0 

&lt;-&gt;

 1.0

#### Use: ####

```
	a = DynSeq.new([0.3, 0.2, 0.4])	
	a = DynSeq.new(((1..10)/10).scramble, [\pppp, \ppp, \pp, \p, \mp, \mf, \f, \ff, \fff, \ffff])	
	a.dynSeq
	a.add(0.9)
	a.add(0.1)
	a.dynSeq[0]
	a.dynSeq[1]
	a.scDyn	
	a.asString
	a.str_([\pp, \mp, \f])
	a.dynSeq[0]
	a.newRange(0.3, 0.6)
	a.scramble	
	a.invert
	a.reverse
	a.rotate(2)
	a.mean
	a.first	
	a.firstStr
	a.last	
	a.lastStr
	a.permute(1)
	a.plot
	a.setTable(9)
```

### LyConfig ###

> Configuration Options stored in a Dictionary

#### Use: ####
```
	LyConfig.options[\output]
	LyConfig.options[\output] = "...another folder..."
```

### Others: ###

  * ext-SequenceableCollection

  * ext-asLily

## Detail ##

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages