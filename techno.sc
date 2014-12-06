// novy coment - test
s.boot

// "techno" synth.

(
{Mix.ar(
	Array.fill(120,
		{arg count;
		var harm;
			harm = count + 1 * MouseY.kr(40,700
		,1); // remember precedence; count + 1, then * 110
			SinOsc.ar(harm,
				mul: max([0, 0], SinOsc.kr(count+1*MouseX.kr(1,5,1)))
				)*1/(count+1)
		})
)*0.7}.play
)
