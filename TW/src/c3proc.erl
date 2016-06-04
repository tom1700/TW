%% @author Tomasz Morson
%% @doc @todo Add description to c.


-module(c3proc).

%% ====================================================================
%% API functions
%% ====================================================================
-export([start/0,init/0,aloop/1,bloop/1]).

start()->
	C = spawn(c3proc,init,[]),
	spawn(c3proc,aloop,[C]),
	spawn(c3proc,bloop,[C]).

init()->
	loop().

%% ====================================================================
%% Internal functions
%% ====================================================================
aloop(C)->
	C ! "Haasdfvaa",
	aloop(C).

bloop(C)->
	C ! "bbbdfgfd",
	bloop(C).

loop()->
	receive
		Msg->
			io:format(Msg),
			io:format("~n"),
			loop()
	end.
