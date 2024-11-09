local collision_factory = require("server.match_runtime.collision_factory")
local match_settings = require("server.match_runtime.match_settings")

local simulation_factory
simulation_factory = {
	-- Methods
	create = function(self)
		return {
			client_ids = {},
			initial_positions = {},
			simulated_positions = {},
			target_positions = {},
			-- Methods
			add_initial_position = function(instance, client_id, initial_position)
				local client_ids = instance.client_ids
				client_ids[#client_ids + 1] = client_id
				instance.initial_positions[client_id] = initial_position:clone()
			end,
			add_simulated_position = function(instance, client_id, simulated_position)
				instance.simulated_positions[client_id] = simulated_position:clone()
			end,
			add_target_position = function(instance, client_id, target_position)
				instance.target_positions[client_id] = target_position:clone()
			end,
			get_collisions = function(instance)
				local collisions = {}

				local size = match_settings.constants.PLAYER_SIZE

				local client_ids = instance.client_ids
				local initial_positions = instance.initial_positions
				local simulated_positions = instance.simulated_positions
				local target_positions = instance.target_positions

				for index_1 = 1, #client_ids - 1 do
					for index_2 = index_1 + 1, #client_ids do
						local client_1 = client_ids[index_1]
						local client_2 = client_ids[index_2]

						local current_position_1 = simulated_positions[client_1] or initial_positions[client_1]
						local current_position_2 = simulated_positions[client_2] or initial_positions[client_2]

						local delta_vector = current_position_2:subtract(current_position_1)
						local distance_sqr = delta_vector:length_sqr()

						if distance_sqr < size * size then
							local initial_position_1 = initial_positions[client_1]
							local initial_position_2 = initial_positions[client_2]

							local movement_distance_1 = current_position_1:subtract(initial_position_1):length()
							local movement_distance_2 = current_position_2:subtract(initial_position_2):length()

							local target_position_1 = target_positions[client_1] or initial_positions[client_1]
							local collision_mistake_1 = target_position_1:subtract(current_position_1):length()
							local target_position_2 = target_positions[client_2] or initial_positions[client_2]
							local collision_mistake_2 = target_position_2:subtract(current_position_2):length()
						
							local collision = collision_factory:create(
								client_1,
								current_position_1,
								movement_distance_1,
								collision_mistake_1,
								client_2,
								current_position_2,
								movement_distance_2,
								collision_mistake_2
							)

							collisions[#collisions + 1] = collision
						end
					end
				end

				return collisions
			end
		}
		
	end
}

return simulation_factory