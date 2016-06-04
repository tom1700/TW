%% @author Tomasz Morson
%% @doc @todo Add description to tw.


-module(sem).

%% ====================================================================
%% API functions
%% ====================================================================

-export([create/1, post/1, wait/1, delete/1, get/1, init/1]).

create(Size) ->
	spawn(sem, init, [Size]).

post(Pid) ->
	Pid ! {request, self(), post},
	receive
		{reply,ok}->ok
	end.

wait(Pid) ->
	Pid ! {request, self(), wait},
	receive
		{reply,ok}->ok
	end.

get(Pid) ->
	Pid ! {request, self(), get},
	receive
		{reply,Val}->Val
	end.

delete(Pid) ->
	Pid ! {request, self(), delete},
	receive
		{reply,ok}->ok
	end.

%% ====================================================================
%% Internal functions
%% ====================================================================
init(Size) ->
	loop(Size).

loop(0) ->
	receive
	{request, Pid, post} ->
		Pid ! {reply, ok},
		io:format("~nPost:"),
		io:format(integer_to_list(1)),
		io:format("~n"),
		loop(1);
	{request, Pid, get} ->
		Pid ! {reply, 0},
		loop(0);
	{request, Pid, delete} ->
		Pid ! {reply, ok}
end;
loop(Value) ->
	receive
	{request, Pid, post} ->
		Pid ! {reply, ok},
		io:format("~nPost:"),
		io:format(integer_to_list(Value+1)),
		io:format("~n"),
		loop(Value + 1);
	{request, Pid, wait} ->
		Pid ! {reply, ok},
		io:format("~nWait:"),
		io:format(integer_to_list(Value-1)),
		io:format("~n"),
		loop(Value - 1);
	{request, Pid, get} ->
		Pid ! {reply, Value},
		loop(Value);
	{request, Pid, delete} ->
		Pid ! {reply, ok}
end.

