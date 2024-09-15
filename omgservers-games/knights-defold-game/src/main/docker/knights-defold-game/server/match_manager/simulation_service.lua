local simulation_factory = require("server.match_manager.simulation_factory")
local players_container = require("server.match_manager.players_container")
local match_settings = require("server.match_manager.match_settings")

local simulation_service
simulation_service = {
	-- Methods
	simulate = function()
		local simulation_step = match_settings.constants.SIMULATION_STEP
		local simulation_depth = players_container:get_simulation_depth(simulation_step)

		print("[SIMULATION_SERVICE] Simulation started, simulation_depth=" .. simulation_depth)

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

			-- TBD
			
		end
		
		-- finish all movements, move all players to final positions
		players_container:finish_movements()
	end
}

return simulation_service