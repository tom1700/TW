%% @author Tomasz Morson
%% @doc @todo Add description to c.


-module(cproc).

%% ====================================================================
%% API functions
%% ====================================================================
-export([start/0,init/0]).

start()->
	C = spawn(cproc,init,[]),
	a:start(C),
	b:start(C).

init()->
	loop().

loop()->
	receive
		aaa->
			io:format("aaa~n")
	end,
	receive
		bbb->
			io:format("bbb~n"),
			loop()
	end.

%% ====================================================================
%% Internal functions
%% ====================================================================


