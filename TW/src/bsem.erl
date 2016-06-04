%% @author Tomasz Morson
%% @doc @todo Add description to tw.


-module(bsem).

%% ====================================================================
%% API functions
%% ====================================================================

-export([create/0, post/1, wait/1, delete/1, get/1, init/0]).

create() ->
	spawn(bsem, init, []).

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
init() ->
	loop(1).

loop(0) ->
	receive
	{request, Pid, post} ->
		Pid ! {reply, ok},
		loop(1);
	{request, Pid, get} ->
		Pid ! {reply, 0},
		loop(0);
	{request, Pid, delete} ->
		Pid ! {reply, ok}
end;
loop(1) ->
	receive
	{request, Pid, wait} ->
		Pid ! {reply, ok},
		loop(0);
	{request, Pid, get} ->
		Pid ! {reply, 1},
		loop(1);
	{request, Pid, delete} ->
		Pid ! {reply, ok}
end.

