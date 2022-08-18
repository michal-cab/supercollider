// mocking fm with two modulators, feedback and lag


(
SynthDef(\mocking_fm, {
	arg freq=400, freq1=200, ratio1=6, freq2=100, ratio2=4, rel=0.5, lag;
    var sig, mod1, mod2, env, fbk;
	fbk = LocalIn.ar(1);
    env = Env([0, 1, 0], [0.01, rel], [1, -1]).ar(2);
	mod2 = SinOsc.ar(
		freq: freq2 * ratio2 * (env * rrand(0, 1.0)),
		mul: freq2 * ratio2 );
	mod1 = SinOsc.ar(
		 freq: freq1 * ratio1 + mod2,
		 mul: freq1 * ratio1);
	freq = Lag.kr(freq, lag);
	sig = SinOsc.ar(freq + mod1) + (fbk * exprand(0.1,5));
    LocalOut.ar(sig);
	sig = Pan2.ar([sig + mod1.range(0,0.5) , sig * mod2.range(0,0.2)], 0, 0.1);
    sig = sig * env * 0.005;
    Out.ar(0, sig);
}).add;
)

(
p = Routine({
loop{
		Synth(\mocking_fm, [\freq, rrand(50,1000),
			                \freq1, rrand(1,5000),
		                	\freq2, rrand(1,10000),
			                \ratio1, rrand(1,10),
				            \ratio2, rrand(1,5),
			                \rel, rrand(0, 0.5),
			                \lag, rrand(0, 1.0),
		]);
		[0.025, 0.1, 0.2, 3].wchoose([0.35, 0.3, 0.3, 0.01]).wait;
	}
}). play;
)

s.scope