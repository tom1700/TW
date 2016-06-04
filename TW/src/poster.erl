%% @author Tomasz Morson
%% @doc @todo Add description to tw.


-module(poster).

%% ====================================================================
%% API functions
%% ====================================================================

-export([start/1, stop/1, action/1, init/1]).

start(Sem) ->
	spawn(poster, init, [Sem]).

stop(Pid) ->
	Pid ! {request, self(), stop},
	receive
		{reply,ok}->ok
	end.
action(Pid) ->
	Pid ! {request, action},
	ok.

%% ====================================================================
%% Internal functions
%% ====================================================================
init(Sem) ->
	loop(Sem).

loop(Sem) ->
	receive
	{request, action} ->
		sem:post(Sem),
		io:format(integer_to_list(sem:get(Sem))),
		io:format("~n"),
		loop(Sem);
	{request, Pid, stop} ->
		Pid ! {reply, ok}
end.
