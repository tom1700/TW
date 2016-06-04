%% @author Tomasz Morson
%% @doc @todo Add description to tw.


-module(distbuff).

%% ====================================================================
%% API functions
%% ====================================================================

-export([start/1, produce/2, consume/1, stop/1, buffer/2, last/1]).

start(Size) ->
	Last = spawn(distbuff, last, [empty]),
	Next = spawnBuffer(Size-2,Last),
	First = spawn(distbuff, buffer, [Next,empty]),
	{First,Last}.

produce(Pid, Portion) ->
	Pid ! {self(), pass, Portion},
	receive
		ok->ok
	end.


consume(Pid) ->
	Pid ! {self(), get},
	receive
		{ok,Portion}->Portion
	end.

stop(Pid) ->
	Pid ! {stop}.

%% ====================================================================
%% Internal functions
%% ====================================================================
spawnBuffer(0,Last) ->
	Last;
spawnBuffer(N,Last) ->
	Next = spawnBuffer(N-1,Last),
	spawn(distbuff, buffer, [Next,empty]).

last(empty)->
	receive
		{Pid,pass,Portion}->
			Pid ! ok,
			last(Portion);
		stop -> ok
	end;
last(Portion)->
	receive
		{Pid,get}->
			Pid ! {ok,Portion},
			last(empty);
		stop -> ok
	end.

buffer(Next,empty)->
	receive
		{Pid,pass,Portion}->
			Pid ! ok,
			buffer(Next,Portion);
		stop ->
			Next ! stop
	end;
buffer(Next,Portion)->
	Next ! {self(),pass,Portion},
	receive
		ok->
			buffer(Next,empty);
		stop ->
			Next ! stop
	end.