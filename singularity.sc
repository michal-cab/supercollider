// plotting freq
// freq will travel from freq_start to freq stop in certain time
// and will be modulated on certain speed with ceratin modulation.
// last two parametres will linearly fade away

(
f = {
	arg freq_start = 1000, freq_stop=100, time = 1, speed=50, mod=100;
	var sig, freq, line;
	freq = Line.ar(freq_start, freq_stop, time,1,0); // car. freq. will travel from freq_start to freq_stop
	line = LFTri.ar(freq: Line.ar(speed,0,time,1,0), // and will be modulated on this rate
	                iphase: 0,
	                mul: Line.ar(mod,0,time,1,0), // with this modulation range
	                add: 0);
	sig = freq + line;
}.plot(1)
)


(
SynthDef(\lines, {
	arg freq_start = 1000, freq_stop=100, time = 10, speed=20, mod;
	var freq, sig, line, env;
	freq = Line.ar(freq_start, freq_stop, time,1,0);
	line = LFTri.ar(freq: Line.ar(speed,0,time,1,0),
	                iphase: 0,
	                mul: Line.ar(mod,0,time,1,0),
	                add: 0);
	env = EnvGen.ar(Env([0,1,1,0], [0.1,time,0.1], [0,0]), doneAction: 2);
	sig = SinOsc.ar(freq + line, 0, 1, 0) * env * 0.1;
	Out.ar(0,sig!2);
	}).add;
)


// test
(
x =	Synth(\lines,[
	\freq_start, 880,
	\freq_stop, 220,
	\time, 1,
	\speed, 24,
	\mod, 100])
)



s.scope

// from anywhere, anyhow, to 440 in one minute (should be an hour at least)

(
a = Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 30, \mod, 25]);
b = Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 50, \mod, 150]);
c =	Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 20, \mod, 100]);
d =	Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 12, \mod, 50]);
e =	Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 8, \mod, 200]);

u = Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 300, \mod, 250]);
v = Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 500, \mod, 1500]);
x =	Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 200, \mod, 1000]);
y =	Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 120, \mod, 500]);
z =	Synth(\lines,[\freq_start, exprand(100,10000), \freq_stop, 440, \time, 60, \speed, 80, \mod, 2000]);
)

s.scope