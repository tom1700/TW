%% @author Tomasz Morson
%% @doc @todo Add description to tw.


-module(distprodcon).

%% ====================================================================
%% API functions
%% ====================================================================

-export([start/1, produce/2, initProd/0, initCon/0, initBuff/3]).

start(Size) ->
	Prod = spawn(distprodcon,initProd,[]),
	Con = spawn(distprodcon,initCon,[]),
	spawn(distprodcon,initBuff,[Size,Prod,Con]),
	{Prod,Con}.

produce({Prod,_}, Amount)->
	Prod ! {request, Amount}.

initProd()->
	prodLoop(0).

initCon()->
	conLoop().

initBuff(1,Prod,Con)->
	buffLoop(Prod,Con, empty);
initBuff(N,Prod,Con)->
	spawn(distprodcon,initBuff,[N-1,Prod,Con]),
	buffLoop(Prod,Con, empty).
%% ====================================================================
%% Internal functions
%% ====================================================================
prodLoop(0)->
	receive
		{request, Amount}->
			prodLoop(Amount)
	end;
prodLoop(N)->
	Portion = round(rand:uniform()*10000),
	io:format("Producer sends value ~s~n",[integer_to_list(Portion)]),
	receive
		{request, PID, empty}->
			PID ! {pass, Portion},
			prodLoop(N-1)
	end.

conLoop()->
	receive
		{request, PID, full}->
			PID ! {request, taking}
	end,
	receive
		{pass,Portion}->
			io:format("Consumer takes value ~s~n",[integer_to_list(Portion)]),
			conLoop()
	end.

buffLoop(Prod,Con,empty)->
	Prod ! {request, self(), empty},
	receive
		{pass, Portion}->
			buffLoop(Prod,Con,Portion)
	end;
buffLoop(Prod,Con,Portion)->
	Con ! {request, self(), full},
	receive
		{request, taking}->
			Con ! {pass,Portion},
			buffLoop(Prod,Con,empty)
	end.