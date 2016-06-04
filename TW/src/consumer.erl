%% @author Tomasz Morson
%% @doc @todo Add description to tw.


-module(consumer).

%% ====================================================================
%% API functions
%% ====================================================================

-export([start/1, stop/1, init/1, listener/1]).

start(Buffer) ->
	Cpid = spawn(consumer, init, [Buffer]),
	spawn(consumer,listener,[Cpid]).

stop(Pid) ->
	Pid ! {request, stop}.

init(Buffer) ->
	loop(Buffer).

%% ====================================================================
%% Internal functions
%% ====================================================================
listener(Cpid)->
	receive
		{request, stop}->
			exit(Cpid,kill),
			ok
	end.
loop(Buffer) ->
	N = buffer:take(Buffer),
	io:format("Consumer ~p takes value ~s~n",[self(),integer_to_list(N)]),
	timer:sleep(1000),
	loop(Buffer).
