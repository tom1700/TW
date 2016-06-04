%% @author Tomasz Morson
%% @doc @todo Add description to c.


-module(c25proc).

%% ====================================================================
%% API functions
%% ====================================================================
-export([start/0,init/0,aloop/2,bloop/2]).

start()->
	C = spawn(c25proc,init,[]),
	spawn(c25proc,aloop,[C,0]),
	spawn(c25proc,bloop,[C,0]).

init()->
	loop().

%% ====================================================================
%% Internal functions
%% ====================================================================
aloop(C,It)->
	C ! {aaa,It},
	aloop(C,It+1).

bloop(C,It)->
	C ! {bbb,It},
	bloop(C,It+1).

loop()->
	receive
		{aaa,It}->
			io:format("aaa "),
			io:format(integer_to_list(It)),
			io:format("~n"),
			loop();
		{bbb,It}->
			io:format("bbb "),
			io:format(integer_to_list(It)),
			io:format("~n"),
			loop()
	end.
