local simulation_factory = require("server.match_runtime.simulation_factory")
local players_container = require("server.match_runtime.players_container")
local events_container = require("server.match_runtime.events_container")
local match_settings = require("server.match_runtime.match_settings")
local match_events = require("common.match_events")

local simulation_service
simulation_service = {
	-- Methods
	simulate = function()
		local simulation_step = match_settings.constants.SIMULATION_STEP
		local simulation_depth = players_container:get_simulation_depth(simulation_step)

		print(socket.gettime() .. " [SIMULATOR] Simulation started, simulation_depth=" .. simulation_depth)

		-- simulate movements by steps
		for step_index = match_settings.constants.SIMULATION_OFFSET, simulation_depth do
			local simulation_distance = step_index * simulation_step

			local spawned_players = players_container:get_spawned_players()
			local simulation_instance = simulation_factory:create()

			-- Add simulated positions
			for client_id, spawned_player in pairs(spawned_players) do
				simulation_instance:add_initial_position(client_id, spawned_player.position)

				local player_movement = spawned_player.movement
				if player_movement then
					local simulated_position = player_movement:simulate_movement(simulation_distance)
					simulation_instance:add_simulated_position(client_id, simulated_position)
				end
			end

			-- To track that players attack only once per simulation
			local killers = {}

			-- handle collisions
			local collisions = simulation_instance:get_collisions()
			for _, collision in ipairs(collisions) do
				local client_1 = collision.client_1
				local position_1 = collision.position_1
				local player_1 = spawned_players[client_1]

				local client_2 = collision.client_2
				local position_2 = collision.position_2
				local player_2 = spawned_players[client_2]

				local alive_1 = not player_1.was_killed
				local alive_2 = not player_2.was_killed

				if alive_1 and alive_2 then
					if player_1.movement then
						player_1:interrupt_movement(position_1)
					end
					if player_2.movement then
						player_2:interrupt_movement(position_2)
					end

					local movement_1 = player_1.movement
					local killer_1 = killers[client_1]
					local distance_1 = collision.distance_1 >= collision.distance_2 + simulation_step

					local movement_2 = player_2.movement
					local killer_2 = killers[client_2]
					local distance_2 = collision.distance_2 >= collision.distance_1 + simulation_step

					
					-- Player 1 moved, but player 2 already killed somebody else -> player 1 is winner
					local winner_1_type_1 = movement_1 and not killer_1 and killer_2
					-- Player 1 moved while moved longer path than player 2 -> player 1 is winner
					local winner_1_type_2 = movement_1 and not killer_1 and distance_1

					local winner_2_type_1 = movement_2 and not killer_2 and killer_1
					local winner_2_type_2 = movement_2 and not killer_2 and distance_2

					local mutual_kill_type_1 = movement_1 and movement_2 and not killer_1 and not killer_2

					if winner_1_type_1 or winner_1_type_2 then
						-- Player 1 is winner
						print(socket.gettime() .. " [SIMULATOR] Player1 kills player2, step_index=" .. step_index .. ", client_1=" .. client_1 .. ", client_2=" .. client_2)
						killers[client_1] = true
						-- hit player_2 by player_1
						player_2:kill_player()
						local match_event = match_events:player_killed(client_2, client_1)
						events_container:add_event(match_event)
						
						
					elseif winner_2_type_1 or winner_2_type_2 then
						-- Player 2 is winner
						print(socket.gettime() .. " [SIMULATOR] Player2 kills player1, step_index=" .. step_index .. ", client_1=" .. client_1 .. ", client_2=" .. client_2)
						killers[client_2] = true
						-- hit player_1 by player_2
						player_1:kill_player()
						local match_event = match_events:player_killed(client_1, client_2)
						events_container:add_event(match_event)

					elseif mutual_kill_type_1 then
						-- Mutual kill
						print(socket.gettime() .. " [SIMULATOR] Mutual kill, step_index=" .. step_index .. ", client_1=" .. client_1 .. ", client_2=" .. client_2)
						killers[client_1] = true
						killers[client_2] = true
						player_1:kill_player()
						player_2:kill_player()
						local match_event_1 = match_events:player_killed(client_1, client_2)
						events_container:add_event(match_event_1)
						local match_event_2 = match_events:player_killed(client_2, client_1)
						events_container:add_event(match_event_2)
					end
				end
			end
			
		end
		
		-- finish all movements, move all players to final positions
		players_container:finish_movements()
	end
}

return simulation_service