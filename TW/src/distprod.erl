%% @author Tomasz Morson
%% @doc @todo Add description to tw.


-module(distprod).

%% ====================================================================
%% API functions
%% ====================================================================

-export([start/1, stop/1, init/1, listener/1]).

start(Buffer) ->
	Cpid = spawn(distprod, init, [Buffer]),
	spawn(producer, listener,[Cpid]).

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
	N = round(rand:uniform()*10000),
	io:format("Producer ~p puts value ~s~n",[self(),integer_to_list(N)]),
	distbuff:produce(Buffer,N),
	timer:sleep(1000),
	loop(Buffer).
