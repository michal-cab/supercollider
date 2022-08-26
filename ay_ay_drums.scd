(
SynthDef(\ayay, {
	arg tonea, // 0..4095
	toneb,     // 0..4095
	tonec,     // 0..4095
	noise,     // 0..31
	control,   // 0..63
	vola,      // 0..15
	volb,      // 0..15
	volc,      // 0..15
	envfreq,   // 0..4096
	envstyle,  // 0..15
	chiptype,  // 0..1 AY/YM
	mul,       // 1
	add,       // 0
	out=1;

	var sig, env;
	env = EnvGen.ar(Env.perc(0.01,0.2), doneAction: 2);
	sig = AY.ar(tonea, toneb, tonec, noise, control, vola, volb, volc, envfreq, envstyle);
	sig = sig * env;
	Out.ar(out,sig);
}).add
)

//generator
(
x = Synth(\ayay, [
	\tonea, rrand(0,4095).postln,
	\toneb, rrand(0,4095).postln,
	\tonec, rrand(0,4095).postln,
	\noise, rrand(0,31).postln,
	\control, rrand(0,63).postln,
	\vola, rrand(0,15).postln,
	\volb, rrand(0,15).postln,
	\volc, rrand(0,15).postln,
	\envfreq, rrand(0,4095).postln,
	\envstyle, rrand(0,15).postln,
	\out, rrand(0,1).postln;
])
)


//kick1
(
x = Synth(\ayay, [
	\tonea, 0,
	\toneb, 0,
	\tonec, 0,
	\noise, 3,
	\control, 32,
	\vola, 15,
	\volb, 15,
	\volc, 3,
	\envfreq, 100,
	\envstyle, 10])
)

//kick2
(
x = Synth(\ayay, [
	\tonea, 3628,
	\toneb, 1539,
	\tonec, 2712,
	\noise, 31,
	\control, 15,
	\vola, 13,
	\volb, 14,
	\volc, 15,
	\envfreq, 2324,
	\envstyle, 9])
)

//kick3
(
x = Synth(\ayay, [
	\tonea, 4006,
	\toneb, 4003,
	\tonec, 3809,
	\noise, 14,
	\control, 2,
	\vola, 12,
	\volb, 7,
	\volc, 15,
	\envfreq, 47,
	\envstyle, 12])
)



//hihat
(
x = Synth(\ayay, [
	\tonea, 0,
	\toneb, 0,
	\tonec, 0,
	\noise, 9,
	\control, 57,
	\vola, 15,
	\volb, 1,
	\volc, 1,
	\envfreq, 804,
	\envstyle, 3])
)

//blip
(
x = Synth(\ayay, [
	\tonea, 56,
	\toneb, 62,
	\tonec, 63,
	\noise, 0,
	\control, 6,
	\vola, 12,
	\volb, 12,
	\volc, 12,
	\envfreq, 0,
	\envstyle, 0])
)

s.scope

