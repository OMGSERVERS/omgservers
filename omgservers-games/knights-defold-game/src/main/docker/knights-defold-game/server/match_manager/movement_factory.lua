local movement_factory
movement_factory = {
	create = function(self, client_id, from_position, to_position)
		local movement_vector = to_position:subtract(from_position)
		local movement_length = movement_vector:length()
		local direction_vector = movement_vector:normalize()

		return {
			client_id = client_id,
			from_position = from_position,
			to_position = to_position,
			movement_length = movement_length,
			direction_vector = direction_vector,
			-- methods
			simulate_movement = function(movement_instance, distance)
				if distance > movement_instance.movement_length then
					return movement_instance.to_position:clone()
				else
					local movement_vector = direction_vector:multiply(distance)
					return from_position:add(movement_vector)
				end
			end,
			get_simulation_depth = function(movement_instance, simulation_step)
				return math.ceil(movement_instance.movement_length / simulation_step)
			end
		}
	end,
}

return movement_factory