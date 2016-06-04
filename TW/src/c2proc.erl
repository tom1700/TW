%% @author Tomasz Morson
%% @doc @todo Add description to c.


-module(c2proc).

%% ====================================================================
%% API functions
%% ====================================================================
-export([start/0,init/0]).

start()->
	C = spawn(c2proc,init,[]),
	a:start(C),
	b:start(C).

init()->
	loop().

loop()->
	receive
		aaa->
			io:format("aaa~n"),
			loop();
		bbb->
			io:format("bbb~n"),
			loop()
	end.

%% ====================================================================
%% Internal functions
%% ====================================================================


