// mocking fm with two modulators, feedback and lag
s.quit
s.boot
Server.default.options.blockSize = 1;



//___SynthDef___//
(
SynthDef(\mocking_fm, {
	arg freq=400, freq1=200, ratio1=6, freq2=100, ratio2=4,
	    rel=0.5, atk=0.1, lag=0, gat=1, mod1a=0.001, mod2a=0.001, fbka=0;
    var sig, mod1, mod2, env, fbk;
	fbk = LocalIn.ar(1);
	env = EnvGen.ar(Env([0, 1, 0], [atk, rel], [6, -3]),
		gate: gat,
		doneAction: Done.freeSelf);
	mod2 = SinOsc.ar(
		freq: freq2 * ratio2 * (env * rrand(0, 10000.0)),
		mul: freq2 * ratio2 );
	mod1 = SinOsc.ar(
		 freq: freq1 * ratio1 + mod2,
		 mul: freq1 * ratio1);
	freq = VarLag.kr(freq, lag, -3); //without Lag / VarLah -> more harsh
	sig = SinOsc.ar(freq + mod1) + (fbk * fbka);
	sig = sig * ({ { Rand(-1, 1) * rrand(1,1000)} ! 4 } ! 4);
	sig = Limiter.ar(sig.sum, 1);
	sig = Pan2.ar([sig * mod1 * mod1a , sig * mod2 * mod2a], 0, 0.1);
	sig = (sig * env).clip2(0.1);
    Out.ar(0, sig);
	LocalOut.ar(sig);
}).add;
)

//___Routine___//
(
p = Routine({
loop{
	x = Synth(\mocking_fm, [\freq, a.value.linlin(0, 1, 10,1000),
			                \freq1,b.value.linlin(0, 1, 1,5000),
		                	\freq2, c.value.linlin(0, 1, 1,2000),
			                \ratio1, d.value.linlin(0, 1, 1, 10),
				            \ratio2, e.value.linlin(0, 1, 1, 5),
			                \atk, f.value,
			                \rel, g.value,
			                \lag, h.value,
			                \mod1a, j.value.linlin(0, 1, 0.0001, 1),
			                \mod2a, k.value.linlin(0, 1, 0.0001, 1),
			                \fbka, l.value

		]);
		[0.025, 0.025, 0.1, 0.1, 0.25,2].choose.wait;
	}
})
)



//___GUI___//
(
Window.closeAll;
w = Window.new("mocking FM", Rect.new(760.0, 429.0, 287.0, 330.0)).front.alwaysOnTop = true;

a = Slider(w, Rect(10, 10, 30, 150)); //freq
b = Slider(w, Rect(50, 10, 30, 150)); //freq1
c = Slider(w, Rect(90, 10, 30, 150)); //freq2
d = Slider(w, Rect(130, 10, 30, 150)); //ratio1
e = Slider(w, Rect(170, 10, 30, 150)); //ratio2

j = Slider(w, Rect(210, 10, 30, 150)); //mod1 amount
k = Slider(w, Rect(250, 10, 30, 150)); //mod2 amount
l = Slider(w, Rect(290, 10, 30, 150)); //rfdbk amount

f = Slider(w, Rect(10, 170, 30, 150)); //atk
g = Slider(w, Rect(50, 170, 30, 150)); //rel
h = Slider(w, Rect(90, 170, 30, 150)); //lag

//////////////////////

i = Button(w, Rect(130, 170, 150, 70))
.states_([
	["OFF", Color.black, Color.gray(0.5)],
	["ON", Color.black, Color.gray(0.8)]])
.action_({
	arg obj;
	if( obj.value == 1, {
		p.reset; p.play(AppClock);},
	{
		p.stop;})
});

m = Button(w, Rect(130, 250, 150, 70))
.states_([
	["RND OFF", Color.black, Color.gray(0.5)],
	["RND ON", Color.black, Color.gray(0.8)]])
.action_({
	arg obj;
	if( obj.value == 1, {

		t=TempoClock.new;
		t.tempo_(16);
		t.schedAbs(t.nextBar,{
			var trig;
			trig = mod(t.beats.asInteger, 3);
			if ( trig == 2, {
				{a.value_(rrand(0,1.0))}.defer;
				{b.value_(rrand(0,1.0))}.defer;
				{c.value_(rrand(0,1.0))}.defer;
				{d.value_(rrand(0,1.0))}.defer;
				{e.value_(rrand(0,1.0))}.defer;
				{f.value_(rrand(0,0.2))}.defer;
				{g.value_(rrand(0,0.5))}.defer;
				{h.value_(rrand(0,1.0))}.defer;
				{j.value_(rrand(0,1.0))}.defer;
				{k.value_(rrand(0,1.0))}.defer;
				{l.value_(rrand(0,1.0))}.defer;},
			                 {// do nothing
			                  }
			);
			4})},   // end of first if statement
	{
		t.stop;})   // ind of second if statement
    });
)

s.scope

// todo: - more interesting time patterns
//       - save presets into array
//       - FX BUS

