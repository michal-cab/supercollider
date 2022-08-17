// rewrite function as SyntDef and adding asr envelope

s.boot
s.scope

// techno_synth \/(o.o)\/

(
SynthDef(\techno_synth, {
	arg freq = 0, modfrq, modamp, sus=0.5; // alt. control with MouseX / MouseY
	var sig, env;
	sig = Mix.ar(
		  Array.fill(120, {
			  arg count;
			  var harm;
			  harm = count + 1 * modfrq; // 40 - 700
			  SinOsc.ar(freq + harm, mul: max([0,0], SinOsc.ar(count + 1 * modamp))) * 1/(count+1);
	}));
	env = EnvGen.ar(Env.new(
		levels: [0, 1, 0],
		times:  [0.01, sus],
		curve:  [-3, 2]),
		doneAction: Done.freeSelf);
	sig = sig * env;
	Out.ar(0,sig);
}).add
)

//testing
Synth(\techno_synth, [modfrq: 100, modamp: 2]);
Synth(\techno_synth, [modfrq: 200, modamp: -3]);
Synth(\techno_synth, [modfrq: 160, modamp: 4]);
Synth(\techno_synth, [modfrq: 150, modamp: -6]);

//shorter/longer sustain
Synth(\techno_synth, [modfrq: 100, modamp: 8, sus: 0.2]);
Synth(\techno_synth, [modfrq: 200, modamp: -4,sus: 0.1]);
Synth(\techno_synth, [modfrq: 260, modamp: 4, sus: 3]);
Synth(\techno_synth, [modfrq: 250, modamp: -6, sus: 3]);

//car.freq
Synth(\techno_synth, [freq: 400, modfrq: 50, modamp: -3]);
Synth(\techno_synth, [freq: 600, modfrq: 80, modamp: 6]);

//todo: add Pbind